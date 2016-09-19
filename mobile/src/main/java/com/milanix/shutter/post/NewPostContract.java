package com.milanix.shutter.post;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract defining new post related view/presenter implementations
 *
 * @author milan
 */
public interface NewPostContract {
    interface View extends IView {
        void selectPostImage();

        void completePublishPost();

        void handleUploadPostError();

        void handlePublishPostError();

        void showProgress();

        void hideProgress();

    }

    interface Presenter extends IPresenter {
        void publishPost(PostModel post);

    }
}
