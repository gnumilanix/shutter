package com.milanix.shutter.notification;

import com.google.firebase.database.ChildEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract for notification lsit related implementations
 *
 * @author milan
 */
public interface NotificationListContract {
    interface View extends IView {

        void openPost(android.view.View view, String notificationId, String postId);

        void openProfile(android.view.View view, String notificationId, String authorId);

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener notificationEventListener);

        void unsubscribe(ChildEventListener notificationEventListener);

        void markRead(String notificationId);

        void refreshNotification();
    }
}
