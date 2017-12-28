package com.ndu.passwordstorage;

import android.app.Application;

import com.ndu.passwordstorage.data.PasswordDatabase;
import com.ndu.passwordstorage.data.PasswordDatabaseImpl;

public final class MainApp extends Application {

    private static PasswordDatabase passwordDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        passwordDatabase = new PasswordDatabaseImpl(getBaseContext());
    }

    public static PasswordDatabase getPasswordDatabase() {
        return passwordDatabase;
    }
}
