package com.study.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.study.accountmanagesystem.R;
import com.study.activity.AddAccountActivity;
import com.study.adapter.FundRvAdapter;
import com.study.bean.AccountBean;
import com.study.dao.AccountDao;
import com.study.dao.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 胡钰 on 2017/1/4.
 */

public class FundFragment extends Fragment {

    private RecyclerView mRv;//recyclerView
    private FloatingActionButton mFab;//悬浮按钮
    private List<AccountBean> mAccountBeans;
    private AccountDao accountDao;
    private TextView mTV_money;
    private double sumPrice=0.0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fund,container,false);
        initView(view);
       // initData();
        initAdapter();
        initListener();


        return view;
    }

    private void initListener() {

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity().getSharedPreferences("userinfo",MODE_PRIVATE).getBoolean("isLogin",false)){
                    Intent intent=new Intent(getActivity(), AddAccountActivity.class);
                    int requestCode=2;
                    startActivityForResult(intent,requestCode);
                }else {
                    Toast.makeText(getActivity()," 请先登录",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //addAccount关闭后会执行的方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 2:
                //重新获取数据，设置recyclerView的适配器
                if(resultCode==RESULT_OK){
                    AccountBean accountBean= (AccountBean) data.getBundleExtra("bundle").getSerializable("newAccount");
                    mAccountBeans.add(accountBean);
                    mRv.setAdapter(new FundRvAdapter(getActivity(),mAccountBeans));
                    Log.i("FundFragment","我更新了数据,插入了"+accountBean.getName());
                    sumPrice+=accountBean.getPrice();
                    mTV_money.setText(sumPrice+"");
                }

                break;
        }
    }

    private void initAdapter() {

        if(getActivity().getSharedPreferences("userinfo",MODE_PRIVATE).getBoolean("isLogin",false)){
            //mAccountBeans.clear();
            accountDao=new AccountDao(getActivity());
            String user_id= String.valueOf(getActivity().getSharedPreferences("userinfo",MODE_PRIVATE).getInt("user_id",0));
            mAccountBeans=accountDao.queryAccount(user_id);
            if(mAccountBeans!=null){
                //initAdapter();
                mRv.setAdapter(new FundRvAdapter(getActivity(),mAccountBeans));
                Log.i("FundFragment","我是登入查询结果刷新过的适配器，内容不为空"+mAccountBeans.size());
                for(int i = 0; i < mAccountBeans.size(); i ++){
                    double price=mAccountBeans.get(i).getPrice();
                    sumPrice+=price;
                }
                mTV_money.setText(sumPrice+"");

            }else{
                Log.i("FundFragment","我是登入后的适配器,内容为空");
            }
        }else {
            mTV_money.setText("0.0");
            Log.i("FundFragment","我不设置适配器");
        }

    }


    private void initView(View view) {

        mRv= (RecyclerView) view.findViewById(R.id.fund_rv);
        mFab= (FloatingActionButton) view.findViewById(R.id.fund_fab);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAccountBeans=new ArrayList<AccountBean>();
        mTV_money= (TextView) view.findViewById(R.id.fund_tv_balance);
    }
}
