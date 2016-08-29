package com.milanix.shutter.notification.model;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.milanix.shutter.App;
import com.milanix.shutter.user.UserComponent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Implementation of {@link JobService} for syncing notifications
 *
 * @author milan
 */
public class NotificationSyncService extends JobService {
    @Inject
    INotificationRepository notificationRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        inject(((App) getApplicationContext()).getUserComponent());
    }

    private void inject(UserComponent component) {
        if (null != component)
            component.inject(this);
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        try {
            notificationRepository.refreshNotifications(null);
            return false;
        } catch (Exception e) {
            Timber.i(e, "Error while syncing notifications");
            return true;
        }
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
