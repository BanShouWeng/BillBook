package com.bsw.billbook.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bsw.billbook.R;
import com.bsw.billbook.base.fragment.BaseFragment;
import com.bsw.billbook.bean.RecordItemBean;
import com.bsw.billbook.ui.activity.AccessoriesChooseActivity;
import com.bsw.billbook.utils.DateFormatUtils;
import com.bsw.billbook.utils.TxtUtils;
import com.bsw.billbook.widget.timeselector.TimeSelector;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;

public class OutgoingFragment extends BaseFragment {
    private final String FORMAT_STRING = "2017-01-01 00:00";
    private final int REQUEST_CODE = 201;

    private boolean isVisibleToUser;
    private Realm realm;

    private EditText infoCountInput, infoPriceInput;
    private TextView infoTimeInput, infoTypeInput, infoNumberPlateInput, infoSignInput, infoRepairInput;
    private TimeSelector timeSelector;

    private String currentTime;
    private String typeUuid;
    private String typeName;

    public static OutgoingFragment newInstance() {
        return new OutgoingFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        realm = Realm.getDefaultInstance();
        initPicker();
    }

    private void initPicker() {
        if (TextUtils.isEmpty(currentTime)) {
            Calendar calendar = Calendar.getInstance();
            currentTime = String.format(Locale.getDefault(), "%d-%s-%s %s:%s", calendar.get(Calendar.YEAR), format(calendar.get(Calendar.MONTH) + 1), format(calendar.get(Calendar.DAY_OF_MONTH)), format(calendar.get(Calendar.HOUR_OF_DAY)), format(calendar.get(Calendar.MINUTE)));
        }
        timeSelector = new TimeSelector(mActivity, resultHandler, FORMAT_STRING, currentTime);
        infoTimeInput.setText(currentTime);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_outgoing;
    }

    @Override
    protected void findViews() {
        infoCountInput = getView(R.id.info_count_input);
        infoPriceInput = getView(R.id.info_price_input);
        infoTimeInput = getView(R.id.info_time_input);
        infoTypeInput = getView(R.id.info_type_input);
        infoNumberPlateInput = getView(R.id.info_number_plate_input);
        infoSignInput = getView(R.id.info_sign_input);
        infoRepairInput = getView(R.id.info_repair_input);
    }

    @Override
    protected void formatViews() {
        setOnClickListener(R.id.info_time_input, R.id.info_type_input);
    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_time_input:
                timeSelector.show("", currentTime);
                break;

            case R.id.info_type_input:
                jumpTo(AccessoriesChooseActivity.class, REQUEST_CODE);
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
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

    public void onSubmit() {
        if (isVisibleToUser) {
            final String count = TxtUtils.getText(infoCountInput);
            final String price = TxtUtils.getText(infoPriceInput);
            final String numberPlate = TxtUtils.getText(infoNumberPlateInput);
            final String sign = TxtUtils.getText(infoSignInput);
            final String repair = TxtUtils.getText(infoRepairInput);
            if (TextUtils.isEmpty(count) || TextUtils.isEmpty(currentTime) || TextUtils.isEmpty(typeName)
                    || TextUtils.isEmpty(numberPlate) || TextUtils.isEmpty(sign)) {
                toast("信息填写不完整");
            } else {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull Realm realm) {
                        RecordItemBean itemBean = realm.createObject(RecordItemBean.class, UUID.randomUUID().toString());
                        try {
                            itemBean.setOperatingTime(DateFormatUtils.parse(currentTime, DateFormatUtils.COMMON_FORMAT));
                            itemBean.setType(RecordItemBean.TYPE_OUTGOING);
                            itemBean.setAccessoriesType(typeName);
                            itemBean.setAccessoriesUuid(typeUuid);
                            itemBean.setCount(count);
                            itemBean.setNumberPlate(numberPlate);
                            itemBean.setSign(sign);
                            if (!TextUtils.isEmpty(repair)) {
                                itemBean.setRepair(price);
                            }
                            if (!TextUtils.isEmpty(price)) {
                                itemBean.setUnitPrice(price);
                            }
                            toast("添加成功");
                            finish();
                        } catch (ParseException e) {
                            e.printStackTrace();
                            toast("添加失败，请重新尝试");
                        }
                    }
                });
            }
        }
    }

    private String format(int time) {
        String t = String.format(Locale.getDefault(), "%d", time);
        if (t.length() < 2) {
            t = String.format("0%s", t);
        }
        return t;
    }

    private TimeSelector.ResultHandler resultHandler = new TimeSelector.ResultHandler() {
        @Override
        public void handle(String tag, String time) {
            currentTime = time;
        }
    };
}
