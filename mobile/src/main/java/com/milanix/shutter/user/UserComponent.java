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
import com.milanix.shutter.home.HomeComponent;
import com.milanix.shutter.home.HomeModule;
import com.milanix.shutter.notification.NotificationListComponent;
import com.milanix.shutter.notification.NotificationListModule;
import com.milanix.shutter.notification.model.NotificationDataModule;
import com.milanix.shutter.notification.model.NotificationMessagingService;
import com.milanix.shutter.notification.model.NotificationSyncService;
import com.milanix.shutter.post.NewPostComponent;
import com.milanix.shutter.post.NewPostModule;
import com.milanix.shutter.user.profile.ProfileComponent;
import com.milanix.shutter.user.profile.ProfileModule;

import dagger.Subcomponent;

/**
 * Component that binds login related component and provide dependencies to it
 *
 * @author milan
 */
@UserScope
@Subcomponent(modules = {UserModule.class, FeedDataModule.class, NotificationDataModule.class})
public interface UserComponent {
    HomeComponent with(HomeModule module);

    ProfileComponent with(ProfileModule module);

    NewPostComponent with(NewPostModule module);

    FeedComponent with(FeedModule module);

    FeedListComponent with(FeedListModule module);

    FavoriteListComponent with(FavoriteListModule module);

    NotificationListComponent with(NotificationListModule module);

    void inject(FeedSyncService service);

    void inject(NotificationSyncService service);

    void inject(NotificationMessagingService service);
}
