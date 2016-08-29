package com.milanix.shutter.feed.favorite;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.feed.model.Feed;

import java.util.List;

/**
 * Contract for favorite feeds related implementations
 *
 * @author milan
 */
public interface FavoriteListContract {
    interface View extends IView {
        void showFeeds(List<Feed> feeds);

        void openFeed(long feedId);

        void handleFeedRefreshError();

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void getFeeds();

        void refreshFeeds();
    }
}
