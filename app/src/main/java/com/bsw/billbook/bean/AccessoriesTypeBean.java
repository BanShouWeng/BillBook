package com.bsw.billbook.bean;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class AccessoriesTypeBean extends RealmObject implements Serializable {
    @PrimaryKey
    private String uuid;

    @Required
    private String typeName;

    private Long editTime;

    public AccessoriesTypeBean() { }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setEditTime(long editTime) {
        this.editTime = editTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
