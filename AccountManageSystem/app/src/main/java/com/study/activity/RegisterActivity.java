package com.study.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.study.accountmanagesystem.R;
import com.study.bean.UserBean;
import com.study.dao.UserDao;

public class RegisterActivity extends Activity {
    private ImageButton mIb_back;
    private Button mBtn_register;
    private EditText mEt_userName,mEt_userPasswd,mEt_userSecondPasswd;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        initView();

        initListener();
    }
    private void initListener() {

        //注册监听
        mBtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里进行插入数据
                String name=mEt_userName.getText().toString();
                String passwd=mEt_userPasswd.getText().toString();
                String secondPasswd=mEt_userSecondPasswd.getText().toString();
                //先判断输入的值是否为空
                if("".equals(name)||"".equals(passwd)||"".equals(secondPasswd)){
                    Toast.makeText(RegisterActivity.this,"请将信息输入完整",Toast.LENGTH_SHORT).show();

                }else{
                    //判断两次输入的密码是否相同
                    if(passwd.equals(secondPasswd)){
                        //由username判断用户是否已存在，返回一个用户id用来判断，如果存在...如果不存在就插入
                        int id=userDao.queryIdByName(name);
                        if (id==0){
                            // 增加用户，回退到登录界面
                            userDao.addUser(new UserBean(name,passwd));
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this,"该用户已存在",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        //返回监听
        mIb_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });



    }

    private void initView() {
        mIb_back= (ImageButton) findViewById(R.id.register_ib_back);
        mBtn_register= (Button) findViewById(R.id.register_btn_register);
        mEt_userName= (EditText) findViewById(R.id.register_et_userName);
        mEt_userPasswd= (EditText) findViewById(R.id.register_et_userPasswd);
        mEt_userName= (EditText) findViewById(R.id.register_et_userName);
        mEt_userSecondPasswd= (EditText) findViewById(R.id.register_et_userPasswdSecond);
        userDao=new UserDao(this);
    }
}
