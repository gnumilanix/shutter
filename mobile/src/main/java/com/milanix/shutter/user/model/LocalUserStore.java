package com.milanix.shutter.user.model;

import com.milanix.shutter.feed.model.Feed;

import javax.inject.Inject;

import io.realm.Realm;

import static com.milanix.shutter.core.RestCallback.invokeCallback;

/**
 * Store that provide user data from local data source
 *
 * @author milan
 */
public class LocalUserStore implements IUserStore {

    @Inject
    public LocalUserStore() {
    }

    @Override
    public void getUser(String userId, Callback<User> callback) {
        invokeCallback(Realm.getDefaultInstance().where(User.class).equalTo(User.FIELD_ID, userId).findFirst(),
                callback);
    }

    @Override
    public void putUser(User user) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(user);
        realm.commitTransaction();
    }

    @Override
    public void deleteUser(User user) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Feed.class).equalTo(User.FIELD_ID, user.getEmail()).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }
}
