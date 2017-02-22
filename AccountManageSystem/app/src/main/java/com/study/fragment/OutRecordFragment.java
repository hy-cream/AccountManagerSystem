package com.study.fragment;



import android.os.Bundle;


import android.os.Handler;

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


import android.widget.EditText;
import android.widget.Spinner;


import com.study.accountmanagesystem.R;
import com.study.adapter.OutRecordRvAdapter;
import com.study.bean.RecordBean;
import com.study.dao.RecordDao;

import java.text.SimpleDateFormat;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by 胡钰 on 2017/1/7.
 */

public class OutRecordFragment extends Fragment{

    private RecyclerView mRv;
    private EditText mEt_money, mEt_content;
    private Spinner mSp;
    private int[] mImgs = {R.mipmap.bt_food, R.mipmap.bt_wine, R.mipmap.bt_car, R.mipmap.bt_shopping,
            R.mipmap.bt_yule, R.mipmap.bt_kuisun, R.mipmap.bt_service, R.mipmap.bt_chongzhi,
            R.mipmap.bt_yiwai, R.mipmap.bt_house, R.mipmap.bt_water, R.mipmap.bt_shicai};
    private String[] mTitles = {"餐饮", "烟酒", "交通", "购物",
            "娱乐", "投资亏损", "生活服务", "充值",
            "医药", "住房", "水电煤", "食材"};
    private String[] mOutRecordType = {"现金", "储蓄卡", "信用卡", "购物卡", "支付宝"};
    private String outRecordType;//默认的账户类型
    private String outRecordContent ;//自定义收入或者支出的事项的内容或者选择列表的内容
    private OutRecordRvAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outrecord, container, false);
        mSp = (Spinner) view.findViewById(R.id.outrecord_sp_type);
        mEt_money = (EditText) view.findViewById(R.id.outrecord_et_outmoney);
        mEt_content = (EditText) view.findViewById(R.id.outrecord_et_content);
        initView(view);
        initListener();
        initAdapter();

        return view;
    }

//    //自定义一个接口，用来与宿主Activity传递值，并在fragment中实现的他的接口
//    public interface OutRecordListener{
//        void outRecordListener(String outRecordType,double money,String date,String outRecordContent);
//    }


    private void initAdapter() {
        mRv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        adapter=new OutRecordRvAdapter(getActivity(), mImgs, mTitles);
        mRv.setAdapter(adapter);
        mSp.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mOutRecordType));
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
        mRv = (RecyclerView) view.findViewById(R.id.outrecord_rv);
        //mIb_sure = (ImageButton) view.findViewById(R.id.outrecord_ib_sure);

        outRecordType = "现金";
        outRecordContent="餐饮";
    }


    private void initListener() {

        //获取spinner选择的type值
        mSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                outRecordType = mOutRecordType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

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
                outRecordContent=mEt_content.getText().toString();

            }
            //获取系统当前的时间
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
            String date=sdf.format(new java.util.Date());
            //进行判断是否能够加入数据库

            //创建一个recordBean对象
            String user_id = String.valueOf(getActivity().getSharedPreferences("userinfo", MODE_PRIVATE).getInt("user_id", 0));
            recordBean=new RecordBean("支出",outRecordContent,money,outRecordType,date,user_id);
        }
        return recordBean;
    }


}
