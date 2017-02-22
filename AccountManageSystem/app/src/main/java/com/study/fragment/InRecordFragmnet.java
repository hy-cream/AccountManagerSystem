package com.study.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.study.accountmanagesystem.R;
import com.study.adapter.OutRecordRvAdapter;
import com.study.bean.RecordBean;


import java.text.SimpleDateFormat;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 胡钰 on 2017/1/7.
 */

public class InRecordFragmnet extends Fragment {
    private RecyclerView mRv;

    private EditText mEt_money, mEt_content;
    private Spinner mSp;
    private int[] mImgs={R.mipmap.bt_wages,R.mipmap.bt_bouns,R.mipmap.bt_fuli,R.mipmap.bt_invest,
            R.mipmap.bt_hongbao,R.mipmap.bt_jianzhi,R.mipmap.bt_shenghuofei,R.mipmap.bt_baoxiao,
            R.mipmap.bt_tuikuan, R.mipmap.bt_gongjijin,R.mipmap.bt_shebao,R.mipmap.bt_yiwai};
    private String[] mTitles={"工资","奖金","福利","投资收益",
            "红包","兼职","生活费","报销",
            "退款","公积金","社保金","意外收获"};
    private String[] mInRecordType = {"现金", "储蓄卡", "信用卡", "购物卡", "支付宝"};
    private String inRecordType;//默认的账户类型
    private String inRecordContent ;//自定义收入或者支出的事项的内容或者选择列表的内容
    private OutRecordRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_inrecord,container,false);
        initView(view);
        initAdapter();
        initListener();
        return view;
    }

    //自定义一个接口回调
//    public interface InRecordListener{
////        void inRecordListener(RecordBean recordBean);
//            void inRecordListener(RecordBean recordBean);
//    }


    //这里写一个方法用来在activity中调用，获取输入的recordBean对象
    public RecordBean getData(){
        RecordBean recordBean=null;
        //获得数据，添加进数据库
        Log.i("+++++++++++++", String.valueOf(mEt_money));

        Log.i("====================", String.valueOf(mEt_money.getText()));
        if(!("".equals(mEt_money.getText().toString()))){
            double money = Double.valueOf(mEt_money.getText().toString());//获取金额
            //获取具体的事件
            if (!("".equals(mEt_content.getText().toString()))) {
                inRecordContent=mEt_content.getText().toString();

            }
            //获取系统当前的时间
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
            String date=sdf.format(new java.util.Date());
            //进行判断是否能够加入数据库

            //创建一个recordBean对象
            String user_id = String.valueOf(getActivity().getSharedPreferences("userinfo", MODE_PRIVATE).getInt("user_id", 0));
            recordBean=new RecordBean("收入",inRecordContent,money,inRecordType,date,user_id);
        }
         return recordBean;
    }

    private void initListener() {
        mSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inRecordType=mInRecordType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void initAdapter() {
        mRv.setLayoutManager(new GridLayoutManager(getActivity(),4));
        adapter=new OutRecordRvAdapter(getActivity(),mImgs,mTitles);
        mRv.setAdapter(adapter);
        mSp.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mInRecordType));

        //调用接口方法
        adapter.setOnItemClickListener(new OutRecordRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OutRecordRvAdapter.RecordViewHolder holder, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        });
    }

    private void initView(View view) {
        mRv= (RecyclerView) view.findViewById(R.id.inrecord_rv);

        mEt_money = (EditText) view.findViewById(R.id.inrecord_et_outmoney);
        Log.i("+++++++++++++", String.valueOf(mEt_money));
        mEt_content = (EditText) view.findViewById(R.id.inrecord_et_content);
        mSp = (Spinner) view.findViewById(R.id.inrecord_sp_type);
        inRecordType="现金";
        inRecordContent="工资";
    }


}
