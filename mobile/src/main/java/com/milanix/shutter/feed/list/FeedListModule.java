package com.milanix.shutter.feed.list;

import com.milanix.shutter.feed.list.presenter.FeedListPresenter;
import com.milanix.shutter.feed.model.IFeedRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing feed related dependencies
 *
 * @author milan
 */
@Module
public class FeedListModule {
    private final FeedListContract.View view;

    public FeedListModule(FeedListContract.View view) {
        this.view = view;
    }

    @Provides
    public FeedListContract.View provideFeedView() {
        return view;
    }

    @Provides
    public FeedListContract.Presenter provideFeedPresenter(FeedListContract.View view, IFeedRepository feedRepository) {
        return new FeedListPresenter(view, feedRepository);
    }
}
