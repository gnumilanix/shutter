package com.milanix.shutter.feed.list;

import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.core.specification.IStore;
import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.feed.model.IFeedRepository;
import com.milanix.shutter.feed.model.Query;

import java.util.List;

import javax.inject.Inject;

/**
 * Feeds presenter
 *
 * @author milan
 */
public class FeedListPresenter extends AbstractPresenter<FeedListContract.View> implements FeedListContract.Presenter {
    private final Query favoriteListQuery = new Query.Builder().setType(Query.Type.PUBLIC).build();
    private final IStore.Callback<List<Feed>> feedsCallback = new IStore.Callback<List<Feed>>() {
        @Override
        public void onSuccess(List<Feed> result) {
            view.hideProgress();
            view.showFeeds(result);
        }

        @Override
        public void onFailure(Throwable t) {
            view.hideProgress();
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
        view.showProgress();
        repository.getFeeds(favoriteListQuery, feedsCallback);
    }

    @Override
    public void refreshFeeds() {
        view.showProgress();
        repository.refreshFeeds(favoriteListQuery, feedsCallback);
    }

    @Override
    public void subscribe() {

    }
}
