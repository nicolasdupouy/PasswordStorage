package com.ndu.passwordstorage;

import android.app.Application;

import com.ndu.passwordstorage.di.AppComponent;
import com.ndu.passwordstorage.di.DaggerAppComponent;

public class MainApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}

