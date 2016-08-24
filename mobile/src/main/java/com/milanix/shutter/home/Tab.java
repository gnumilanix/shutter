package com.milanix.shutter.home;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.milanix.shutter.home.Tab.FAVORITES;
import static com.milanix.shutter.home.Tab.FEEDS;
import static com.milanix.shutter.home.Tab.NOTIFICATIONS;

/**
 * Definition for possible tabs
 *
 * @author milan
 */
@StringDef({FEEDS, FAVORITES, NOTIFICATIONS})
@Retention(RetentionPolicy.SOURCE)
public @interface Tab {
    String FEEDS = "FEEDS";
    String FAVORITES = "FAVORITES";
    String NOTIFICATIONS = "NOTIFICATIONS";
}
