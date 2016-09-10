package com.milanix.shutter.notification;

import android.content.Intent;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Implementation of {@link JobService} for keeping {@link NotificationService } alive
 *
 * @author milan
 */
public class NotificationJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        startService(new Intent(this, NotificationService.class));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

}

