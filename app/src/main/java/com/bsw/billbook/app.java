package com.bsw.billbook;

import android.app.Application;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.internal.OsSharedRealm;

public class app extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("bsw.realm")
                .schemaVersion(1)
                .build();
        Realm realm = Realm.getInstance(config);
//        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().name("billRealm.realm").schemaVersion(1).build());
    }
}
