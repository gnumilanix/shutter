package com.milanix.shutter.user.profile.posts;

import com.google.firebase.database.ChildEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract defining posts related view/presenter implementations
 *
 * @author milan
 */
public interface PostListContract {
    interface View extends IView {
        void openPost(android.view.View view, String postId);

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener childEventListener);

        void unsubscribe(ChildEventListener childEventListener);

        void refreshPosts();

    }
}
