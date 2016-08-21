package com.milanix.shutter.feed.model;

import com.milanix.shutter.specs.IStore;

import java.util.List;

/**
 * Contract for classes providing feed data
 *
 * @author milan
 */
public interface IFeedStore extends IStore {
    void getFeed(long feedId, Callback<Feed> callback);

    void putFeed(Feed feed, Callback<Feed> callback);

    void deleteFeed(Feed feed, Callback<Void> callback);

    void getFeeds(Callback<List<Feed>> callback);

    void putFeeds(List<Feed> feeds, Callback<List<Feed>> callback);

    void deleteFeeds(List<Feed> feeds, Callback<Void> callback);
}
