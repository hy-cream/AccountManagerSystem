package com.study.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.accountmanagesystem.R;
import com.study.bean.UserBean;
import com.study.dao.UserDao;

public class OutLoginActivity extends AppCompatActivity {

    private RelativeLayout mRl_chageName,mRl_chagePasswd;
    private Button mBtn_outLogin;
    private ImageButton mIb_back;
    private TextView mTv_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_login);
        initView();
        initListener();
    }

    private void initListener() {
        //修改用户名的监听
        mRl_chageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个对话框
               final EditText et = new EditText(OutLoginActivity.this);
                new AlertDialog.Builder(OutLoginActivity.this).setTitle("修改用户名")
                        .setIcon(R.mipmap.mine2)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //获取输入值进行判断
                                String input = et.getText().toString();
                                if ("".equals(input)) {
                                    Toast.makeText(getApplicationContext(), "请输入完整哦" + input, Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    UserDao dao=new UserDao(OutLoginActivity.this);
                                    int id=dao.queryIdByName(input);
                                    //id不为0，说明该用户名已存在
                                    if (id!=0){
                                        Toast.makeText(getApplicationContext(), "该用户已存在" + input, Toast.LENGTH_SHORT).show();
                                    }else {
                                        //修改数据库的用户信息
                                        String passwd=getSharedPreferences("userinfo",MODE_PRIVATE).getString("user_passwd","");
                                        UserBean userBean=new UserBean(input,passwd);
                                        dao.updateUser(userBean,getSharedPreferences("userinfo",MODE_PRIVATE).getString("user_name",""));
                                        //修改sharePreferences里的值
                                        SharedPreferences.Editor editor=OutLoginActivity.this.getSharedPreferences("userinfo",MODE_PRIVATE).edit();
                                        editor.putString("user_name",input);
                                        editor.commit();

                                        mTv_name.setText(input);
                                        Intent intent2=new Intent();
                                        intent2.putExtra("name",input);
                                        setResult(RESULT_OK,intent2);
                                    }
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        //修改密码的监听
        mRl_chagePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个自定义两个输入框的对话框
                LayoutInflater inflater=LayoutInflater.from(OutLoginActivity.this);
                View view=inflater.inflate(R.layout.alertdialog_item,null);
                final EditText one = (EditText) view.findViewById(R.id.alertdialog_one);
                final EditText second= (EditText) view.findViewById(R.id.alertdialog_second);

                new AlertDialog.Builder(OutLoginActivity.this).setTitle("修改密码")
                        .setIcon(R.mipmap.mine2)
                        .setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //自定义一个布局，因为要写两个EditView
                                //获取输入值进行判断
                                String oneinput = one.getText().toString();
                                String secondinput = second.getText().toString();
                                if ("".equals(oneinput)||"".equals(secondinput)) {
                                    Toast.makeText(getApplicationContext(), "请输入完整哦", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    if (oneinput.equals(secondinput)){
                                        //修改数据库的用户信息
                                        UserDao dao=new UserDao(OutLoginActivity.this);
                                        String name=getSharedPreferences("userinfo",MODE_PRIVATE).getString("user_name","");
                                        UserBean userBean=new UserBean(name,oneinput);
                                        dao.updateUser(userBean,name);
                                        //修改sharePreferences里的值
                                        SharedPreferences.Editor editor=OutLoginActivity.this.getSharedPreferences("userinfo",MODE_PRIVATE).edit();
                                        editor.putString("user_passwd",oneinput);
                                        editor.commit();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "两次输入的密码不一样哦" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();

            }
        });
        //退出登入的监听
        mBtn_outLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                    //修改密码或者退出登入
                    //退出登入，发送一条广播给MainActivity通知他更新UI
                    SharedPreferences.Editor editor=OutLoginActivity.this.getSharedPreferences("userinfo",MODE_PRIVATE).edit();
                    editor.putInt("user_id",0);
                    editor.putString("user_name","");
                    editor.putString("user_passwd","");
                    editor.putBoolean("isLogin",false);
                    editor.commit();

                    Log.i("MoreFragment","更新完成sharePreferences");

                    //发送本地广播
                    Intent intent = new Intent("com.study.broadcast.LocalLogin");
                    intent.putExtra("data","refresh");
                    LocalBroadcastManager.getInstance(OutLoginActivity.this).sendBroadcast(intent);
                    Log.i("MoreFragment","发送广播成功");
                    Toast.makeText(OutLoginActivity.this,"退出登入",Toast.LENGTH_SHORT).show();
                //设置返回值判断
                Intent intent1=new Intent();
                intent1.putExtra("outlogin",true);
                setResult(RESULT_OK,intent1);
                    finish();
            }
        });

        mIb_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        mRl_chageName= (RelativeLayout) findViewById(R.id.outlogin_rl_name);
        mRl_chagePasswd= (RelativeLayout) findViewById(R.id.outlogin_rl_passwd);
        mBtn_outLogin= (Button) findViewById(R.id.outlogin_btn_out);
        mTv_name= (TextView) findViewById(R.id.outlogin_tv_name);
        mIb_back= (ImageButton) findViewById(R.id.outlogin_ib_back);
        mTv_name.setText(getSharedPreferences("userinfo",MODE_PRIVATE).getString("user_name",""));
    }
}
