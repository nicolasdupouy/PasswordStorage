package com.ndu.passwordstorage;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ndu.passwordstorage.data.PasswordDatas;
import com.ndu.passwordstorage.data.impl.PasswordDatasImpl;

public final class MainApp extends Application {

    private static PasswordDatas passwordDatas = new PasswordDatasImpl();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public PasswordDatas getPasswordDatas() {
        return passwordDatas;
    }


}
