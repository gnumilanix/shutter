package com.milanix.shutter.post;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing new post related dependencies
 *
 * @author milan
 */
@Module
public class NewPostModule {
    private final NewPostContract.View view;

    public NewPostModule(NewPostContract.View view) {
        this.view = view;
    }

    @Provides
    public NewPostContract.View provideView() {
        return view;
    }

    @Provides
    public NewPostContract.Presenter providePresenter(NewPostContract.View view,
                                                      Context context,
                                                      FirebaseUser user,
                                                      FirebaseDatabase database,
                                                      FirebaseStorage storage) {
        return new NewPostPresenter(view, context, user, database, storage);
    }
}
