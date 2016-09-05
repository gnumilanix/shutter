package com.milanix.shutter.feed.favorite;

import com.google.firebase.database.ChildEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.feed.model.Post;

import java.util.List;

/**
 * Contract for favorite feeds related implementations
 *
 * @author milan
 */
public interface FavoriteListContract {
    interface View extends IView {
        void openProfile(android.view.View view, String authorId);

        void showFeeds(List<Post> posts);

        void openFeed(android.view.View view, String feedId);

        void handleFeedRefreshError();

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener childEventListener);

        void unsubscribe(ChildEventListener childEventListener);

        void refreshFavorites();
    }
}
