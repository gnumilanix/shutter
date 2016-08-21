package com.milanix.shutter.dependencies.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.milanix.shutter.BuildConfig;
import com.milanix.shutter.TaskScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing data dependencies
 *
 * @author milan
 */
@Module
public class DataModule {
    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public TaskScheduler provideTaskScheduler(Context context) {
        return new TaskScheduler(context);
    }
}
