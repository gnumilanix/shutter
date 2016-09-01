package com.milanix.shutter.dependencies.module;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing auth related dependencies
 *
 * @author milan
 */
@Module
public class FirebaseModule {
    public static final String STORAGE_URL = "gs://shutter-fd81d.appspot.com";

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
    public StorageReference provideStorage() {
        return FirebaseStorage.getInstance().getReferenceFromUrl(STORAGE_URL);
    }
}
