package com.milanix.shutter.dependencies.module;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.milanix.shutter.BuildConfig;
import com.milanix.shutter.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing firebase related dependencies
 *
 * @author milan
 */
@Module
public class FirebaseModule {
    public static final int ITEM_PER_PAGE = 20;
    public static final String KEY_TERMS = "url_terms";
    public static final String KEY_PRIVACY = "url_privacy";

    @Provides
    @Singleton
    public FirebaseAuth provideAuth() {
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    public FirebaseDatabase provideDatabase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);

        return database;
    }

    @Singleton
    @Provides
    public FirebaseStorage provideStorageReference() {
        return FirebaseStorage.getInstance();
    }

    @Singleton
    @Provides
    public FirebaseRemoteConfigSettings provideRemoteConfigSettings() {
        return new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
    }

    @Singleton
    @Provides
    public FirebaseRemoteConfig provideRemoteConfig(FirebaseRemoteConfigSettings settings) {
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.setConfigSettings(settings);
        remoteConfig.setDefaults(R.xml.config_defaults);

        return remoteConfig;
    }
}
