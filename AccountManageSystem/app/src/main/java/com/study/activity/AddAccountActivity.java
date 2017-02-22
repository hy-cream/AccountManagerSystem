package com.study.activity;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.study.accountmanagesystem.R;
import com.study.bean.AccountBean;
import com.study.dao.AccountDao;

public class AddAccountActivity extends Activity {
    private Button mBtn_sure;
    private EditText mEt_accountName, mEt_accountBalance;
    private ImageButton mIb_back;
    private Spinner mSp_accountType;
    private String[] mAccountTypes = {"现金", "储蓄卡", "信用卡", "购物卡", "支付宝"};
    private String accountType;
    private AccountDao accountDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        initView();
        //这是实现往数据库的插入数据

        initAdapter();
        initListener();
    }

    private void initAdapter() {
        mSp_accountType.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mAccountTypes));

    }

    private void initListener() {
        mBtn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加一个判断,如果输入的内容全部不为空，先执行插入,返回请求码然后更新recyclerView
                //插入数据库
                String name = mEt_accountName.getText().toString();
                String price = mEt_accountBalance.getText().toString();
                if ("".equals(name)||("".equals(price))) {
                    Toast.makeText(AddAccountActivity.this, "请将信息输入完整", Toast.LENGTH_SHORT).show();
                } else {
                    //将数据插入到Account数据库里
                    accountDao = new AccountDao(AddAccountActivity.this);
                    String user_id = String.valueOf(getSharedPreferences("userinfo", MODE_PRIVATE).getInt("user_id", 0));
                    AccountBean accountBean = new AccountBean(name,Double.valueOf(price) , user_id, accountType);
                    accountDao.addAccount(accountBean);
                    Toast.makeText(AddAccountActivity.this, "添加新账户成功"+user_id, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("newAccount", accountBean);
                    intent.putExtra("bundle", bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        mIb_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSp_accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //拿到被选择项的值
                accountType = (String) mSp_accountType.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initView() {
        mBtn_sure = (Button) findViewById(R.id.addaccount_btn_sure);
        mIb_back = (ImageButton) findViewById(R.id.addaccount_ib_back);
        mSp_accountType = (Spinner) findViewById(R.id.addaccound_sp_accountType);
        mEt_accountName = (EditText) findViewById(R.id.addaccount_et_accountName);
        mEt_accountBalance = (EditText) findViewById(R.id.addaccount_et_balance);
        accountType = "现金";

    }
}
