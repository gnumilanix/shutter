package com.milanix.shutter.user.model;

import android.accounts.Account;

import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.user.account.IAccountStore;

import javax.inject.Inject;

import io.realm.Realm;

import static com.milanix.shutter.core.RestCallback.invokeCallback;

/**
 * Store that provide user data from local data source
 *
 * @author milan
 */
public class LocalUserStore implements IUserStore {

    private IAccountStore accountStore;

    @Inject
    public LocalUserStore(IAccountStore accountStore) {
        this.accountStore = accountStore;
    }

    @Override
    public void getSelf(Callback<User> callback) {
        final Account account = accountStore.getDefaultAccount();

        if (null == account) {
            callback.onFailure(new NullPointerException("Self not found"));
        } else {
            getUser(account.name, callback);
        }
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
    public void deleteUser(String userId) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Feed.class).equalTo(User.FIELD_ID, userId).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }
}
