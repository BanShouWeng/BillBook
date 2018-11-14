package com.bsw.billbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.bsw.billbook.R;
import com.bsw.billbook.base.activity.BaseLayoutActivity;
import com.bsw.billbook.bean.AccessoriesTypeBean;
import com.bsw.billbook.utils.Const;
import com.bsw.billbook.utils.RealmUtils;
import com.bsw.billbook.widget.BswRecyclerView.BswFilterRecyclerView;
import com.bsw.billbook.widget.BswRecyclerView.FilterConvertViewCallBack;
import com.bsw.billbook.widget.BswRecyclerView.RecyclerViewHolder;
import com.bsw.billbook.widget.SingleInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class AccessoriesChooseActivity extends BaseLayoutActivity {

    private EditText accessoriesFilterInput;
    private BswFilterRecyclerView<AccessoriesTypeBean> accessoriesRv;
    private SingleInputDialog dialog;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("配件类型");
        setBaseRightText("添加");
        resetBaseBack();
        realm = Realm.getDefaultInstance();
        initJudge();
        getTypeList();
    }

    private void initJudge() {
        AccessoriesTypeBean typeBean = realm.where(AccessoriesTypeBean.class).equalTo("uuid", AccessoriesTypeBean.OIL_UUID).findFirst();
        if (Const.isEmpty(typeBean)) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    AccessoriesTypeBean accessoriesTypeBean = realm.createObject(AccessoriesTypeBean.class, AccessoriesTypeBean.OIL_UUID);
                    accessoriesTypeBean.setEditTime(System.currentTimeMillis())
                            .setTimer(false)
                            .setTypeName("油")
                            .setTypeCount(0.0);
                    getTypeList();
                }
            });
        } else {
            getTypeList();
        }
    }

    private void getTypeList() {
        RealmResults<AccessoriesTypeBean> results = realm.where(AccessoriesTypeBean.class).sort("editTime", Sort.DESCENDING).findAll();
        accessoriesRv.setData(results);
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_accessories_choose;
    }

    @Override
    protected void findViews() {
        accessoriesRv = getView(R.id.accessories_rv);
        accessoriesFilterInput = getView(R.id.accessories_filter_input);
    }

    @Override
    protected void formatViews() {
        accessoriesRv.initAdapter(R.layout.item_accessories_type_layout, accessoriesFilterInput, convertViewCallBack)
                .setDecoration()
                .setLayoutManager();
    }

    @Override
    protected void formatData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case BACK_ID:
                setResult(RESULT_CANCELED);
                finish();
                break;

            case RIGHT_TEXT_ID:
                dialog = SingleInputDialog.show(mActivity, onSingleInputListener);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Const.notEmpty(dialog)) {
            dialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private FilterConvertViewCallBack<AccessoriesTypeBean> convertViewCallBack = new FilterConvertViewCallBack<AccessoriesTypeBean>() {
        @Override
        public void convert(RecyclerViewHolder holder, final AccessoriesTypeBean accessoriesTypeBean, int showPosition, int allPosition) {
            holder.setText(R.id.item_type_name, accessoriesTypeBean.getTypeName())
                    .setClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(@NonNull Realm realm) {
                                    AccessoriesTypeBean updateType = realm.where(AccessoriesTypeBean.class).equalTo("uuid", accessoriesTypeBean.getUuid()).findFirst();
                                    if (Const.notEmpty(updateType)) {
                                        updateType.setEditTime(System.currentTimeMillis());
                                    }
                                }
                            });
                            Intent intent = new Intent();
                            intent.putExtra("typeUuid", accessoriesTypeBean.getUuid());
                            intent.putExtra("typeName", accessoriesTypeBean.getTypeName());
                            intent.putExtra("typeCount", accessoriesTypeBean.getTypeCount());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
        }

        @Override
        public List<AccessoriesTypeBean> filter(List<AccessoriesTypeBean> data, CharSequence constraint) {
            List<AccessoriesTypeBean> filterResult = new ArrayList<>();
            for (AccessoriesTypeBean typeBean : data) {
                if (typeBean.getTypeName().contains(constraint)) {
                    filterResult.add(typeBean);
                }
            }
            return filterResult;
        }
    };

    private SingleInputDialog.OnSingleInputListener onSingleInputListener = new SingleInputDialog.OnSingleInputListener() {
        @Override
        public void getResult(final String keyword) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    String uuid = UUID.randomUUID().toString();
                    AccessoriesTypeBean accessoriesTypeBean = realm.createObject(AccessoriesTypeBean.class, uuid);
                    accessoriesTypeBean.setEditTime(System.currentTimeMillis())
                            .setTypeName(keyword)
                            .setTimer(false)
                            .setTypeCount(0.0);
                    getTypeList();
                }
            });
        }
    };
}
