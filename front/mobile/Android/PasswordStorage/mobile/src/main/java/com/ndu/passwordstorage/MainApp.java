package com.ndu.passwordstorage;

import android.app.Application;

import com.ndu.passwordstorage.data.DbHelper;
import com.ndu.passwordstorage.data.PasswordDatas;
import com.ndu.passwordstorage.data.impl.PasswordDatasImpl;

public final class MainApp extends Application {

    private static PasswordDatas passwordDatas;
    private static DbHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new DbHelper(getApplicationContext());
        passwordDatas = new PasswordDatasImpl();
    }

    public static PasswordDatas getPasswordDatas() {
        return passwordDatas;
    }
}
