package com.milanix.shutter;

import android.support.multidex.MultiDexApplication;

import com.milanix.shutter.dependencies.component.AppComponent;
import com.milanix.shutter.dependencies.component.DaggerAppComponent;
import com.milanix.shutter.dependencies.module.AppModule;
import com.milanix.shutter.feed.FeedComponent;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.user.User;
import com.milanix.shutter.user.UserComponent;
import com.milanix.shutter.user.UserModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * {@link android.app.Application} class for this app
 *
 * @author milan
 */
public class App extends MultiDexApplication {
    protected AppComponent appComponent;
    protected UserComponent userComponent;
    protected FeedComponent feedComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
        createAppComponent(this).inject(this);
    }

    private void initRealm() {
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this).schemaVersion(0).deleteRealmIfMigrationNeeded().build());
    }

    public synchronized AppComponent createAppComponent(App app) {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(app)).build();

        return appComponent;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public synchronized UserComponent createUserComponent(User user) {
        userComponent = getAppComponent().with(new UserModule(user));

        return userComponent;
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }

    public void releaseUserComponent() {
        userComponent = null;
    }

    public synchronized FeedComponent createFeedComponent(long feedId) {
        feedComponent = getUserComponent().with(new FeedModule(feedId));

        return feedComponent;
    }

    public FeedComponent getFeedComponent() {
        return feedComponent;
    }
}
