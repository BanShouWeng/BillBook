package com.bsw.billbook.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.bsw.billbook.R;
import com.bsw.billbook.base.activity.BaseLayoutActivity;
import com.bsw.billbook.bean.RecordAccessoriesCountBean;
import com.bsw.billbook.widget.BswRecyclerView.BswRecyclerView;
import com.bsw.billbook.widget.BswRecyclerView.ConvertViewCallBack;
import com.bsw.billbook.widget.BswRecyclerView.RecyclerViewHolder;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RecordActivity extends BaseLayoutActivity {
    private BswRecyclerView<RecordAccessoriesCountBean> recordRv;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("历史记录");
        realm = Realm.getDefaultInstance();
        init();
    }

    private void init() {
        RealmResults<RecordAccessoriesCountBean> countBeans = realm.where(RecordAccessoriesCountBean.class)
                .equalTo("type", RecordAccessoriesCountBean.TYPE_OVERVIEW)
                .sort("recordTime", Sort.DESCENDING)
                .findAll();
        recordRv.setData(countBeans);
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void findViews() {
        recordRv = getView(R.id.record_rv);
    }

    @Override
    protected void formatViews() {
        recordRv.initAdapter(R.layout.item_record_layout, countBeanConvertViewCallBack);
    }

    @Override
    protected void formatData() {

    }

    @Override
    public void onClick(View view) {

    }

    private ConvertViewCallBack<RecordAccessoriesCountBean> countBeanConvertViewCallBack = new ConvertViewCallBack<RecordAccessoriesCountBean>() {
        @Override
        public RecordAccessoriesCountBean convert(RecyclerViewHolder holder, final RecordAccessoriesCountBean recordAccessoriesCountBean, int position) {
            holder.setText(R.id.record_create_time, String.format("生成日期：%s/01", recordAccessoriesCountBean.getRecordYearAndMonth()))
                    .setText(R.id.record_time, recordAccessoriesCountBean.getYearAndMonth())
                    .setClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putString("yearAndMonth", recordAccessoriesCountBean.getYearAndMonth());
                            jumpTo(RecordDetailActivity.class, bundle);
                        }
                    });
            return recordAccessoriesCountBean;
        }
    };
}
