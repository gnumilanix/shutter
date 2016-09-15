package com.milanix.shutter;

import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.core.MessageSubscriber;
import com.milanix.shutter.dependencies.component.AppComponent;
import com.milanix.shutter.dependencies.component.DaggerAppComponent;
import com.milanix.shutter.dependencies.module.AppModule;
import com.milanix.shutter.notification.NotificationService;
import com.milanix.shutter.user.UserComponent;
import com.milanix.shutter.user.UserModule;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * {@link android.app.Application} class for this app
 *
 * @author milan
 */
public class App extends MultiDexApplication {
    protected AppComponent appComponent;
    protected UserComponent userComponent;

    @Inject
    protected Timber.Tree logTree;
    @Inject
    protected MessageSubscriber messageSubscriber;

    @Override
    public void onCreate() {
        super.onCreate();

        createAppComponent(this).inject(this);
        Timber.plant(logTree);
    }

    public synchronized AppComponent createAppComponent(App app) {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(app)).build();

        return appComponent;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public synchronized UserComponent createUserComponent(FirebaseUser user) {
        userComponent = getAppComponent().with(new UserModule(user));
        subscribeNotifications();

        return userComponent;
    }

    public UserComponent getUserComponent() {
        createUserComponent();
        return userComponent;
    }

    private void createUserComponent() {
        if (null == userComponent) {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (null != user)
                createUserComponent(user);
        }
    }

    public void releaseUserComponent() {
        unsubscribeNotifications();
        userComponent = null;
    }

    private void subscribeNotifications() {
        messageSubscriber.subscribe(NotificationService.NOTIFICATIONS);
        startService(new Intent(this, NotificationService.class));
    }

    private void unsubscribeNotifications() {
        messageSubscriber.unsubscribe(NotificationService.NOTIFICATIONS);
        stopService(new Intent(this, NotificationService.class));
    }

    public void initializeFacebookSDK() {
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(this);
        }
    }
}
