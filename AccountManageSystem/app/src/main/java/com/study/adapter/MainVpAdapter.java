package com.study.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 胡钰 on 2017/1/4.
 */

public class MainVpAdapter  extends FragmentPagerAdapter{

    private List<Fragment> mFragments;
    private String[] mTabTitles;
    private List<String> tagList;

    public MainVpAdapter(FragmentManager fm,List<Fragment> fragments,String[] tabTitles ) {
        super(fm);
        this.mFragments=fragments;
        this.mTabTitles=tabTitles;
        tagList=new ArrayList<String>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        tagList.add(makeFragmentName(container.getId(),getItemId(position)));
//        return super.instantiateItem(container, position);
//    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
