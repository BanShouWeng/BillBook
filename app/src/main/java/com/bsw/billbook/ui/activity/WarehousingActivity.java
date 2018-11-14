package com.bsw.billbook.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bsw.billbook.R;
import com.bsw.billbook.base.activity.BaseLayoutActivity;
import com.bsw.billbook.bean.AccessoriesTypeBean;
import com.bsw.billbook.bean.RecordItemBean;
import com.bsw.billbook.utils.Const;
import com.bsw.billbook.utils.DateFormatUtils;
import com.bsw.billbook.utils.TxtUtils;
import com.bsw.billbook.widget.timeselector.TimeSelector;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;

public class WarehousingActivity extends BaseLayoutActivity {
    private final int REQUEST_CODE = 201;

    private Realm realm;
    private EditText infoCountInput, infoPriceInput;
    private TextView infoTimeInput, infoTypeInput;
    private TextView rightText;

    private TimeSelector timeSelector;

    private String operatingTime;
    private String typeUuid;
    private String typeName;

    private RecordItemBean itemBean;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPicker();
        setEnable(false);
        setTitle("入库详情");
        rightText = setBaseRightText("编辑");
        rightText.setTag(false);
    }

    private void initPicker() {
        if (TextUtils.isEmpty(operatingTime)) {
            Calendar calendar = Calendar.getInstance();
            operatingTime = String.format(Locale.getDefault(), "%d-%s-%s %s:%s"
                    , calendar.get(Calendar.YEAR)
                    , TimeSelector.format(calendar.get(Calendar.MONTH) + 1)
                    , TimeSelector.format(calendar.get(Calendar.DAY_OF_MONTH))
                    , TimeSelector.format(calendar.get(Calendar.HOUR_OF_DAY))
                    , TimeSelector.format(calendar.get(Calendar.MINUTE)));
        }
        timeSelector = new TimeSelector(mActivity, resultHandler, "2017-01-01 00:00", operatingTime);
        infoTimeInput.setText(operatingTime);
    }

    @Override
    protected void getBundle(Bundle bundle) {
        if (Const.notEmpty(bundle)) {
            uuid = bundle.getString("uuid");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_warehousing;
    }

    @Override
    protected void findViews() {
        infoCountInput = getView(R.id.info_count_input);
        infoPriceInput = getView(R.id.info_price_input);
        infoTimeInput = getView(R.id.info_time_input);
        infoTypeInput = getView(R.id.info_type_input);
    }

    @Override
    protected void formatViews() {
        setOnClickListener(R.id.info_time_input, R.id.info_type_input);
    }

    @Override
    protected void formatData() {
        if (Const.isEmpty(realm)) {
            realm = Realm.getDefaultInstance();
        }
        itemBean = realm.where(RecordItemBean.class).equalTo("uuid", uuid).findFirst();
        if (Const.notEmpty(itemBean)) {
            infoCountInput.setText(String.valueOf(itemBean.getCount()));
            infoPriceInput.setText(itemBean.getUnitPrice());
            operatingTime = DateFormatUtils.format(itemBean.getOperatingTime(), DateFormatUtils.COMMON_FORMAT);
            infoTimeInput.setText(operatingTime);
            infoTypeInput.setText(itemBean.getAccessoriesType());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_time_input:
                timeSelector.show("", operatingTime);
                break;

            case R.id.info_type_input:
                jumpTo(AccessoriesChooseActivity.class, REQUEST_CODE);
                break;

            case RIGHT_TEXT_ID:
                boolean isEdit = (boolean) rightText.getTag();
                if (isEdit) {
                    rightText.setText("编辑");
                    final double count = Double.valueOf(TxtUtils.getText(infoCountInput));
                    final String price = TxtUtils.getText(infoPriceInput);
                    if (count == 0 || TextUtils.isEmpty(operatingTime) || TextUtils.isEmpty(typeName)) {
                        toast("信息填写不完整");
                    } else {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(@NonNull Realm realm) {
                                try {
                                    itemBean.setOperatingTime(DateFormatUtils.parse(operatingTime, DateFormatUtils.COMMON_FORMAT))
                                            .setType(RecordItemBean.TYPE_WAREHOUSING)
                                            .setAccessoriesType(typeName)
                                            .setAccessoriesUuid(typeUuid)
                                            .setOil(typeUuid.equals(AccessoriesTypeBean.OIL_UUID))
                                            .setCount(count);
                                    if (! TextUtils.isEmpty(price)) {
                                        itemBean.setUnitPrice(price);
                                    }
                                    realm.copyToRealmOrUpdate(itemBean);
                                    toast("修改成功");
                                    finish();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    toast("修改失败，请重新尝试");
                                }
                            }
                        });
                    }
                } else {
                    rightText.setText("提交");
                }
                rightText.setTag(! isEdit);
                setEnable(! isEdit);
                break;
        }
    }

    private void setEnable(boolean enable) {
        infoCountInput.setEnabled(enable);
        infoPriceInput.setEnabled(enable);
        infoTimeInput.setEnabled(enable);
        infoTypeInput.setEnabled(enable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            typeUuid = data.getStringExtra("typeUuid");
            typeName = data.getStringExtra("typeName");
            infoTypeInput.setText(typeName);
        }
    }


    private TimeSelector.ResultHandler resultHandler = new TimeSelector.ResultHandler() {
        @Override
        public void handle(String tag, String time) {
            operatingTime = time;
            infoTimeInput.setText(operatingTime);
        }
    };
}
