package com.milanix.shutter.feed.model;

import android.support.annotation.NonNull;

import com.milanix.shutter.core.specification.IStore;

import java.util.List;


/**
 * Contract for classes providing feed data
 *
 * @author milan
 */
public interface IFeedStore extends IStore {
    void getFeed(long feedId, Callback<Feed> callback);

    void putFeed(@NonNull Feed feed, Callback<Feed> callback);

    void deleteFeed(long feedId, Callback<Void> callback);

    void getFeeds(@NonNull Query query, Callback<List<Feed>> callback);

    void putFeeds(@NonNull List<Feed> feeds, Callback<List<Feed>> callback);

    void deleteFeeds(@NonNull List<Long> feedIds, Callback<Void> callback);
}
