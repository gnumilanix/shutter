package com.milanix.shutter.feed.favorite;

import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.core.IStore;
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
public class FavoriteListPresenter extends AbstractPresenter<FavoriteListContract.View> implements FavoriteListContract.Presenter {
    private final Query favoriteListQuery = new Query.Builder().setType(Query.Type.SELF).build();
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
    public FavoriteListPresenter(FavoriteListContract.View view, IFeedRepository repository) {
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
