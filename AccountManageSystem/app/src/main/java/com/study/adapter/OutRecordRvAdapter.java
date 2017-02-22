package com.study.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.accountmanagesystem.R;


/**
 * Created by 胡钰 on 2017/1/5.
 */

public class OutRecordRvAdapter extends RecyclerView.Adapter<OutRecordRvAdapter.RecordViewHolder> {

    private Context context;
    private int[] mImgs;
    private String[] mTitles;
    private LayoutInflater mInflater;



    public OutRecordRvAdapter(Context context,int[] imgs,String[] titles){
        this.context=context;
        this.mImgs=imgs;
        this.mTitles=titles;
        this.mInflater=LayoutInflater.from(context);
    }

    //添加点击事件，自定义接口，模拟ListView的onitemclickListener
    public interface OnItemClickListener{
        void onItemClick(RecordViewHolder holder, int position);
        void onLongItemClick(View view,int position);
    }

    //创建一个点击事件回调
    private OnItemClickListener monItemClickListener;
    public void setOnItemClickListener(OnItemClickListener monItemClickListener){
        this.monItemClickListener=monItemClickListener;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.record_rv_item,parent,false);
        RecordViewHolder holder=new RecordViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecordViewHolder holder, final int position) {

        holder.imageView.setImageResource(mImgs[position]);
        holder.textView.setText(mTitles[position]);

        //Log.i("FUndRvAdaoter","我显示view成功");
        //如果设置了回调，则设置点击事件
        if (monItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monItemClickListener.onItemClick(holder,position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mTitles.length;
    }


    public class RecordViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        RelativeLayout rl;
        public RecordViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.record_rv_iv);
            textView= (TextView) itemView.findViewById(R.id.record_rv_tv);
            rl= (RelativeLayout) itemView.findViewById(R.id.record_rl_tab);

        }
    }
}
