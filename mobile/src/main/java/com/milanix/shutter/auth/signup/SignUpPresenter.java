package com.milanix.shutter.auth.signup;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.annotations.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

//// TODO: 31/8/2016 comment it
public class SignUpPresenter extends AbstractPresenter<SignUpContract.View> implements SignUpContract.Presenter {
    private final App app;
    private final FirebaseAuth auth;
    private final StorageReference storage;

    @Inject
    public SignUpPresenter(SignUpContract.View view, App app, FirebaseAuth auth, StorageReference storage) {
        super(view);
        this.app = app;
        this.auth = auth;
        this.storage = storage;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void signUp(@NonNull final SignUp signUp) {
        if (isActive()) {
            view.showProgress();
        }

        auth.createUserWithEmailAndPassword(signUp.getEmail(), signUp.getPassword()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            login(signUp);
                        } else if (isActive()) {
                            view.handleSignUpFailure();
                        }
                    }
                });
    }

    private void login(final SignUp signUp) {
        auth.signInWithEmailAndPassword(signUp.getEmail(), signUp.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    app.createUserComponent(auth.getCurrentUser());
                    uploadPhoto(signUp, auth.getCurrentUser());
                } else if (isActive()) {
                    view.handleLoginFailure();
                }
            }
        });
    }

    private void uploadPhoto(final SignUp signUp, final FirebaseUser user) {
        final InputStream avatarStream = getInputStream(signUp.getAvatar());

        if (null != avatarStream) {
            storage.child("user/" + user.getUid() + "/profile.jpeg").putStream(avatarStream).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            updateProfile(signUp, user, taskSnapshot.getMetadata().getDownloadUrl());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    view.completeSignUp();
                }
            });
        } else if (isActive()) {
            view.completeSignUp();
        }
    }

    @Nullable
    public InputStream getInputStream(Uri uri) {
        try {
            return app.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private void updateProfile(SignUp signUp, FirebaseUser user, Uri avatarUri) {
        user.updateProfile(new UserProfileChangeRequest.Builder()
                .setDisplayName(signUp.getUsername())
                .setPhotoUri(avatarUri)
                .build()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (isActive()) {
                    if (task.isSuccessful()) {
                        view.completeSignUp();
                    } else {
                        //try on later stage to update profile
                        view.completeSignUp();
                    }
                }
            }
        });
    }

    @Override
    public void requestPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (isActive()) {
                            if (task.isSuccessful()) {
                                view.passwordResetEmailSent();
                            } else {
                                view.handleResetPasswordError();
                            }
                        }
                    }
                });
    }
}
