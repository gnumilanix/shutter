package com.milanix.shutter.notification;

import com.milanix.shutter.notification.model.INotificationRepository;

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
    public NotificationListContract.View provideNotificationListView() {
        return view;
    }

    @Provides
    public NotificationListContract.Presenter provideNotificationListPresenter(NotificationListContract.View view,
                                                                               INotificationRepository repository) {
        return new NotificationListPresenter(view, repository);
    }
}
