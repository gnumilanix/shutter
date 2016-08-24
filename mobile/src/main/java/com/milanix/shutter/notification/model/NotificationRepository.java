package com.milanix.shutter.notification.model;

import com.android.annotations.Nullable;
import com.milanix.shutter.dependencies.qualifier.Local;
import com.milanix.shutter.dependencies.qualifier.Remote;

import java.util.List;

/**
 * Repository that provide notification data
 *
 * @author milan
 */
public class NotificationRepository implements INotificationRepository {
    private final INotificationStore localStore;
    private final INotificationStore remoteStore;

    public NotificationRepository(@Local INotificationStore localStore, @Remote INotificationStore remoteStore) {
        this.localStore = localStore;
        this.remoteStore = remoteStore;
    }

    @Override
    public void refreshNotifications(@Nullable final Callback<List<Notification>> callback) {
        remoteStore.getNotifications(new Callback<List<Notification>>() {
            @Override
            public void onSuccess(List<Notification> result) {
                localStore.putNotifications(result, null);

                if (null != callback)
                    callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                if (null != callback)
                    callback.onFailure(t);
            }
        });
    }

    @Override
    public void getNotification(String notificationId, Callback<Notification> callback) {
        localStore.getNotification(notificationId, callback);
    }

    @Override
    public void getNotifications(final Callback<List<Notification>> callback) {
        localStore.getNotifications(new Callback<List<Notification>>() {
            @Override
            public void onSuccess(List<Notification> result) {
                if (result.isEmpty())
                    onFailure(new Exception("No feeds found"));
                else
                    callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                refreshNotifications(callback);
            }
        });
    }

    @Override
    public void putNotifications(List<Notification> notifications, Callback<List<Notification>> callback) {
        localStore.putNotifications(notifications, callback);
    }

    @Override
    public void putNotification(Notification notification, final Callback<Notification> callback) {
        remoteStore.putNotification(notification, new Callback<Notification>() {
            @Override
            public void onSuccess(Notification result) {
                localStore.putNotification(result, null);

                if (null != callback)
                    callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                if (null != callback)
                    callback.onFailure(t);
            }
        });
    }
}
