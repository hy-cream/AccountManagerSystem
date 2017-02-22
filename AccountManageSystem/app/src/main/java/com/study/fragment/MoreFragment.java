package com.study.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.accountmanagesystem.R;
import com.study.activity.LoginActivity;
import com.study.activity.OutLoginActivity;
import com.study.adapter.MoreLvAdapter;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 胡钰 on 2017/1/4.
 */

public class MoreFragment extends Fragment {

    private ImageButton mIbSet;
    private ImageView mIvUserPhoto;
    private TextView mTvIsLogin;
    private ImageButton mIbIsHandPasswd;
    private ListView mLv_shareFriends,mLv_writeAccount,mLv_other;
    private String[] mLvOtherTitles={"意见反馈","好评打赏","关于我们"};
    private String[] mLvShareTitles={"推荐给好友"};
    private String[] mLvAccountTitles={"记账提醒"};
    private double inSum,outSum;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_more,container,false);
        initView(view);
        initData();
        initAdapter();
        initListener();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                //更新view
                // Toast.makeText(getActivity(),"我更新了",Toast.LENGTH_SHORT).show();
                initData();
                Log.i("MoreFragment",requestCode+""+getActivity().getSharedPreferences("userinfo",MODE_PRIVATE).getString("user_name",""));
                break;
            case 5:
                if (resultCode==RESULT_OK&&data.getBooleanExtra("outlogin",false)){
                    mTvIsLogin.setText("请登入");
                    mIvUserPhoto.setImageResource(R.mipmap.mine_pic_nor);
                    Log.i("MoreFragment","我更新了登入后的状态");
                }else if(resultCode==RESULT_OK&&!("".equals(data.getStringExtra("name")))){
                    mTvIsLogin.setText(data.getStringExtra("name"));
                }
                break;
        }
    }

    public void initData(){
        if(getActivity().getSharedPreferences("userinfo",MODE_PRIVATE).getBoolean("isLogin",false)){
            mTvIsLogin.setText(getActivity().getSharedPreferences("userinfo",MODE_PRIVATE).getString("user_name",""));
            mIvUserPhoto.setImageResource(R.mipmap.login_mine_pic);
        }else{
            mTvIsLogin.setText("请登入");
            mIvUserPhoto.setImageResource(R.mipmap.mine_pic_nor);
        }
    }
    private void initListener() {
        
            //处理登入后的头像点击事件
            mIvUserPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                if(getActivity().getSharedPreferences("userinfo",MODE_PRIVATE).getBoolean("isLogin",false)){
                    //打开个人中心的活动
                    Intent intent=new Intent(getActivity(), OutLoginActivity.class);
                    startActivityForResult(intent,5);
                }else{
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    int requestCode=1;
                    startActivityForResult(intent,requestCode);
                }
            }
        });
    }
            
    

    private void initAdapter() {
        mLv_writeAccount.setAdapter(new MoreLvAdapter(getActivity(),mLvAccountTitles));
        mLv_shareFriends.setAdapter(new MoreLvAdapter(getActivity(),mLvShareTitles));
        mLv_other.setAdapter(new MoreLvAdapter(getActivity(),mLvOtherTitles));
    }

    private void initView(View view) {
        mIbSet= (ImageButton) view.findViewById(R.id.more_ib_set);
        mIvUserPhoto= (ImageView) view.findViewById(R.id.more_iv_userPhoto);
        mTvIsLogin= (TextView) view.findViewById(R.id.more_tv_isLogin);
        mIbIsHandPasswd= (ImageButton) view.findViewById(R.id.more_ib_handPasswd);
        mLv_writeAccount= (ListView) view.findViewById(R.id.more_lv_writeAccount);
        mLv_shareFriends= (ListView) view.findViewById(R.id.more_lv_shareFriends);
        mLv_other= (ListView) view.findViewById(R.id.more_lv_other);

    }
}
