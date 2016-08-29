package com.milanix.shutter.notification.model;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.milanix.shutter.dependencies.qualifier.Local;
import com.milanix.shutter.dependencies.qualifier.Remote;
import com.milanix.shutter.dependencies.scope.UserScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Module providing notification data related dependencies
 *
 * @author milan
 */
@Module
public class NotificationDataModule {

    @Provides
    @UserScope
    public NotificationApi provideNotificationApi(Retrofit retrofit) {
        return retrofit.create(NotificationApi.class);
    }

    @Provides
    @Local
    @UserScope
    public INotificationStore provideLocalNotificationStore() {
        return new LocalNotificationStore();
    }

    @Provides
    @Remote
    @UserScope
    public INotificationStore provideRemoteNotificationStore(NotificationApi feedApi) {
        return new RemoteNotificationStore(feedApi);
    }

    @Provides
    @UserScope
    public INotificationRepository provideNotificationRepository(@Local INotificationStore local, @Remote INotificationStore remote) {
        return new NotificationRepository(local, remote);
    }

    @Provides
    @UserScope
    @Named(NotificationMessagingService.NOTIFICATIONS)
    public Job provideNotificationSyncJob(FirebaseJobDispatcher dispatcher) {
        return dispatcher.newJobBuilder()
                .setService(NotificationSyncService.class)
                .setTag(NotificationMessagingService.NOTIFICATIONS)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTrigger(Trigger.NOW)
                .setLifetime(Lifetime.FOREVER)
                .build();
    }
}
