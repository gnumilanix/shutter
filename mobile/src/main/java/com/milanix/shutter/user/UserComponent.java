package com.milanix.shutter.user;

import com.milanix.shutter.dependencies.scope.UserScope;
import com.milanix.shutter.feed.FeedComponent;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.favorite.FavoriteListComponent;
import com.milanix.shutter.feed.favorite.FavoriteListModule;
import com.milanix.shutter.feed.list.FeedListComponent;
import com.milanix.shutter.feed.list.FeedListModule;
import com.milanix.shutter.feed.model.FeedDataModule;
import com.milanix.shutter.feed.model.FeedSyncService;
import com.milanix.shutter.notification.NotificationListComponent;
import com.milanix.shutter.notification.NotificationListModule;
import com.milanix.shutter.notification.model.NotificationDataModule;
import com.milanix.shutter.notification.model.NotificationSyncService;

import dagger.Subcomponent;

/**
 * Component that binds login related component and provide dependencies to it
 *
 * @author milan
 */
@UserScope
@Subcomponent(modules = {UserModule.class, FeedDataModule.class, NotificationDataModule.class})
public interface UserComponent {
    FeedComponent with(FeedModule module);

    FeedListComponent with(FeedListModule module);

    FavoriteListComponent with(FavoriteListModule module);

    NotificationListComponent with(NotificationListModule module);

    void inject(FeedSyncService service);

    void inject(NotificationSyncService service);
}
