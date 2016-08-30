package com.milanix.shutter.dependencies.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.milanix.shutter.BuildConfig;
import com.milanix.shutter.core.JobScheduler;

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
    public static final String STORAGE_URL = "gs://shutter-fd81d.appspot.com";

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Singleton
    @Provides
    public FirebaseDatabase provideDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    public StorageReference provideStorage() {
        return FirebaseStorage.getInstance().getReferenceFromUrl(STORAGE_URL);
    }

    @Singleton
    @Provides
    public Driver provideDriver(Context context) {
        return new GooglePlayDriver(context);
    }

    @Singleton
    @Provides
    public FirebaseJobDispatcher provideJobDispatcher(Driver driver) {
        return new FirebaseJobDispatcher(driver);
    }

    @Singleton
    @Provides
    public JobScheduler provideTaskScheduler(Driver driver, FirebaseJobDispatcher jobDispatcher) {
        return new JobScheduler(driver, jobDispatcher);
    }
}
