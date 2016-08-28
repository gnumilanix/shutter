package com.milanix.shutter.feed.model;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import javax.inject.Inject;

/**
 * Implementation of {@link JobService} for syncing feeds
 *
 * @author milan
 */
public class FeedSyncService extends JobService {
    @Inject
    IFeedRepository feedRepository;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        try {
            feedRepository.refreshFeeds(new Query.Builder().setType(Query.Type.SELF).build(), null);
            feedRepository.refreshFeeds(new Query.Builder().setType(Query.Type.SELF).setFavorite(true).build(), null);
            feedRepository.refreshFeeds(new Query.Builder().setType(Query.Type.PUBLIC).build(), null);
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
