package com.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.accountmanagesystem.R;

/**
 * Created by 胡钰 on 2017/1/4.
 */

public class MoreLvAdapter extends BaseAdapter {

    private String[] mLvTitles;
    private Context context;

    public MoreLvAdapter(Context context,String[] lvTitles){
        this.context=context;
        this.mLvTitles=lvTitles;
    }

    @Override
    public int getCount() {
        return mLvTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.more_lv_item,null);
            viewHolder=new ViewHolder();
            viewHolder.mTv= (TextView) convertView.findViewById(R.id.more_lv_item_tv);
            viewHolder.mIv= (ImageView) convertView.findViewById(R.id.more_lv_item_iv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.mTv.setText(mLvTitles[position]);
        viewHolder.mIv.setImageResource(R.mipmap.ic_arrow_down);
        return convertView;
    }
    class ViewHolder{
        private TextView mTv;
        private ImageView mIv;
    }
}
