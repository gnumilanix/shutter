package com.milanix.shutter.core;

import android.util.Log;

import timber.log.Timber;

/**
 * Implementation of  {@link timber.log.Timber.Tree} that reports crash logs using {@link FirebaseCrash}
 *
 * @author milan
 */
public class FirebaseLogTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.ERROR || priority == Log.WARN) {
//            if (null == t)
//                FirebaseCrash.logcat(priority, tag, message);
//            else
//                FirebaseCrash.report(t);
        }
    }
}