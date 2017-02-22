package com.study.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;

import android.os.Bundle;

import android.util.Log;
import android.view.Window;

import com.study.accountmanagesystem.R;
import com.study.adapter.MainVpAdapter;

import com.study.fragment.AccountFragment;
import com.study.fragment.FormFragment;
import com.study.fragment.FundFragment;
import com.study.fragment.MoreFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager mVp;
    private TabLayout mTl;
    private List<Fragment> mFragments;
    private String[] mTabTitles={"记账","报表","资金","更多"};
    private TabLayout.Tab account,form,fund,more;

    //在MainActivity中注册一个本地广播
    private LocalReceiver localReceiver;//广播接收器
    private LocalBroadcastManager broadcastManager;//广播管理器
    private IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //注册一个本地广播
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.study.broadcast.LocalLogin");
        broadcastManager=LocalBroadcastManager.getInstance(this);//获取实例
        localReceiver=new LocalReceiver();
        broadcastManager.registerReceiver(localReceiver,intentFilter);//注册一个本地广播监听器
        Log.i("MainActivity","注册广播成功");

        initView();
        initData();
        initAdapter();
        initListener();


    }



    private void initListener() {

        mTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab==mTl.getTabAt(0)){
                    tab.setIcon(R.mipmap.tab_accounte);


                }else if(tab==mTl.getTabAt(1)){
                    tab.setIcon(R.mipmap.tab_form);

                }else if(tab==mTl.getTabAt(2)){
                    tab.setIcon(R.mipmap.tab_founds);

                }else if(tab==mTl.getTabAt(3)){
                    tab.setIcon(R.mipmap.tab_mine);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                if(tab==mTl.getTabAt(0)){
                    tab.setIcon(R.mipmap.tab_accounte2);

                }else if(tab==mTl.getTabAt(1)){
                    tab.setIcon(R.mipmap.tab_form2);

                }else if(tab==mTl.getTabAt(2)){
                    tab.setIcon(R.mipmap.tab_founds2);

                }else if(tab==mTl.getTabAt(3)){
                    tab.setIcon(R.mipmap.tab_mine2);

                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void initAdapter() {
        mVp.setAdapter(new MainVpAdapter(getSupportFragmentManager(),mFragments,mTabTitles));
        mVp.setCurrentItem(0);
        mTl.setupWithViewPager(mVp);

        account=mTl.getTabAt(0).setIcon(R.mipmap.tab_accounte);
        form=mTl.getTabAt(1).setIcon(R.mipmap.tab_form2);
        fund=mTl.getTabAt(2).setIcon(R.mipmap.tab_founds2);
        more=mTl.getTabAt(3).setIcon(R.mipmap.tab_mine2);

    }

    private void initData() {
        mFragments=new ArrayList<Fragment>();
        AccountFragment accountFragment=new AccountFragment();
        FormFragment formFragment=new FormFragment();
        FundFragment fundFragment=new FundFragment();
        MoreFragment moreFragment=new MoreFragment();
        mFragments.add(accountFragment);
        mFragments.add(formFragment);
        mFragments.add(fundFragment);
        mFragments.add(moreFragment);

    }

    private void initView() {
        mVp= (ViewPager) findViewById(R.id.main_vp);
        mTl= (TabLayout) findViewById(R.id.main_tl);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        broadcastManager.unregisterReceiver(localReceiver);
        Log.i("MainActivity","解除广播成功");

    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
           // initData();
            String msg = intent.getStringExtra("data");

            if("refresh".equals(msg)){
                onResume();
//                MainVpAdapter mainVpAdapter=new MainVpAdapter(getSupportFragmentManager(),mFragments,mTabTitles);
//                mainVpAdapter.notifyDataSetChanged();
                Log.i("MainActivity","执行广播成功");
            }


        }
    }


}
