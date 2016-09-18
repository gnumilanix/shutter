package com.milanix.shutter.feed.detail;

import android.net.Uri;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.feed.model.Post;

/**
 * Contract defining post detail related view/presenter implementations
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

        void openComment(Post post);

        void toggleViewState();
    }

    interface Presenter extends IPresenter {
        void share(Post post);

        void markFavorite(Post post);
    }
}
