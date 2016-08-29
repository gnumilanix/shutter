package com.milanix.shutter.feed.detail;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.feed.model.Feed;

/**
 * Contract for feed related implementations
 *
 * @author milan
 */
public interface FeedDetailContract {
    interface View extends IView {
        void showFeed(Feed feed);

        void handleFeedRefreshError();
    }

    interface Presenter extends IPresenter {
        void getFeed();

        void refreshFeed();
    }
}
