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
    public static final String POST_ID = "_post_id";

    private final String postId;

    public FeedModule(String postId) {
        this.postId = postId;
    }

    @Provides
    @Named(POST_ID)
    public String providePostId() {
        return postId;
    }
}
