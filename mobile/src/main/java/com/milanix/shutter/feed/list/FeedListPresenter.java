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
            if (isActive()) {
                view.hideProgress();
                view.showFeeds(result);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            if (isActive()) {
                view.hideProgress();
                view.handleFeedRefreshError();
            }
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
        if (isActive()) {
            view.showProgress();
        }

        repository.getFeeds(favoriteListQuery, feedsCallback);
    }

    @Override
    public void refreshFeeds() {
        if (isActive()) {
            view.showProgress();
        }

        repository.refreshFeeds(favoriteListQuery, feedsCallback);
    }

    @Override
    public void subscribe() {

    }
}
