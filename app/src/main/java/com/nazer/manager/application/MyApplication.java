package com.nazer.manager.application;

import android.app.Application;

import com.nazer.manager.dagger.AppModule;
import com.nazer.manager.dagger.ApplicationComponent;
import com.nazer.manager.dagger.DaggerApplicationComponent;
import com.nazer.manager.dagger.LocalDbModule;
import com.nazer.manager.dagger.NetModule;
import com.nazer.manager.dagger.ViewModule;

public class MyApplication extends Application {
    public static final String PAYMET_GATEWAY_BASE_URL="";

    private static MyApplication sAppContext;
    private ApplicationComponent mApplicationComponent;

    public static synchronized MyApplication getInstance() {
        return sAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;

        mApplicationComponent= DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .viewModule(new ViewModule())
                .localDbModule(new LocalDbModule())
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }


}
