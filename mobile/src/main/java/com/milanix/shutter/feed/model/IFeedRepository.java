package com.milanix.shutter.feed.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Contract for classes acting as feed repository
 *
 * @author milan
 */
public interface IFeedRepository extends IFeedStore {
    void refreshFeeds(@NonNull Query query, @Nullable Callback<List<Feed>> callback);

    void refreshFeed(long feedId, @Nullable Callback<Feed> callback);
}
