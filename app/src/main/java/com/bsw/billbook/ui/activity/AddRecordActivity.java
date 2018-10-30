package com.bsw.billbook.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bsw.billbook.R;
import com.bsw.billbook.base.activity.BaseLayoutActivity;
import com.bsw.billbook.ui.adapter.BswPagerAdapter;
import com.bsw.billbook.ui.fragment.ListFragment;
import com.bsw.billbook.ui.fragment.OutgoingFragment;
import com.bsw.billbook.ui.fragment.WarehousingFragment;
import com.bsw.billbook.utils.TxtUtils;

public class AddRecordActivity extends BaseLayoutActivity {
    private TabLayout addRecordTl;
    private ViewPager addRecordVp;

    private BswPagerAdapter pagerAdapter;

    private OutgoingFragment outgoingFragment;
    private WarehousingFragment warehousingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("记录");
        setBaseRightText("提交");
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_record;
    }

    @Override
    protected void findViews() {
        addRecordTl = getView(R.id.add_record_tl);
        addRecordVp = getView(R.id.add_record_vp);
    }

    @Override
    protected void formatViews() {
        pagerAdapter = new BswPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setTitles("出库", "入库");

        outgoingFragment = OutgoingFragment.newInstance();
        warehousingFragment = WarehousingFragment.newInstance();
        pagerAdapter.setFragments(outgoingFragment, warehousingFragment);

        addRecordVp.setAdapter(pagerAdapter);
        addRecordTl.setupWithViewPager(addRecordVp);
        addRecordTl.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void formatData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case RIGHT_TEXT_ID:
                outgoingFragment.onSubmit();
                warehousingFragment.onSubmit();
                break;
        }
    }
}
