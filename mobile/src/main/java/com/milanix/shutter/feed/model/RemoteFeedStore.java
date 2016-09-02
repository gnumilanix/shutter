package com.milanix.shutter.feed.model;

import java.util.List;

import javax.inject.Inject;

/**
 * Store that provide feed data from remote data source
 *
 * @author milan
 */
public class RemoteFeedStore implements IFeedStore {

    @Inject
    public RemoteFeedStore() {
    }

    @Override
    public void getFeed(long feedId, final Callback<Feed> callback) {

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
    public void getFeeds(Query query, final Callback<List<Feed>> callback) {

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
