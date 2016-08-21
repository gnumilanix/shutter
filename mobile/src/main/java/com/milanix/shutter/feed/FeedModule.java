package com.milanix.shutter.feed;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing feed related dependencies
 *
 * @author milan
 */
@Module
public class FeedModule {
    public static final String FEED_ID = "_feed_id";

    private long feedId;

    public FeedModule(long feedId) {
        this.feedId = feedId;
    }

    @Provides
    @Named(FEED_ID)
    public long provideFeedId() {
        return feedId;
    }
}
