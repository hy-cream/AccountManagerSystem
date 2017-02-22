package com.study.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.accountmanagesystem.R;
import com.study.bean.AccountBean;

import java.util.List;
import java.util.Map;

/**
 * Created by 胡钰 on 2017/1/5.
 */

public class FundRvAdapter extends RecyclerView.Adapter<FundRvAdapter.FundViewHolder> {

    private Context context;
    private List<AccountBean> mAccountBeans;
    private LayoutInflater mInflater;
    private int[] mAccountImgs={R.mipmap.ft_cash1,R.mipmap.ft_chuxuka1,
            R.mipmap.ft_creditcard1,R.mipmap.ft_shiwuka1,R.mipmap.ft_wangluochongzhi1};

    public FundRvAdapter(Context context,List<AccountBean> accountBeans){
        this.context=context;
        this.mAccountBeans=accountBeans;
        this.mInflater=LayoutInflater.from(context);
    }

    //添加点击事件，自定义接口，模拟ListView的onitemclickListener
    public interface OnItemClickListener{
        void onItemClick(View view,int position,String url);
        void onLongItemClick(View view,int position);
    }

    //创建一个回调
    private OnItemClickListener monItemClickListener;
    public void setOnItemClickListener(OnItemClickListener monItemClickListener){
        this.monItemClickListener=monItemClickListener;
    }

    @Override
    public FundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.fund_rv_item,parent,false);
        FundViewHolder holder=new FundViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FundViewHolder holder, int position) {

        String accountType=mAccountBeans.get(position).getType();
        holder.mAccountImg.setImageResource(mAccountImgs[findImgIdByName(accountType)]);
        holder.mAccountName.setText(mAccountBeans.get(position).getName());
        holder.mAccountBalance.setText(String.valueOf(mAccountBeans.get(position).getPrice()));
        Log.i("FUndRvAdaoter","我显示view成功"+accountType);
//        if (monItemClickListener!=null){
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //创建一个弹出框输入框
//                    EditText balance=new EditText(context);
//                    //Builder是AlertDialog的一个静态内部类
//                    AlertDialog.Builder builder=new AlertDialog.Builder(context)
//                            .setTitle("请输入账户余额")
//                            .setView(balance)
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    //进行TextView的修改和在数据库的修改
//
//                                }
//                            });
////                    //不能点击空白除退出
////                    builder.setCancelable(false);
//                    //用create创建AlertDialog的对象，并调用show方法显示
//                   // builder.create().show();
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return mAccountBeans.size();
    }

    private int findImgIdByName(String accountType){
        int img=0;

        if(accountType.equals("储蓄卡")){
            return 1;
        }else if(accountType.equals("信用卡")){
            return 2;

        }else if(accountType.equals("购物卡")){
            return 3;

        }else if(accountType.equals("支付宝")){
            return 4;

        }
        return img;

    }
    public class FundViewHolder extends RecyclerView.ViewHolder {

        ImageView mAccountImg;
        TextView mAccountName;
        TextView mAccountBalance;
        public FundViewHolder(View itemView) {
            super(itemView);
            mAccountImg= (ImageView) itemView.findViewById(R.id.fund_rv_iv_typeImg);
            mAccountName= (TextView) itemView.findViewById(R.id.fund_rv_tv_typeName);
            mAccountBalance= (TextView) itemView.findViewById(R.id.fund_rv_tv_balance);
        }
    }
}
