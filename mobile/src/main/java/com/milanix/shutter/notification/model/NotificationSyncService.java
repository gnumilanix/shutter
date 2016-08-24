package com.milanix.shutter.notification.model;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.milanix.shutter.App;
import com.milanix.shutter.user.UserComponent;

import javax.inject.Inject;

/**
 * Implementation of {@link GcmTaskService} for syncing notifications
 *
 * @author milan
 */
public class NotificationSyncService extends GcmTaskService {
    @Inject
    INotificationRepository notificationRepository;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bind(((App) getApplication()).getUserComponent());
        return super.onStartCommand(intent, flags, startId);
    }

    protected void bind(@Nullable UserComponent component) {
        if (null != component)
            component.inject(this);
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        try {
            notificationRepository.refreshNotifications(null);
            return GcmNetworkManager.RESULT_SUCCESS;
        } catch (Exception e) {
            return GcmNetworkManager.RESULT_FAILURE;
        }
    }
}
