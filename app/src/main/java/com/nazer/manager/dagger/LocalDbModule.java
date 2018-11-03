package com.nazer.manager.dagger;

import com.nazer.manager.db.PreferenceHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalDbModule {

    @Provides
    @Singleton
    public PreferenceHandler getSharedPrefrencesHandler() {
        return new PreferenceHandler();
    }


}
