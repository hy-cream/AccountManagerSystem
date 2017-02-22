package com.study.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.study.accountmanagesystem.R;
import com.study.adapter.MainVpAdapter;
import com.study.bean.RecordBean;
import com.study.dao.RecordDao;
import com.study.fragment.InRecordFragmnet;
import com.study.fragment.OutRecordFragment;

import java.util.ArrayList;
import java.util.List;

public class AddRecordActivity extends FragmentActivity {

    private ViewPager mVp;
    private TabLayout mTl;
    private ImageButton mIb_back;
    private List<Fragment> mFragments;
    private String[] mTitles={"支出","收入"};
    private ImageButton mBtn_sure;
    private RecordBean bean;
    private MainVpAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_record);
        initView();
        intitData();
        initAdapter();
        initListener();
    }

    private void intitData() {
        OutRecordFragment out=new OutRecordFragment();
        InRecordFragmnet in=new InRecordFragmnet();
        mFragments=new ArrayList<Fragment>();
        mFragments.add(out);
        mFragments.add(in);
    }

    private void initAdapter() {
        vpAdapter=new MainVpAdapter(getSupportFragmentManager(),mFragments,mTitles);
        mVp.setAdapter(vpAdapter);
        mVp.setCurrentItem(0);
        mTl.setupWithViewPager(mVp);

    }

    private void initListener() {

        mIb_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutRecordFragment out = (OutRecordFragment) vpAdapter.getItem(0);
                InRecordFragmnet in= (InRecordFragmnet) vpAdapter.getItem(1);
                RecordBean recordBeanin=in.getData();
                RecordBean recordBeanout=out.getData();
                if(recordBeanin!=null){
                    RecordDao recordDao = new RecordDao(AddRecordActivity.this);
                            recordDao.addRecord(recordBeanin);
                            Toast.makeText(AddRecordActivity.this, "账单保存成功", Toast.LENGTH_SHORT).show();
                          //  getIntent().getBooleanExtra()
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("newrecord", recordBeanin);
                            intent.putExtra("bundle", bundle);
                            setResult(RESULT_OK, intent);
                            finish();
                }else if (recordBeanout!=null){
                    RecordDao recordDao = new RecordDao(AddRecordActivity.this);
                    recordDao.addRecord(recordBeanout);
                    Toast.makeText(AddRecordActivity.this, "账单保存成功", Toast.LENGTH_SHORT).show();
                    //  getIntent().getBooleanExtra()
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("newrecord", recordBeanout);
                    intent.putExtra("bundle", bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(AddRecordActivity.this,"请将信息输入完整",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
       mVp= (ViewPager) findViewById(R.id.addrecord_vp);
        mTl= (TabLayout) findViewById(R.id.addrecord_tl);
        mIb_back= (ImageButton) findViewById(R.id.addrecord_ib_back);
        mBtn_sure= (ImageButton) findViewById(R.id.addrecord_ib_sure);
    }

}
