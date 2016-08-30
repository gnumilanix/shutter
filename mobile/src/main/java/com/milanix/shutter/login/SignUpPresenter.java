package com.milanix.shutter.login;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;

import javax.inject.Inject;

/**
 * Created by milan on 30/8/2016.
 */

public class SignUpPresenter extends AbstractPresenter<SignUpContract.View> implements SignUpContract.Presenter {
    private final App app;
    private final FirebaseAuth auth;
    private final FirebaseStorage storage;

    @Inject
    public SignUpPresenter(SignUpContract.View view, App app, FirebaseAuth auth, FirebaseStorage storage) {
        super(view);
        this.app = app;
        this.auth = auth;
        this.storage = storage;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void signUp(@NonNull SignUp signUp) {

    }

    @Override
    public void requestPassword(String email) {

    }
}
