package com.milanix.shutter.auth.signup;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.annotations.Nullable;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.dependencies.module.FirebaseModule;
import com.milanix.shutter.user.model.Profile;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

/**
 * Sign up presenter
 *
 * @author milan
 */
public class SignUpPresenter extends AbstractPresenter<SignUpContract.View> implements SignUpContract.Presenter {
    private final App app;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    private final FirebaseStorage storage;
    private final FirebaseRemoteConfig remoteConfig;

    @Inject
    public SignUpPresenter(SignUpContract.View view, App app, FirebaseAuth auth, FirebaseDatabase database,
                           FirebaseStorage storage, FirebaseRemoteConfig remoteConfig) {
        super(view);
        this.app = app;
        this.auth = auth;
        this.database = database;
        this.storage = storage;
        this.remoteConfig = remoteConfig;
    }

    @Override
    public void signUp(@NonNull final SignUpModel signUp) {
        if (isActive()) {
            view.showProgress();
        }

        auth.createUserWithEmailAndPassword(signUp.getEmail(), signUp.getPassword()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uploadAvatar(signUp, task.getResult().getUser());
                        } else if (isActive()) {
                            view.handleSignUpFailure();
                        }
                    }
                });
    }

    @Override
    public String getTermsUrl() {
        return remoteConfig.getString(FirebaseModule.KEY_TERMS);
    }

    private void uploadAvatar(final SignUpModel signUp, final FirebaseUser user) {
        final InputStream avatarStream = getInputStream(signUp.getAvatar());

        if (null != avatarStream) {
            storage.getReference().child("users/" + user.getUid() + "/profile.jpeg").putStream(avatarStream).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            createProfile(signUp, user, taskSnapshot.getDownloadUrl());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    createProfile(signUp, user, null);
                }
            });
        } else if (isActive()) {
            createProfile(signUp, user, null);
        }
    }

    private void createProfile(final SignUpModel signUp, final FirebaseUser user, final Uri avatarUri) {
        final Profile profile = new Profile(user.getUid(), user.getEmail(), avatarUri == null ? null :
                avatarUri.toString(), signUp.getUsername());
        database.getReference().child("users").child(user.getUid()).setValue(profile).
                continueWith(new Continuation<Void, Void>() {
                    @Override
                    public Void then(@NonNull Task<Void> task) throws Exception {
                        updateProfile(signUp, user, avatarUri);

                        return null;
                    }
                });
    }

    private void updateProfile(SignUpModel signUp, FirebaseUser user, Uri avatarUri) {
        user.updateProfile(new UserProfileChangeRequest.Builder()
                .setDisplayName(signUp.getUsername())
                .setPhotoUri(avatarUri)
                .build()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                completeSignUp();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                completeSignUp();
            }
        });
    }

    private void completeSignUp() {
        if (isActive()) {
            app.createUserComponent(auth.getCurrentUser());
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
}
