package com.milanix.shutter.feed.list.presenter;

import com.milanix.shutter.feed.list.FeedListContract;
import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.feed.model.IFeedRepository;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.core.IStore;

import java.util.List;

import javax.inject.Inject;

/**
 * Feeds presenter
 *
 * @author milan
 */
public class FeedListPresenter extends AbstractPresenter<FeedListContract.View> implements FeedListContract.Presenter {
    private final IStore.Callback<List<Feed>> feedsCallback = new IStore.Callback<List<Feed>>() {
        @Override
        public void onSuccess(List<Feed> result) {
            view.showFeeds(result);
        }

        @Override
        public void onFailure(Throwable t) {
            view.handleFeedRefreshError();
        }
    };
    private final IFeedRepository repository;

    @Inject
    public FeedListPresenter(FeedListContract.View view, IFeedRepository repository) {
        super(view);
        this.repository = repository;
    }

    @Override
    public void getFeeds() {
        repository.getFeeds(feedsCallback);
    }

    @Override
    public void refreshFeeds() {
        repository.refreshFeeds(feedsCallback);
    }

    @Override
    public void subscribe() {

    }
}
