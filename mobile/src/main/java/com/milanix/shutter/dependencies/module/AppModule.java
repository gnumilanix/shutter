package com.milanix.shutter.dependencies.module;

import android.content.Context;

import com.milanix.shutter.App;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing core application dependencies
 */
@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public App provideApp() {
        return app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    public EventBus provideBus() {
        return EventBus.getDefault();
    }
}
