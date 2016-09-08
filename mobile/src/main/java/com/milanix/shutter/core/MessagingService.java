package com.milanix.shutter.core;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.milanix.shutter.App;
import com.milanix.shutter.user.UserComponent;

/**
 * Implementation of {@link FirebaseMessagingService} used by this app
 *
 * @author milan
 */
public class MessagingService extends FirebaseMessagingService {
    public static final String NOTIFICATIONS = "notifications";


}
