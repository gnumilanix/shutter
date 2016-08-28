package com.milanix.shutter.feed.model;

import com.milanix.shutter.core.RestCallback;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Store that provide feed data from remote data source
 *
 * @author milan
 */
public class RemoteFeedStore implements IFeedStore {
    private final FeedApi feedApi;

    @Inject
    public RemoteFeedStore(FeedApi feedApi) {
        this.feedApi = feedApi;
    }

    @Override
    public void getFeed(long feedId, final Callback<Feed> callback) {
        feedApi.getFeed(feedId).enqueue(new RestCallback<Feed>() {
            @Override
            public void onResponse(Response<Feed> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void putFeed(Feed feed, Callback<Feed> callback) {
        throw new UnsupportedOperationException("Put operation not supported in this store");
    }

    @Override
    public void deleteFeed(long feedId, Callback<Void> callback) {
        throw new UnsupportedOperationException("Delete operation not supported in this store");
    }

    @Override
    public void getFeeds(Query query,final Callback<List<Feed>> callback) {
        feedApi.getFeeds().enqueue(new RestCallback<List<Feed>>() {
            @Override
            public void onResponse(Response<List<Feed>> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void putFeeds(List<Feed> feeds, Callback<List<Feed>> callback) {
        throw new UnsupportedOperationException("Put operation not supported in this store");
    }

    @Override
    public void deleteFeeds(List<Long> feedIds, Callback<Void> callback) {
        throw new UnsupportedOperationException("Delete operation not supported in this store");
    }
}
