package com.milanix.shutter.notification.list;

import com.google.firebase.database.ChildEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.notification.model.Notification;

/**
 * Contract defining notification list related view/presenter implementations
 *
 * @author milan
 */
public interface NotificationListContract {
    interface View extends IView {

        void openPost(android.view.View view, Notification notification);

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
