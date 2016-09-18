package com.milanix.shutter.feed;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing post related dependencies
 *
 * @author milan
 */
@Module
public class PostModule {
    public static final String POST_ID = "_post_id";

    private final String postId;

    public PostModule(String postId) {
        this.postId = postId;
    }

    @Provides
    @Named(POST_ID)
    public String providePostId() {
        return postId;
    }
}
