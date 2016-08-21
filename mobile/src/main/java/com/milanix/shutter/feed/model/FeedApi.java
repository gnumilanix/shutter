package com.milanix.shutter.feed.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit interface providing feed related API
 *
 * @author milan
 */
public interface FeedApi {
    @GET("/api/v1/feeds/{feedId}")
    Call<Feed> getFeed(@Path("feedId") long feedId);

    @GET("/api/v1/feeds")
    Call<List<Feed>> getFeeds();
}
