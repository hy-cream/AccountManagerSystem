package com.study.activity;

import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.study.accountmanagesystem.R;
import com.study.bean.UserBean;

import com.study.dao.UserDao;

public class LoginActivity extends Activity {

    private ImageButton mIb_back;
    private Button mBtn_login;
    private final int LOGIN=2;//判断是否登录成功的Message信息
    private Button mIb_register;
    private EditText mEt_userName;
    private EditText mEt_userPasswd;
    private UserDao userDao;
    private UserBean userBean;
    private final int ISUPDATE=3;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
        //这是实现往数据库的插入数据
        initListener();

    }

    private void initListener() {

        mIb_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加一个判断,如果输入的内容全部不为空，去数据库匹配，然后发送message给handler进行更新
                //查询数据库，登录是一种全局的状态，所以所有界面都要有这个状态值
                String name=mEt_userName.getText().toString();
                String passwd=mEt_userPasswd.getText().toString();
                if("".equals(name)||"".equals(passwd)){
                    Toast.makeText(LoginActivity.this,"请输入完整的登录信息",Toast.LENGTH_SHORT).show();
                }else{
                    //先匹配用户名，如果用户名不存在就提示信息，存在再进行用户密码的匹配
                    userBean=userDao.queryUser(name,passwd);
                    //如果用户登录成功，去修改share里的值，发送广播通知MainActivity更新UI并且回退到more界面
                    if (userBean!=null){
                        //查询用户id,修改share的值
                        editor.putBoolean("isLogin",true);
                        editor.putString("user_name",userBean.getName());
                        editor.putString("user_passwd",userBean.getPasswd());
                        editor.putInt("user_id",userDao.queryIdByName(userBean.getName()));
                        editor.commit();
                        Toast.makeText(LoginActivity.this,"登入成功",Toast.LENGTH_SHORT).show();
                        //发送本地广播
                        Intent intent = new Intent("com.study.broadcast.LocalLogin");
                        intent.putExtra("data","refresh");
                        LocalBroadcastManager.getInstance(LoginActivity.this).sendBroadcast(intent);

                        sendBroadcast(intent);
                        Log.i("LoginActivity","发送广播成功");
                        finish();

                    }else{
                        Toast.makeText(LoginActivity.this,"输入的账户或密码错误",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });
        mIb_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RegisterFragment registerFragment=new RegisterFragment();
//                mFt=mFm.beginTransaction();
//                mFt.replace(R.id.fragment_login,registerFragment);
//                mFt.addToBackStack(null);
//                mFt.commit();
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mIb_back= (ImageButton) findViewById(R.id.login_ib_back);
        mBtn_login= (Button) findViewById(R.id.login_btn_login);
        mIb_register= (Button) findViewById(R.id.login_btn_register);
        mEt_userName= (EditText) findViewById(R.id.login_et_userName);
        mEt_userPasswd= (EditText) findViewById(R.id.login_et_userPasswd);
        userDao=new UserDao(this);
        sharedPreferences=getSharedPreferences("userinfo",MODE_PRIVATE);
        editor=sharedPreferences.edit();


    }
}
