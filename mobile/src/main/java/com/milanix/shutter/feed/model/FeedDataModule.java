package com.milanix.shutter.feed.model;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.milanix.shutter.dependencies.qualifier.Local;
import com.milanix.shutter.dependencies.scope.UserScope;
import com.milanix.shutter.notification.model.NotificationMessagingService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Module providing feed data related dependencies
 *
 * @author milan
 */
@Module
public class FeedDataModule {

    @Provides
    @UserScope
    public FeedApi provideFeedApi(Retrofit retrofit) {
        return retrofit.create(FeedApi.class);
    }

    @Provides
    @Local
    @UserScope
    public IFeedStore provideLocalFeedStore() {
        return new RemoteFeedStore();
    }

    @Provides
    @UserScope
    public IFeedRepository provideFeedRepository(@Local IFeedStore local) {
        return new FeedRepository(local);
    }

    @Provides
    @UserScope
    @Named(NotificationMessagingService.FEEDS)
    public Job provideFeedSyncJob(FirebaseJobDispatcher dispatcher) {
        return dispatcher.newJobBuilder()
                .setService(FeedSyncService.class)
                .setTag(NotificationMessagingService.FEEDS)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .setTrigger(Trigger.executionWindow(0, 60))
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .build();
    }
}
