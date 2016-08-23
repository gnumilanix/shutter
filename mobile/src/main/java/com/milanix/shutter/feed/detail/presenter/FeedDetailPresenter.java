package com.milanix.shutter.feed.detail.presenter;

import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.core.IStore;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.detail.FeedDetailContract;
import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.feed.model.IFeedRepository;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Feeds presenter
 *
 * @author milan
 */
public class FeedDetailPresenter extends AbstractPresenter<FeedDetailContract.View> implements FeedDetailContract.Presenter {
    private final IStore.Callback<Feed> feedCallback = new IStore.Callback<Feed>() {
        @Override
        public void onSuccess(Feed result) {
            view.showFeed(result);
        }

        @Override
        public void onFailure(Throwable t) {
            view.handleFeedRefreshError();
        }
    };
    private final IFeedRepository repository;
    private final long feedId;

    @Inject
    public FeedDetailPresenter(FeedDetailContract.View view, IFeedRepository repository, @Named(FeedModule.FEED_ID) long feedId) {
        super(view);
        this.repository = repository;
        this.feedId = feedId;
    }

    @Override
    public void getFeed() {
        repository.getFeed(feedId, feedCallback);
    }

    @Override
    public void refreshFeed() {
        repository.refreshFeed(feedId, feedCallback);
    }

    @Override
    public void subscribe() {

    }
}
