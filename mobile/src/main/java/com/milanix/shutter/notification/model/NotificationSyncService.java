package com.milanix.shutter.notification.model;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import javax.inject.Inject;

/**
 * Implementation of {@link JobService} for syncing notifications
 *
 * @author milan
 */
public class NotificationSyncService extends JobService {
    @Inject
    INotificationRepository notificationRepository;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        try {
            notificationRepository.refreshNotifications(null);

            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
