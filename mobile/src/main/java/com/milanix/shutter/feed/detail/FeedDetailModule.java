package com.milanix.shutter.feed.detail;

import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.model.IFeedRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing feed related dependencies
 *
 * @author milan
 */
@Module
public class FeedDetailModule {
    private final FeedDetailContract.View view;

    public FeedDetailModule(FeedDetailContract.View view) {
        this.view = view;
    }

    @Provides
    public FeedDetailContract.View provideFeedView() {
        return view;
    }

    @Provides
    public FeedDetailContract.Presenter provideFeedPresenter(FeedDetailContract.View view,
                                                             IFeedRepository feedRepository,
                                                             @Named(FeedModule.FEED_ID) long feedId) {
        return new FeedDetailPresenter(view, feedRepository, feedId);
    }
}
