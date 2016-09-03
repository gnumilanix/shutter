package com.milanix.shutter.feed.detail;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.feed.model.Post;

/**
 * Contract for feed related implementations
 *
 * @author milan
 */
public interface PostDetailContract {
    interface View extends IView {
        void showPost(Post post);

        void handlePostRetrieveError();

        void share(String title, String message, Uri imageUri);

        void handlePostShareError();

        void showProgress();

        void hideProgress();

        void completeMarkFavorite();

        void handleMarkFavoriteError();

        void openProfile(String authorId);
    }

    interface Presenter extends IPresenter {
        void share(Post post);

        void markFavorite(Post post);

        FirebaseUser getUser();
    }
}
