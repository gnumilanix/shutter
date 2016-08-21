package com.milanix.shutter.feed.model;

import com.android.annotations.Nullable;
import com.milanix.shutter.dependencies.qualifier.Local;
import com.milanix.shutter.dependencies.qualifier.Remote;

import java.util.List;

/**
 * Repository that provide feed data
 *
 * @author milan
 */
public class FeedRepository implements IFeedRepository {
    private final IFeedStore localStore;
    private final IFeedStore remoteStore;

    public FeedRepository(@Local IFeedStore localStore, @Remote IFeedStore remoteStore) {
        this.localStore = localStore;
        this.remoteStore = remoteStore;
    }

    @Override
    public void getFeed(final long feedId, final Callback<Feed> callback) {
        localStore.getFeed(feedId, new Callback<Feed>() {
            @Override
            public void onSuccess(Feed result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                remoteStore.getFeed(feedId, callback);
            }
        });
    }

    @Override
    public void putFeed(final Feed feed, final Callback<Feed> callback) {
        localStore.putFeed(feed, callback);
    }

    @Override
    public void deleteFeed(final Feed feed, final Callback<Void> callback) {
        localStore.deleteFeed(feed, callback);
    }

    @Override
    public void getFeeds(final Callback<List<Feed>> callback) {
        localStore.getFeeds(new Callback<List<Feed>>() {
            @Override
            public void onSuccess(List<Feed> result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                refreshFeeds(callback);
            }
        });
    }

    @Override
    public void putFeeds(final List<Feed> feeds, final Callback<List<Feed>> callback) {
        localStore.putFeeds(feeds, callback);
    }

    @Override
    public void deleteFeeds(final List<Feed> feeds, final Callback<Void> callback) {
        localStore.deleteFeeds(feeds, callback);
    }

    @Override
    public void refreshFeeds(final Callback<List<Feed>> callback) {
        remoteStore.getFeeds(new Callback<List<Feed>>() {
            @Override
            public void onSuccess(List<Feed> result) {
                localStore.putFeeds(result, null);

                if (null != callback)
                    callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                if (null != callback)
                    callback.onFailure(t);
            }
        });
    }

    @Override
    public void refreshFeed(long feedId, @Nullable final Callback<Feed> callback) {
        remoteStore.getFeed(feedId, new Callback<Feed>() {
            @Override
            public void onSuccess(Feed result) {
                localStore.putFeed(result, null);

                if (null != callback)
                    callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                if (null != callback)
                    callback.onFailure(t);
            }
        });
    }
}
