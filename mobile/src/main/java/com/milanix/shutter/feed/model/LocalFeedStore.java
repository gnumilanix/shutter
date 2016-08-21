package com.milanix.shutter.feed.model;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;

import static com.milanix.shutter.core.RestCallback.invokeCallback;

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
    public void deleteFeed(Feed feed, Callback<Void> callback) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Feed.class).equalTo(Feed.FIELD_ID, feed.getId()).findAll().deleteAllFromRealm();
        realm.commitTransaction();

        callback.onSuccess(null);
    }

    @Override
    public void getFeeds(Callback<List<Feed>> callback) {
        invokeCallback(Realm.getDefaultInstance().where(Feed.class).findAll(), callback);
    }

    @Override
    public void putFeeds(List<Feed> feeds, Callback<List<Feed>> callback) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(feeds);
        realm.commitTransaction();

        callback.onSuccess(feeds);
    }

    @Override
    public void deleteFeeds(List<Feed> feeds, Callback<Void> callback) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmQuery<Feed> where = realm.where(Feed.class);

        for (int i = 0, size = feeds.size(); i < size; i++) {
            if (i > 0)
                where.or();

            where.equalTo(Feed.FIELD_ID, feeds.get(i).getId());
        }

        where.findAll().deleteAllFromRealm();
        realm.commitTransaction();

        callback.onSuccess(null);
    }
}
