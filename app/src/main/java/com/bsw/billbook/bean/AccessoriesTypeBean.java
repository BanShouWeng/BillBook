package com.bsw.billbook.bean;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * 配件类
 */
public class AccessoriesTypeBean extends RealmObject implements Serializable {
    public static final String OIL_UUID = "1234567890";
    @PrimaryKey
    private String uuid;

    @Required
    private String typeName;

    @Required
    private Double typeCount;

    @Required
    private Long editTime;

    @SuppressWarnings("FieldCanBeLocal")
    @Required
    private Boolean isTimer;

    public AccessoriesTypeBean setTimer(Boolean timer) {
        isTimer = timer;
        return this;
    }

    public AccessoriesTypeBean() {
    }

    public AccessoriesTypeBean setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public AccessoriesTypeBean setEditTime(long editTime) {
        this.editTime = editTime;
        return this;
    }

    public Long getEditTime() {
        return editTime;
    }

    public String getUuid() {
        return uuid;
    }

    public Double getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(Double typeCount) {
        this.typeCount = typeCount;
    }
}
