package com.ndu.passwordstorage.di;

import com.ndu.passwordstorage.data.PasswordDatas;
import com.ndu.passwordstorage.data.impl.PasswordDatasImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public abstract class AppModule {

    @Provides
    @Singleton
    public PasswordDatas providePasswordDatas() {
        return new PasswordDatasImpl();
    }
}
