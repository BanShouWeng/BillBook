package com.bsw.billbook.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bsw.billbook.R;
import com.bsw.billbook.base.activity.BaseLayoutActivity;
import com.bsw.billbook.bean.AccessoriesTypeBean;
import com.bsw.billbook.ui.adapter.BswPagerAdapter;
import com.bsw.billbook.ui.fragment.ListFragment;
import com.bsw.billbook.utils.Const;
import com.bsw.billbook.utils.RealmUtils;
import com.bsw.billbook.utils.TxtUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends BaseLayoutActivity {
    private TabLayout mainTl;
    private ViewPager mainVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("记录", false);
        setBaseRightIcon1(R.mipmap.icon_add, "添加");
        initData();
    }

    private void initData() {
        Realm realm = Realm.getDefaultInstance();
        final AccessoriesTypeBean timerBean = realm.where(AccessoriesTypeBean.class)
                .equalTo("isTimer", true)
                .sort("editTime", Sort.DESCENDING)
                .findFirst();
        if (Const.isEmpty(timerBean)) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    AccessoriesTypeBean accessoriesTypeBean = realm.createObject(AccessoriesTypeBean.class, UUID.randomUUID().toString());
                    accessoriesTypeBean.setEditTime(System.currentTimeMillis())
                            .setTimer(true)
                            .setTypeName("时间暂存")
                            .setTypeCount(0.0);
                }
            });
        } else {
            final Calendar calendar = Calendar.getInstance();
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            calendar.setTimeInMillis(timerBean.getEditTime());
            int beforeMonth = calendar.get(Calendar.MONTH) + 1;
            int beforeYear = calendar.get(Calendar.YEAR);
            if (currentMonth != beforeMonth) {
                RealmResults<AccessoriesTypeBean> typeBeans = realm.where(AccessoriesTypeBean.class)
                        .equalTo("isTimer", false)
                        .findAll();
                for (final AccessoriesTypeBean typeBean : typeBeans) {
                    RealmUtils.CreateHistoryDetail(realm, typeBean.getTypeCount(), typeBean.getTypeName(),typeBean.getUuid(), beforeYear, beforeMonth);
                }
                RealmUtils.CreateHistoryOverview(realm, beforeYear, beforeMonth);
            }
        }
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
        setOnClickListener(R.id.btn_record_jump);

        mainTl.setupWithViewPager(mainVp);

        BswPagerAdapter pagerAdapter = new BswPagerAdapter(getSupportFragmentManager());
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

            case R.id.btn_record_jump:
                jumpTo(RecordActivity.class);
                break;
        }
    }
}
