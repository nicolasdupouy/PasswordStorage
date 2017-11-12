package com.ndu.passwordstorage;

import android.app.Application;

import com.ndu.passwordstorage.data.PasswordDatas;
import com.ndu.passwordstorage.data.PasswordDatasImpl;

public final class MainApp extends Application {

    private static PasswordDatas passwordDatas;

    @Override
    public void onCreate() {
        super.onCreate();
        passwordDatas = new PasswordDatasImpl();
    }

    public static PasswordDatas getPasswordDatas() {
        return passwordDatas;
    }
}
