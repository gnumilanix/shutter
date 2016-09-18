package com.milanix.shutter.feed.list;

import com.google.firebase.database.ChildEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract defining feed list related view/presenter implementations
 *
 * @author milan
 */
public interface FeedListContract {
    interface View extends IView {
        void openProfile(android.view.View view, String authorId);

        void openFeed(android.view.View view, String feedId);

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener childEventListener);

        void unsubscribe(ChildEventListener childEventListener);

        void refreshFeeds();
    }
}
