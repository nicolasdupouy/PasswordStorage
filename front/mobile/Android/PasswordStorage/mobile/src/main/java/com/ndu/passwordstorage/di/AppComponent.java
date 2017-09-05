package com.ndu.passwordstorage.di;

import com.ndu.passwordstorage.screen.DisplayListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(DisplayListActivity displayListActivity);
}
