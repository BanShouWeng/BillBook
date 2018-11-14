package com.bsw.billbook.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RecordItemBean extends RealmObject {
    public static final int TYPE_WAREHOUSING = 1;
    public static final int TYPE_OUTGOING = 2;
    public static final int TYPE_OTHERS = 3;

    @PrimaryKey
    @Required
    private String uuid;

    /**
     * 类型：1、入库；2、出库；3、时间
     */
    @Required
    private Integer type;

    /**
     * 操作时间
     */
    @Required
    private Long operatingTime;

    /**
     * 单价
     */
    private String unitPrice;

    /**
     * 数量，可能是多少升油，不一定是整数
     */
    @Required
    private Double count;

    /**
     * 车牌号
     */
    private String numberPlate;

    /**
     * 签名
     */
    private String sign;

    /**
     * 维修人员
     */
    private String repair;

    /**
     * 配件类型
     */
    @Required
    private String accessoriesType;
    /**
     * 配件类型
     */
    @Required
    private Boolean isOil;

    /**
     * 配件UUID
     */
    @Required
    private String accessoriesUuid;

    public String getUuid() {
        return uuid;
    }

    public Integer getType() {
        return type;
    }

    public RecordItemBean setType(Integer type) {
        this.type = type;
        return this;
    }

    public Long getOperatingTime() {
        return operatingTime;
    }

    public RecordItemBean setOperatingTime(Long operatingTime) {
        this.operatingTime = operatingTime;
        return this;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public RecordItemBean setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public Double getCount() {
        return count;
    }

    public RecordItemBean setCount(Double count) {
        this.count = count;
        return this;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public RecordItemBean setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public RecordItemBean setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String getRepair() {
        return repair;
    }

    public RecordItemBean setRepair(String repair) {
        this.repair = repair;
        return this;
    }

    public String getAccessoriesType() {
        return accessoriesType;
    }

    public RecordItemBean setAccessoriesType(String accessoriesType) {
        this.accessoriesType = accessoriesType;
        return this;
    }

    public String getAccessoriesUuid() {
        return accessoriesUuid;
    }

    public RecordItemBean setAccessoriesUuid(String accessoriesUuid) {
        this.accessoriesUuid = accessoriesUuid;
        return this;
    }

    public Boolean getOil() {
        return isOil;
    }

    public RecordItemBean setOil(Boolean oil) {
        isOil = oil;
        return this;
    }
}
