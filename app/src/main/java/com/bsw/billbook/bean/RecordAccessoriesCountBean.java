package com.bsw.billbook.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author leiming
 * @date 2018/11/1.
 */
public class RecordAccessoriesCountBean extends RealmObject {
    /**
     * 概述：当前记录是那年那月的
     */
    public static final int TYPE_OVERVIEW = 22;
    /**
     * 详情：概述对应年月的入库出库总数
     */
    public static final int TYPE_DETAIL = 23;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_DETAIL, TYPE_OVERVIEW})
    @interface HistoryType {
    }

    @SuppressWarnings("FieldCanBeLocal")
    @PrimaryKey
    private String uuid;

    /**
     * 上月结余
     */
    @Required
    private Double lastBalanceAccessoriesCount;

    /**
     * 本月结余
     */
    private Double currentBalanceAccessoriesCount;

    /**
     * 出库总数
     */
    @Required
    private Double outGoingAccessories;

    /**
     * 出库总数
     */
    @Required
    private Integer outGoingTimes = 0;

    /**
     * 入库总数
     */
    @Required
    private Double warehousingAccessories;

    /**
     * 入库总数
     */
    @Required
    private Integer warehousingTimes = 0;

    /**
     * 配件Id
     */
    @SuppressWarnings("FieldCanBeLocal")
    @Required
    private String accessoriesUuid;

    /**
     * 配件名称
     */
    @SuppressWarnings("FieldCanBeLocal")
    @Required
    private String accessoriesName;

    /**
     * 记录时间
     */
    @Required
    private Long recordTime;

    /**
     * 当前类类型，是记录详情，还是记录概述
     */
    @SuppressWarnings("FieldCanBeLocal")
    @Required
    private Integer type;

    /**
     * 生成记录的年月
     */
    @Required
    private String recordYearAndMonth;
    /**
     * 记录对应的年月，识别详情是否与概述是同一时间段的
     */
    @Required
    private String yearAndMonth;

    public RecordAccessoriesCountBean() {
    }

    public Long getRecordTime() {
        return recordTime;
    }

    public Double getOutGoingAccessories() {
        return outGoingAccessories;
    }

    public RecordAccessoriesCountBean setOutGoingAccessories(Double outGoingAccessories) {
        this.outGoingAccessories = outGoingAccessories;
        return this;
    }

    public Double getWarehousingAccessories() {
        return warehousingAccessories;
    }

    public RecordAccessoriesCountBean setWarehousingAccessories(Double warehousingAccessories) {
        this.warehousingAccessories = warehousingAccessories;
        return this;
    }

    public RecordAccessoriesCountBean setRecordTime(Long recordTime) {
        this.recordTime = recordTime;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public RecordAccessoriesCountBean setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public RecordAccessoriesCountBean(String uuid) {
        this.uuid = uuid;
    }

    public Double getLastBalanceAccessoriesCount() {
        return lastBalanceAccessoriesCount;
    }

    public RecordAccessoriesCountBean setLastBalanceAccessoriesCount(Double lastBalanceAccessoriesCount) {
        this.lastBalanceAccessoriesCount = lastBalanceAccessoriesCount;
        return this;
    }

    public RecordAccessoriesCountBean setAccessoriesUuid(String accessoriesUuid) {
        this.accessoriesUuid = accessoriesUuid;
        return this;
    }

    public RecordAccessoriesCountBean setType(@HistoryType Integer type) {
        this.type = type;
        return this;
    }

    public Double getCurrentBalanceAccessoriesCount() {
        return currentBalanceAccessoriesCount;
    }

    public RecordAccessoriesCountBean setCurrentBalanceAccessoriesCount(Double currentBalanceAccessoriesCount) {
        this.currentBalanceAccessoriesCount = currentBalanceAccessoriesCount;
        return this;
    }

    public String getRecordYearAndMonth() {
        return recordYearAndMonth;
    }

    public String getYearAndMonth() {
        return yearAndMonth;
    }

    public String getAccessoriesName() {
        return accessoriesName;
    }

    public RecordAccessoriesCountBean setAccessoriesName(String accessoriesName) {
        this.accessoriesName = accessoriesName;
        return this;
    }

    public Integer getOutGoingTimes() {
        return outGoingTimes;
    }

    public RecordAccessoriesCountBean setOutGoingTimes() {
        outGoingTimes++;
        return this;
    }

    public Integer getWarehousingTimes() {
        return warehousingTimes;
    }

    public RecordAccessoriesCountBean setWarehousingTimes() {
        warehousingTimes++;
        return this;
    }

    public RecordAccessoriesCountBean setRecordYearAndMonth(int year, int month) {
        yearAndMonth = String.format(Locale.getDefault(), "%d/%s", year, format(month));
        if (month == 12) {
            month = 1;
            year++;
        }
        this.recordYearAndMonth = String.format(Locale.getDefault(), "%d/%s", year, format(month));
        return this;
    }

    protected static String format(int formatInt) {
        if (formatInt > 9) {
            return String.valueOf(formatInt);
        } else {
            return String.format(Locale.getDefault(), "0%d", formatInt);
        }
    }
}
