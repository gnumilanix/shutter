package com.milanix.shutter;

import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.milanix.shutter.core.MessageSubscriber;
import com.milanix.shutter.dependencies.component.AppComponent;
import com.milanix.shutter.dependencies.component.DaggerAppComponent;
import com.milanix.shutter.dependencies.module.AppModule;
import com.milanix.shutter.feed.FeedComponent;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.notification.model.NotificationMessagingService;
import com.milanix.shutter.user.UserComponent;
import com.milanix.shutter.user.UserModule;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * {@link android.app.Application} class for this app
 *
 * @author milan
 */
public class App extends MultiDexApplication {
    protected AppComponent appComponent;
    protected UserComponent userComponent;
    protected FeedComponent feedComponent;

    @Inject
    protected Timber.Tree logTree;
    @Inject
    protected MessageSubscriber messageSubscriber;

    @Override
    public void onCreate() {
        super.onCreate();

        initSDKs();
        createAppComponent(this).inject(this);

        Timber.plant(logTree);
    }

    private void initSDKs() {
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).schemaVersion(0).deleteRealmIfMigrationNeeded().build());
        Dexter.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
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
        return userComponent;
    }

    public void releaseUserComponent() {
        unsubscribeNotifications();
        userComponent = null;
    }

    public synchronized FeedComponent createFeedComponent(String postId) {
        feedComponent = getUserComponent().with(new FeedModule(postId));

        return feedComponent;
    }

    public FeedComponent getFeedComponent() {
        return feedComponent;
    }

    private void subscribeNotifications() {
        messageSubscriber.subscribe(NotificationMessagingService.NOTIFICATIONS);
    }

    private void unsubscribeNotifications() {
        messageSubscriber.unsubscribe(NotificationMessagingService.NOTIFICATIONS);
    }
}
