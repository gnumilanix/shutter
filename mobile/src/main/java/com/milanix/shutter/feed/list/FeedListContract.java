package com.milanix.shutter.feed.list;

import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.core.IPresenter;
import com.milanix.shutter.core.IView;

import java.util.List;

/**
 * Contract for feeds related implementations
 *
 * @author milan
 */
public interface FeedListContract {
    interface View extends IView {
        void showFeeds(List<Feed> feeds);

        void handleFeedRefreshError();
    }

    interface Presenter extends IPresenter {
        void getFeeds();

        void refreshFeeds();
    }
}
