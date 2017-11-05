package com.ndu.passwordstorage;

import android.app.Application;

import com.ndu.passwordstorage.data.PasswordDatas;
import com.ndu.passwordstorage.data.impl.PasswordDatasImpl;

public final class MainApp extends Application {

    private static PasswordDatas passwordDatas = new PasswordDatasImpl();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static PasswordDatas getPasswordDatas() {
        return passwordDatas;
    }
}
