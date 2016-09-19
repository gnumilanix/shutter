package com.milanix.shutter.feed.list;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.notification.NotificationGenerator;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing feed related dependencies
 *
 * @author milan
 */
@Module
public class FeedListModule {
    private final FeedListContract.View view;

    public FeedListModule(FeedListContract.View view) {
        this.view = view;
    }

    @Provides
    public FeedListContract.View provideView() {
        return view;
    }

    @Provides
    public FeedListContract.Presenter providePresenter(FeedListContract.View view,
                                                       FirebaseUser user,
                                                       FirebaseDatabase database,
                                                       NotificationGenerator notificationGenerator) {
        return new FeedListPresenter(view, user, database, notificationGenerator);
    }
}
