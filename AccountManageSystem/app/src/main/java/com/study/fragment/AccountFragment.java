package com.study.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.accountmanagesystem.R;
import com.study.activity.AddRecordActivity;
import com.study.adapter.AccoundRvAdapter;
import com.study.adapter.FundRvAdapter;
import com.study.bean.AccountBean;
import com.study.bean.RecordBean;
import com.study.bean.UserBean;
import com.study.dao.RecordDao;
import com.study.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 胡钰 on 2017/1/4.
 */

public class AccountFragment extends Fragment{

    private ImageView mIv_pen;
    private RecyclerView mRv;
    private List<RecordBean> mRecordBeans;
    private RecordDao recordDao;
    private TextView mTv_inmoney,mTv_outmoney;
    private double insum,outsum;//收入或者指出的总值

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_account,container,false);
        initView(view);
        initAdapter();
        initData();
        initListener();
        return view;
    }

    private void initData() {
        if(getActivity().getSharedPreferences("userinfo",MODE_PRIVATE).getBoolean("isLogin",false)){
            //在数据库中获取所有的消费记录
            recordDao=new RecordDao(getActivity());
            String user_id= String.valueOf(getActivity().getSharedPreferences("userinfo",MODE_PRIVATE).getInt("user_id",0));
            mRecordBeans=recordDao.queryRecord(user_id);
            if(mRecordBeans!=null){
                //initAdapter();
                mRv.setAdapter(new AccoundRvAdapter(getActivity(),mRecordBeans));
                Log.i("AccountFragment","我是登入查询结果刷新过的适配器，内容不为空"+mRecordBeans.size());
                for(int i = 0; i < mRecordBeans.size(); i ++){
                    double money=mRecordBeans.get(i).getMoney();
                    String inorout=mRecordBeans.get(i).getInOrOut();
                    if(inorout.equals("支出")){
                        outsum=outsum+money;
                        mTv_outmoney.setText("-"+outsum);
                    }else {
                        insum=insum+money;
                        mTv_inmoney.setText("+"+insum);
                    }
                }
                //mIv_background.setVisibility(View.GONE);
            }else{
                Log.i("AccountFragment","我是登入后的适配器,内容为空");
            }
        }else{
            mTv_outmoney.setText("0.0");
            mTv_inmoney.setText("0.0");
        }


    }

    private void initAdapter() {
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initListener() {
        //点击铅笔后进行对账单的增添

            mIv_pen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity().getSharedPreferences("userinfo", MODE_PRIVATE).getBoolean("isLogin", false)) {
                    Intent intent = new Intent(getActivity(), AddRecordActivity.class);
                    int requestCode = 4;
                    startActivityForResult(intent, requestCode);
                    }else{
                        Toast.makeText(getActivity(),"请先登入",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 4:
                //重新获取数据，设置recyclerView的适配器
                if(resultCode==RESULT_OK){
                    RecordBean recordBean= (RecordBean) data.getBundleExtra("bundle").getSerializable("newrecord");
                    mRecordBeans.add(recordBean);
                    mRv.setAdapter(new AccoundRvAdapter(getActivity(),mRecordBeans));
                    if(recordBean.getInOrOut().equals("支出")){
                        outsum+=recordBean.getMoney();
                        mTv_outmoney.setText("-"+outsum);
                    }else {
                        insum+=recordBean.getMoney();
                        mTv_inmoney.setText("+"+insum);
                    }
                    Log.i("AccountFragment","我更新了数据,插入了"+recordBean.getContent());
                }
                break;
        }
    }

    private void initView(View view) {
        mIv_pen= (ImageView) view.findViewById(R.id.account_iv_pen);
        mRv= (RecyclerView) view.findViewById(R.id.account_rv);
        mTv_inmoney= (TextView) view.findViewById(R.id.account_tv_inmoney);
        mTv_outmoney= (TextView) view.findViewById(R.id.account_tv_outmoney);
    }


}
