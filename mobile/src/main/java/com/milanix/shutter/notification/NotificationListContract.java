package com.milanix.shutter.notification;

import com.google.firebase.database.ChildEventListener;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.notification.model.Notification;

/**
 * Contract for notification lsit related implementations
 *
 * @author milan
 */
public interface NotificationListContract {
    interface View extends IView {

        void showProgress();

        void hideProgress();

        void openNotification(Notification notification);
    }

    interface Presenter extends IPresenter {
        void subscribe(ChildEventListener notificationEventListener);

        void unsubscribe(ChildEventListener notificationEventListener);

        void markRead(Notification notification);

        void refreshNotification();
    }
}
