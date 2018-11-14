package com.bsw.billbook.utils;

import android.support.annotation.NonNull;

import com.bsw.billbook.bean.RecordAccessoriesCountBean;

import java.util.UUID;

import io.realm.Realm;
import io.realm.Sort;

/**
 * @author leiming
 * @date 2018/11/1.
 */
public class RealmUtils {
    public static void CreateHistoryDetail(Realm realm, final Double typeCount, final String typeName, final String typeUuid, final int year, final int month) {
        final RecordAccessoriesCountBean lastCountBean = realm.where(RecordAccessoriesCountBean.class)
                .equalTo("accessoriesUuid", typeUuid)
                .sort("recordTime", Sort.DESCENDING)
                .findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                if (Const.notEmpty(lastCountBean)) {
                    lastCountBean.setCurrentBalanceAccessoriesCount(typeCount);
                    realm.copyToRealmOrUpdate(lastCountBean);
                }
                RecordAccessoriesCountBean countBean = realm.createObject(RecordAccessoriesCountBean.class, UUID.randomUUID().toString());
                countBean.setLastBalanceAccessoriesCount(typeCount)
                        .setAccessoriesUuid(typeUuid)
                        .setAccessoriesName(typeUuid)
                        .setRecordTime(System.currentTimeMillis())
                        .setOutGoingAccessories(0.0)
                        .setWarehousingAccessories(0.0)
                        .setRecordYearAndMonth(year, month)
                        .setType(RecordAccessoriesCountBean.TYPE_DETAIL);
            }
        });
    }

    public static void CreateHistoryOverview(Realm realm, final int year, final int month) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RecordAccessoriesCountBean countBean = realm.createObject(RecordAccessoriesCountBean.class, UUID.randomUUID().toString());
                countBean.setLastBalanceAccessoriesCount(0.0)
                        .setAccessoriesUuid("")
                        .setAccessoriesName("")
                        .setRecordTime(System.currentTimeMillis())
                        .setOutGoingAccessories(0.0)
                        .setWarehousingAccessories(0.0)
                        .setRecordYearAndMonth(year, month)
                        .setType(RecordAccessoriesCountBean.TYPE_OVERVIEW);
            }
        });
    }
}
