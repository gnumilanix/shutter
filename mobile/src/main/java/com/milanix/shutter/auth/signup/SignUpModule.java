package com.milanix.shutter.auth.signup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.milanix.shutter.App;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing login related dependencies
 *
 * @author milan
 */
@Module
public class SignUpModule {
    private final SignUpContract.View view;

    public SignUpModule(SignUpContract.View view) {
        this.view = view;
    }

    @Provides
    public SignUpContract.View provideSignUpView() {
        return view;
    }

    @Provides
    public SignUpContract.Presenter provideSignUpPresenter(SignUpContract.View view, App app,
                                                           FirebaseAuth auth, FirebaseDatabase database,
                                                           StorageReference storage) {
        return new SignUpPresenter(view, app, auth, database,storage);
    }
}
