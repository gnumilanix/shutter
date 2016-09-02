package com.milanix.shutter.feed.detail;

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
    }

    interface Presenter extends IPresenter {
    }
}
