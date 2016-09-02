package com.milanix.shutter.feed.detail;

import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.feed.FeedModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing feed related dependencies
 *
 * @author milan
 */
@Module
public class PostDetailModule {
    private final PostDetailContract.View view;

    public PostDetailModule(PostDetailContract.View view) {
        this.view = view;
    }

    @Provides
    public PostDetailContract.View provideView() {
        return view;
    }

    @Provides
    public PostDetailContract.Presenter providePresenter(PostDetailContract.View view,
                                                         FirebaseDatabase database,
                                                         @Named(FeedModule.POST_ID) String feedId) {
        return new PostDetailPresenter(view, database, feedId);
    }
}
