package com.milanix.shutter.dependencies.module;

import com.milanix.shutter.BuildConfig;
import com.milanix.shutter.core.FirebaseLogTree;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Module providing logging related dependencies
 *
 * @author milan
 */
@Module(includes = {AppModule.class})
public class LogModule {
    @Singleton
    @Provides
    public Timber.Tree provideLogTree() {
        if (BuildConfig.DEBUG)
            return new Timber.DebugTree();
        else
            return new FirebaseLogTree();
    }
}