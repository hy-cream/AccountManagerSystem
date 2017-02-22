package com.study.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.accountmanagesystem.R;
import com.study.bean.RecordBean;

import java.util.List;

/**
 * Created by 胡钰 on 2017/1/9.
 */

public class AccoundRvAdapter extends RecyclerView.Adapter<AccoundRvAdapter.AccountRvHolder>{

    private List<RecordBean> mRecordBeans;
    private Context context;
    private LayoutInflater mInflater;

    public AccoundRvAdapter(Context context,List<RecordBean> mRecordBeans){
        this.context=context;
        this.mRecordBeans=mRecordBeans;
        this.mInflater=LayoutInflater.from(context);
    }

    //添加点击事件，自定义接口，模拟ListView的onitemclickListener
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        //void onLongItemClick(View view,int position);
    }

    //创建一个点击事件回调
    private OnItemClickListener monItemClickListener;
    public void setOnItemClickListener(OnItemClickListener monItemClickListener){
        this.monItemClickListener=monItemClickListener;
    }


    @Override
    public AccountRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.account_rv_item,parent,false);
        AccountRvHolder holder=new AccountRvHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final AccountRvHolder holder, final int position) {
        String recordType=mRecordBeans.get(position).getInOrOut();
        if(recordType.equals("收入")){
            holder.mTv_inmoney.setText(String.valueOf(mRecordBeans.get(position).getContent()+"+"+mRecordBeans.get(position).getMoney()));
            holder.mTv_indate.setText(mRecordBeans.get(position).getDate());
            holder.mIv_img.setImageResource(R.mipmap.ic_zhuanchu);
        }else if (recordType.equals("支出")){
            holder.mTv_indate.setText(String.valueOf(mRecordBeans.get(position).getContent()+"-"+mRecordBeans.get(position).getMoney()));
            holder.mTv_inmoney.setText(mRecordBeans.get(position).getDate());
            holder.mIv_img.setImageResource(R.mipmap.ic_zhuanru);
        }
        Log.i("AccountRvAdapter","我显示rv成功");

        if(monItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mRecordBeans.size();
    }

    class AccountRvHolder extends RecyclerView.ViewHolder{

        TextView mTv_inmoney,mTv_indate;
        ImageView mIv_img;
        public AccountRvHolder(View itemView) {
            super(itemView);
            mTv_inmoney= (TextView) itemView.findViewById(R.id.account_rv_tv_inmoney);
            mTv_indate= (TextView) itemView.findViewById(R.id.account_rv_tv_indate);
            mIv_img= (ImageView) itemView.findViewById(R.id.account_rv_iv_img);
        }
    }
}
