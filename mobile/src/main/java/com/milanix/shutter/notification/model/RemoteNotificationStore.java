package com.milanix.shutter.notification.model;

import com.milanix.shutter.core.RestCallback;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * Store that provide notification data from remote data source
 *
 * @author milan
 */
public class RemoteNotificationStore implements INotificationStore {
    private final NotificationApi notificationApi;

    @Inject
    public RemoteNotificationStore(NotificationApi notificationApi) {
        this.notificationApi = notificationApi;
    }

    @Override
    public void getNotification(String notificationId, Callback<Notification> callback) {
        throw new UnsupportedOperationException("Get operation not supported in this store");
    }

    @Override
    public void getNotifications(final Callback<List<Notification>> callback) {
        notificationApi.getNotifications().enqueue(new RestCallback<List<Notification>>() {
            @Override
            public void onResponse(Response<List<Notification>> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void putNotifications(List<Notification> notifications, Callback<List<Notification>> callback) {
        throw new UnsupportedOperationException("Put operation not supported in this store");
    }

    @Override
    public void putNotification(Notification notification, final Callback<Notification> callback) {
        notificationApi.putNotification(notification).enqueue(new RestCallback<Notification>() {
            @Override
            public void onResponse(Response<Notification> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
