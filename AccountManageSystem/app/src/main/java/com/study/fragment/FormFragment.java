package com.study.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.accountmanagesystem.R;
import com.study.dao.UserDao;

/**
 * Created by 胡钰 on 2017/1/4.
 */

public class FormFragment extends Fragment {
    private UserDao userDao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_form,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        userDao=new UserDao(getActivity());
    }
}
