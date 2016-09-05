package com.milanix.shutter.feed.list;

import com.google.firebase.database.ChildEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract for feeds related implementations
 *
 * @author milan
 */
public interface FeedListContract {
    interface View extends IView {
        void openProfile(String authorId);

        void openFeed(String feedId);

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener childEventListener);

        void unsubscribe(ChildEventListener childEventListener);

        void refreshFeeds();
    }
}
