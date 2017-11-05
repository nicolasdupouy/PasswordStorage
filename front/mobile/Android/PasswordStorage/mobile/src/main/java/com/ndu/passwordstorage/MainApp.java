package com.ndu.passwordstorage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.ndu.passwordstorage.data.PasswordDatas;
import com.ndu.passwordstorage.data.impl.PasswordDatasImpl;

public final class MainApp extends Application {

    private static final String PASSWORD_DATAS_PREFERENCES = "passwordDatasPreferences";
    private static SharedPreferences sharedPreferences;
    private static PasswordDatas passwordDatas;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(PASSWORD_DATAS_PREFERENCES, Context.MODE_PRIVATE);
        passwordDatas = new PasswordDatasImpl();
    }

    public static PasswordDatas getPasswordDatas() {
        return passwordDatas;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
