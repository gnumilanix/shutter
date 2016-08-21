package com.milanix.shutter.user;

import com.milanix.shutter.dependencies.scope.UserScope;
import com.milanix.shutter.feed.FeedComponent;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.list.FeedListComponent;
import com.milanix.shutter.feed.list.FeedListModule;
import com.milanix.shutter.feed.model.FeedDataModule;
import com.milanix.shutter.feed.model.FeedSyncService;
import com.milanix.shutter.user.UserModule;

import dagger.Subcomponent;

/**
 * Component that binds login related component and provide dependencies to it
 *
 * @author milan
 */
@UserScope
@Subcomponent(modules = {UserModule.class, FeedDataModule.class})
public interface UserComponent {
    FeedComponent with(FeedModule module);

    FeedListComponent with(FeedListModule module);

    void inject(FeedSyncService service);
}
