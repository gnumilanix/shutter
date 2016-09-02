package com.milanix.shutter.notification;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing notification related dependencies
 *
 * @author milan
 */
@Module
public class NotificationListModule {
    private final NotificationListContract.View view;

    public NotificationListModule(NotificationListContract.View view) {
        this.view = view;
    }

    @Provides
    public NotificationListContract.View provideView() {
        return view;
    }

    @Provides
    public NotificationListContract.Presenter providePresenter(NotificationListContract.View view,
                                                               FirebaseUser user,
                                                               FirebaseDatabase database) {
        return new NotificationListPresenter(view, user,database);
    }
}
