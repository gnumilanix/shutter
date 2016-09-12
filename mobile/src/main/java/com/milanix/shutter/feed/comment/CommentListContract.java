package com.milanix.shutter.feed.comment;

import com.google.firebase.database.ChildEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.feed.model.Post;

/**
 * Contract for feed related implementations
 *
 * @author milan
 */
public interface CommentListContract {
    interface View extends IView {
        void showPost(Post post);

        void handlePostRetrieveError();

        void handleSendCommentError();

        void openProfile(android.view.View view, String authorId);

        void clearComment();
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener commentEventListener);

        void unsubscribe(ChildEventListener commentEventListener);

        void sendComment(Post post, CommentModel comment);
    }
}
