package com.ndu.passwordstorage.di;


import com.ndu.passwordstorage.data.PasswordDatas;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    PasswordDatas providePasswordDatas();

}
