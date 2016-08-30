package com.milanix.shutter.core;

import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;

import javax.inject.Inject;

/**
 * Class that can schedule or cancel {@link com.firebase.jobdispatcher.Job}
 *
 * @author milan
 */
public class JobScheduler {
    private final Driver driver;
    private final FirebaseJobDispatcher dispatcher;

    @Inject
    public JobScheduler(Driver driver, FirebaseJobDispatcher jobDispatcher) {
        this.driver = driver;
        this.dispatcher = jobDispatcher;
    }

    /**
     * Schedules given job if google play service is available
     *
     * @param job to schedule
     * @return true on success, otherwise false
     */
    public boolean schedule(@NonNull Job job) {
        if (isAvailable()) {
            dispatcher.schedule(job);

            return true;
        }

        return false;
    }

    /**
     * Removes given job
     *
     * @param tag running job tag to cancel
     * @return true on success, otherwise false
     */
    public boolean cancel(@NonNull String tag) {
        if (isAvailable()) {
            dispatcher.cancel(tag);

            return true;
        }

        return false;
    }

    public void cancelAll() {
        if (isAvailable()) {
            dispatcher.cancelAll();
        }
    }

    /**
     * Returns if job scheduling is available
     *
     * @return true if available
     */
    public boolean isAvailable() {
        return driver.isAvailable();
    }
}
