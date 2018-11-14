package com.bsw.billbook.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.TextImageDrawFormat;
import com.bin.david.form.data.format.title.TitleImageDrawFormat;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.bsw.billbook.R;
import com.bsw.billbook.base.activity.BaseLayoutActivity;
import com.bsw.billbook.bean.RecordAccessoriesCountBean;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RecordDetailActivity extends BaseLayoutActivity {

    private SmartTable table;
    private TextView recordDetail;

    private String yearAndMonth;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("历史详情");
        realm = Realm.getDefaultInstance();
        init();
    }

    private void init() {
        RealmResults<RecordAccessoriesCountBean> countBeans = realm.where(RecordAccessoriesCountBean.class)
                .equalTo("type", RecordAccessoriesCountBean.TYPE_DETAIL)
                .equalTo("yearAndMonth", yearAndMonth)
                .sort("recordTime", Sort.DESCENDING)
                .findAll();

        final Column<String> typeColumn = new Column<>("类型", "accessoriesName");
        typeColumn.setAutoCount(true);
        final Column<Integer> lastColumn = new Column<>("上月结余", "lastBalanceAccessoriesCount");
        lastColumn.setAutoCount(true);
        final Column<Integer> currentColumn = new Column<>("本月结余", "currentBalanceAccessoriesCount");
        currentColumn.setAutoCount(true);
        final Column<Integer> outgoingCountColumn = new Column<>("出库总数", "outGoingAccessories");
        outgoingCountColumn.setAutoCount(true);
        final Column<Integer> warehousingCountColumn = new Column<>("入库总数", "warehousingAccessories");
        warehousingCountColumn.setAutoCount(true);
        final Column<Integer> outgoingTimeColumn = new Column<>("出库次数", "outGoingTimes");
        outgoingTimeColumn.setAutoCount(true);
        final Column<Integer> warehousingTimeColumn = new Column<>("入库次数", "warehousingTimes");
        warehousingTimeColumn.setAutoCount(true);

        final TableData<RecordAccessoriesCountBean> tableData = new TableData<>("统计", countBeans, typeColumn
                , lastColumn, currentColumn, outgoingCountColumn, warehousingCountColumn, outgoingTimeColumn, warehousingTimeColumn);
        tableData.setShowCount(true);

    }

    @Override
    protected void getBundle(Bundle bundle) {
        yearAndMonth = bundle.getString("yearAndMonth");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_detail;
    }

    @Override
    protected void findViews() {
        table = getView(R.id.table);
        recordDetail = getView(R.id.record_detail);
    }

    @Override
    protected void formatViews() {

    }

    @Override
    protected void formatData() {

    }

    @Override
    public void onClick(View view) {

    }
}
