package com.milanix.shutter;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.Task;

import javax.inject.Inject;

/**
 * Class that can schedule or cancel {@link com.google.android.gms.gcm.Task}
 *
 * @author milan
 */
public class TaskScheduler {
    private final GoogleApiAvailability googleApiAvailability;
    private final Context context;

    @Inject
    public TaskScheduler(Context context) {
        this.context = context;
        googleApiAvailability = GoogleApiAvailability.getInstance();
    }

    /**
     * Schedules given task if google play service is available
     *
     * @param task to schedule
     * @return true on success, otherwise false
     */
    public boolean schedule(Task task) {
        final int result = googleApiAvailability.isGooglePlayServicesAvailable(context);

        if (result == ConnectionResult.SUCCESS) {
            GcmNetworkManager.getInstance(context).schedule(task);

            return true;
        }

        return false;
    }

    /**
     * Removes given task
     *
     * @param service running task to cancel
     * @return true on success, otherwise false
     */
    public <T extends GcmTaskService> boolean cancel(Class<T> service) {
        final int result = googleApiAvailability.isGooglePlayServicesAvailable(context);

        if (result == ConnectionResult.SUCCESS) {
            GcmNetworkManager.getInstance(context).cancelAllTasks(service);

            return true;
        }

        return false;
    }
}
