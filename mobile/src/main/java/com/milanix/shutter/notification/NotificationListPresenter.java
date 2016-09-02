package com.milanix.shutter.notification;

import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.core.specification.IStore;
import com.milanix.shutter.notification.model.INotificationRepository;
import com.milanix.shutter.notification.model.Notification;

import java.util.List;

import javax.inject.Inject;

/**
 * Notification list presenter
 *
 * @author milan
 */
public class NotificationListPresenter extends AbstractPresenter<NotificationListContract.View> implements NotificationListContract.Presenter {
    private final IStore.Callback<List<Notification>> notificationsCallback = new IStore.Callback<List<Notification>>() {
        @Override
        public void onSuccess(List<Notification> result) {
            if (isActive()) {
                view.hideProgress();
                view.showNotifications(result);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            if (isActive()) {
                view.hideProgress();
                view.handleNotificationsRefreshError();
            }
        }
    };

    private final INotificationRepository repository;

    @Inject
    public NotificationListPresenter(NotificationListContract.View view, INotificationRepository repository) {
        super(view);
        this.repository = repository;
    }

    @Override
    public void getNotifications() {
        if (isActive()) {
            view.showProgress();
            repository.getNotifications(notificationsCallback);
        }
    }

    @Override
    public void refreshNotifications() {
        if (isActive()) {
            view.showProgress();
            repository.refreshNotifications(notificationsCallback);
        }
    }

    @Override
    public void markRead(Notification notification) {
        if (!notification.isRead()) {
            final Notification update = new Notification(notification);
            update.setRead(true);

            repository.putNotification(update, null);
        }
    }
}
