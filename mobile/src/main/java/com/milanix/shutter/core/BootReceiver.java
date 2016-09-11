package com.milanix.shutter.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.milanix.shutter.notification.NotificationService;

/**
 * Broadcast receiver that listens to boot completed event
 *
 * @author milan
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
            context.startService(new Intent(context, NotificationService.class));
    }
}