package com.milanix.shutter.notification;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.notification.model.Notification;

import java.util.List;

/**
 * Contract for notification lsit related implementations
 *
 * @author milan
 */
public interface NotificationListContract {
    interface View extends IView {
        void showNotifications(List<Notification> notifications);

        void handleNotificationsRefreshError();

        void showProgress();

        void hideProgress();

        void openNotification(Notification notification);
    }

    interface Presenter extends IPresenter {
        void getNotifications();

        void markRead(Notification notification);
    }
}
