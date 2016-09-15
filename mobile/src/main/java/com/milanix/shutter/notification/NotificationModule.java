package com.milanix.shutter.notification;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

/**
 * Module providing notification related dependencies
 *
 * @author milan
 */
@Module
public class NotificationModule {
    public static final String NOTIFICATION_JOB = "_notification_job";

    @Provides
    @Reusable
    public NotificationManagerCompat provideNotificationManager(Context context) {
        return NotificationManagerCompat.from(context);
    }

    @Provides
    @Named(NOTIFICATION_JOB)
    @Reusable
    public Job provideNotificationJob(FirebaseJobDispatcher dispatcher) {
        return dispatcher.newJobBuilder()
                .setService(NotificationJobService.class)
                .setTag(NotificationService.NOTIFICATIONS)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTrigger(Trigger.executionWindow(0, 5 * 60))
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .build();
    }
}
