package com.bsw.billbook.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bsw.billbook.utils.Const;

/**
 * Created by leiming on 2018/1/9.
 */

public class BswPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;
    private String[] titles;

    public BswPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 设置Fragment数组
     *
     * @param fragments Fragment数组
     * @return 适配器
     */
    public BswPagerAdapter setFragments(Fragment... fragments) {
        this.fragments = fragments;
        return this;
    }

    /**
     * 设置Title数组
     *
     * @param titles title数组
     * @return 适配器
     */
    public BswPagerAdapter setTitles(String... titles) {
        this.titles = titles;
        return this;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return Const.judgeListNull(fragments);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
