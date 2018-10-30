package com.bsw.billbook.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bsw.billbook.R;
import com.bsw.billbook.base.activity.BaseLayoutActivity;
import com.bsw.billbook.ui.adapter.BswPagerAdapter;
import com.bsw.billbook.ui.fragment.ListFragment;
import com.bsw.billbook.utils.TxtUtils;

public class MainActivity extends BaseLayoutActivity {
    private TabLayout mainTl;
    private ViewPager mainVp;

    private BswPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("记录");
        hideBack();
        setBaseRightIcon1(R.mipmap.icon_add, "添加");
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        mainTl = getView(R.id.main_tl);
        mainVp = getView(R.id.main_vp);
    }

    @Override
    protected void formatViews() {
        mainTl.setupWithViewPager(mainVp);
//        mainTl.setTabMode(TabLayout.MODE_FIXED);

        pagerAdapter = new BswPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setTitles(TxtUtils.getText(mContext, R.string.oil), TxtUtils.getText(mContext, R.string.accessories));
        pagerAdapter.setFragments(ListFragment.newInstance(ListFragment.OIL_TYPE), ListFragment.newInstance(ListFragment.ACCESSORIES_TYPE));

        mainVp.setAdapter(pagerAdapter);
    }

    @Override
    protected void formatData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case RIGHT_ICON_ID1:
                jumpTo(AddRecordActivity.class);
                break;
        }
    }
}
