package com.milanix.shutter.notification.model;

import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
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
    public static final String NOTIFICATIONS = "_NOTIFICATIONS";

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
    @Named(NOTIFICATIONS)
    public Task provideNotificationTask() {
        return new PeriodicTask.Builder().
                setService(NotificationSyncService.class).
                setPeriod(4 * 60 * 60).
                setFlex(15 * 60).
                setTag(NOTIFICATIONS).
                setUpdateCurrent(true).
                setPersisted(true).
                build();
    }
}
