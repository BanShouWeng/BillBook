package com.bsw.billbook;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class app extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
//        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().name("billRealm.realm").schemaVersion(1).build());
    }
}
