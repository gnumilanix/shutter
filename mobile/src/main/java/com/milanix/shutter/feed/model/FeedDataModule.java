package com.milanix.shutter.feed.model;

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
 * Module providing feed data related dependencies
 *
 * @author milan
 */
@Module
public class FeedDataModule {
    public static final String FEEDS = "_FEEDS";

    @Provides
    @UserScope
    public FeedApi provideFeedApi(Retrofit retrofit) {
        return retrofit.create(FeedApi.class);
    }

    @Provides
    @Local
    @UserScope
    public IFeedStore provideLocalFeedStore() {
        return new LocalFeedStore();
    }

    @Provides
    @Remote
    @UserScope
    public IFeedStore provideRemoteFeedStore(FeedApi feedApi) {
        return new RemoteFeedStore(feedApi);
    }

    @Provides
    @UserScope
    public IFeedRepository provideFeedRepository(@Local IFeedStore local, @Remote IFeedStore remote) {
        return new FeedRepository(local, remote);
    }

    @Provides
    @UserScope
    @Named(FEEDS)
    public Job provideFeedSyncJob(FirebaseJobDispatcher dispatcher) {
        return dispatcher.newJobBuilder()
                .setService(FeedSyncService.class)
                .setTag(FEEDS)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .setTrigger(Trigger.executionWindow(0, 60))
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .build();
    }
}
