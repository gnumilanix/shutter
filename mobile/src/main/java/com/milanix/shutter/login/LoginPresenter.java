package com.milanix.shutter.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.App;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.core.MessageSubscriber;
import com.milanix.shutter.notification.model.NotificationMessagingService;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Login presenter
 *
 * @author milan
 */
public class LoginPresenter extends AbstractPresenter<LoginContract.View> implements LoginContract.Presenter {
    private final App app;
    private final FirebaseAuth auth;
    private final MessageSubscriber messageSubscriber;

    @Inject
    public LoginPresenter(LoginContract.View view, App app, FirebaseAuth auth, MessageSubscriber messageSubscriber) {
        super(view);
        this.app = app;
        this.auth = auth;
        this.messageSubscriber = messageSubscriber;
    }

    @Override
    public void subscribe() {
        view.showProgress();

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    view.setSessionAvailable();
                } else {
                    view.hideProgress();
                    Timber.d("onAuthStateChanged:signed_out");
                }
            }
        });
    }

    @Override
    public void login(final Login login) {
        view.showProgress();

        auth.signInWithEmailAndPassword(login.getUsername(), login.getPassword()).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult result) {
                        final FirebaseUser user = result.getUser();

                        if (null != user) {
                            subscribeNotifications();
                            app.createUserComponent(user);
                            view.setSessionAvailable();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                view.hideProgress();
                view.handleInvalidLogin();
            }
        });
    }

    private void subscribeNotifications() {
        messageSubscriber.subscribe(NotificationMessagingService.NOTIFICATIONS);
    }

}
