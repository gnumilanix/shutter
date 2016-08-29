package com.milanix.shutter.feed.model;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

import static com.milanix.shutter.core.RealmHelper.invokeCallback;
import static com.milanix.shutter.core.RealmHelper.where;

/**
 * Store that provide feed data from local data source
 *
 * @author milan
 */
public class LocalFeedStore implements IFeedStore {
    @Inject
    public LocalFeedStore() {
    }

    @Override
    public void getFeed(long feedId, Callback<Feed> callback) {
        invokeCallback(Realm.getDefaultInstance().where(Feed.class).equalTo(Feed.FIELD_ID, feedId).findFirst(),
                callback);
    }

    @Override
    public void putFeed(Feed feed, Callback<Feed> callback) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(feed);
        realm.commitTransaction();

        callback.onSuccess(feed);
    }

    @Override
    public void deleteFeed(long feedId, Callback<Void> callback) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Feed.class).equalTo(Feed.FIELD_ID, feedId).findAll().deleteAllFromRealm();
        realm.commitTransaction();

        callback.onSuccess(null);
    }

    @Override
    public void getFeeds(Query query, Callback<List<Feed>> callback) {
        // TODO: 28/8/2016 add query when changing to real server
        invokeCallback(Realm.getDefaultInstance().where(Feed.class).findAll(), callback);
    }

    @Override
    public void putFeeds(List<Feed> feeds, Callback<List<Feed>> callback) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(feeds);
        realm.commitTransaction();

        if (null != callback)
            callback.onSuccess(feeds);
    }

    @Override
    public void deleteFeeds(List<Long> feedIds, Callback<Void> callback) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        where(realm.where(Feed.class), Feed.FIELD_ID, feedIds).findAll().deleteAllFromRealm();
        realm.commitTransaction();

        callback.onSuccess(null);
    }
}
