package com.bsw.billbook.bean;

import android.text.TextUtils;

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
    private String count;

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
     * 配件UUID
     */
    @Required
    private String accessoriesUuid;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(Long operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public String getAccessoriesType() {
        return accessoriesType;
    }

    public void setAccessoriesType(String accessoriesType) {
        this.accessoriesType = accessoriesType;
    }

    public String getAccessoriesUuid() {
        return accessoriesUuid;
    }

    public void setAccessoriesUuid(String accessoriesUuid) {
        this.accessoriesUuid = accessoriesUuid;
    }
}
