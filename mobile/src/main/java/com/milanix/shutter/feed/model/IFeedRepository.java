package com.milanix.shutter.feed.model;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;

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
