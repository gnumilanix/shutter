package com.milanix.shutter.notification.list;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds notification list related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = NotificationListModule.class)
public interface NotificationListComponent {

    void inject(NotificationListFragment fragment);
}
