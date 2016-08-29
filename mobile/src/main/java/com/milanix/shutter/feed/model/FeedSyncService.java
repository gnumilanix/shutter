package com.milanix.shutter.feed.model;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.milanix.shutter.App;
import com.milanix.shutter.user.UserComponent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Implementation of {@link JobService} for syncing feeds
 *
 * @author milan
 */
public class FeedSyncService extends JobService {
    @Inject
    IFeedRepository feedRepository;

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
            feedRepository.refreshFeeds(new Query.Builder().setType(Query.Type.SELF).build(), null);
            feedRepository.refreshFeeds(new Query.Builder().setType(Query.Type.SELF).setFavorite(true).build(), null);
            feedRepository.refreshFeeds(new Query.Builder().setType(Query.Type.PUBLIC).build(), null);
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
