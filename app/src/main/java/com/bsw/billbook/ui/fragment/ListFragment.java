package com.bsw.billbook.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.View;

import com.bsw.billbook.R;
import com.bsw.billbook.base.fragment.BaseFragment;
import com.bsw.billbook.bean.RecordItemBean;
import com.bsw.billbook.utils.DateFormatUtils;
import com.bsw.billbook.utils.Logger;
import com.bsw.billbook.widget.BswRecyclerView.BswFilterRecyclerView;
import com.bsw.billbook.widget.BswRecyclerView.BswRecyclerView;
import com.bsw.billbook.widget.BswRecyclerView.MultiplexAdapterCallBack;
import com.bsw.billbook.widget.BswRecyclerView.OnLoadListener;
import com.bsw.billbook.widget.BswRecyclerView.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ListFragment extends BaseFragment {

    public static final int OIL_TYPE = 50;
    public static final int ACCESSORIES_TYPE = 51;

    private int type = OIL_TYPE;

    private SwipeRefreshLayout listSwipe;
    private BswRecyclerView<RecordItemBean> recordRv;

    private Realm realm;
    private Calendar calendar;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(int type) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        realm = Realm.getDefaultInstance();
        calendar = Calendar.getInstance();
    }

    private void getRecordList() {
        RealmResults<RecordItemBean> results = realm.where(RecordItemBean.class).sort("operatingTime", Sort.DESCENDING).findAll();
        List<RecordItemBean> recordItemBeans = new ArrayList<>();

        String tempDate = "-";

        for (int i = 0; i < results.size(); i++) {
            RecordItemBean currentItem = results.get(i);
            calendar.setTimeInMillis(currentItem.getOperatingTime());
            String currentDate = String.format(Locale.getDefault(), "%d-%d", calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            if (!currentDate.equals(tempDate)) {
                RecordItemBean recordItemBean = new RecordItemBean();
                recordItemBean.setType(3);
                recordItemBean.setOperatingTime(currentItem.getOperatingTime());
                recordItemBeans.add(recordItemBean);
            }
            recordItemBeans.add(currentItem);
        }
        recordRv.setData(recordItemBeans);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void findViews() {
        listSwipe = getView(R.id.list_swipe);
        recordRv = getView(R.id.list_rv);
    }

    @Override
    protected void formatViews() {
        listSwipe.setProgressBackgroundColorSchemeResource(android.R.color.white);
        listSwipe.setColorSchemeResources(android.R.color.holo_blue_light
                , android.R.color.holo_green_light
                , android.R.color.holo_red_light
                , android.R.color.holo_orange_light);
        listSwipe.setProgressViewOffset(true
                , 0
                , (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics()));
        listSwipe.setOnRefreshListener(onRefreshListener);

        recordRv.initAdapter(recordCallBack, R.layout.item_time_layout, R.layout.item_info_layout)
                .setLayoutManager()
                .setDecoration();
        switch (type) {
            case OIL_TYPE:
                recordRv.setBackgroundResource(R.color.red);
                break;

            case ACCESSORIES_TYPE:
                recordRv.setBackgroundResource(R.color.green);
                break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRecordList();
    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {

    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

        }
    };

    private MultiplexAdapterCallBack<RecordItemBean> recordCallBack = new MultiplexAdapterCallBack<RecordItemBean>() {
        @Override
        public RecordItemBean convert(RecyclerViewHolder holder, RecordItemBean recordItemBean, int position) {
            if (recordItemBean.getType() == RecordItemBean.TYPE_OTHERS) {
                calendar.setTimeInMillis(recordItemBean.getOperatingTime());
                String currentDate = String.format(Locale.getDefault(), "%d/%d", calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
                holder.setText(R.id._item_time, currentDate);
            } else {
                switch (type) {
                    case OIL_TYPE:
                        holder.setText(R.id.info_name, String.format("%s升", recordItemBean.getCount()))
                                .setText(R.id.info_state, getResources().getColor(recordItemBean.getType() == RecordItemBean.TYPE_OUTGOING ? R.color.red : R.color.green), recordItemBean.getType() == RecordItemBean.TYPE_OUTGOING ? "出库" : "入库")
                                .setText(R.id.info_user, recordItemBean.getSign())
                                .setText(R.id.info_time, DateFormatUtils.format(recordItemBean.getOperatingTime(), "HH:mm:ss"));
                        break;

                    case ACCESSORIES_TYPE:
                        holder.setText(R.id.info_name, recordItemBean.getAccessoriesType())
                                .setText(R.id.info_state, getResources().getColor(recordItemBean.getType() == RecordItemBean.TYPE_OUTGOING ? R.color.red : R.color.green), recordItemBean.getType() == RecordItemBean.TYPE_OUTGOING ? "出库" : "入库")
                                .setText(R.id.info_user, recordItemBean.getSign())
                                .setText(R.id.info_time, DateFormatUtils.format(recordItemBean.getOperatingTime(), "HH:mm:ss"));
                        break;
                }
            }
            return recordItemBean;
        }

        @Override
        public int getItemViewType(int position) {
            return recordRv.getItem(position).getType();
        }

        @Override
        public int onCreateHolder(int viewType, int[] layouts) {
            switch (viewType) {
                case RecordItemBean.TYPE_OTHERS:
                    return layouts[1];

                case RecordItemBean.TYPE_OUTGOING:
                case RecordItemBean.TYPE_WAREHOUSING:
                    return layouts[0];
            }
            return 0;
        }
    };
}
