package com.milanix.shutter.feed.model;

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
    public Task provideFeedTask() {
        return new PeriodicTask.Builder().
                setService(FeedSyncService.class).
                setPeriod(4 * 60 * 60).
                setFlex(15 * 60).
                setTag(FEEDS).
                setUpdateCurrent(true).
                setPersisted(true).
                build();
    }
}
