package com.milanix.shutter.feed.detail;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.milanix.shutter.feed.PostModule;
import com.milanix.shutter.notification.NotificationGenerator;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing feed related dependencies
 *
 * @author milan
 */
@Module
public class PostDetailModule {
    private final PostDetailContract.View view;

    public PostDetailModule(PostDetailContract.View view) {
        this.view = view;
    }

    @Provides
    public PostDetailContract.View provideView() {
        return view;
    }

    @Provides
    public PostDetailContract.Presenter providePresenter(PostDetailContract.View view,
                                                         FirebaseUser user,
                                                         FirebaseDatabase database,
                                                         FirebaseStorage storage,
                                                         NotificationGenerator notificationGenerator,
                                                         @Named(PostModule.POST_ID) String postId) {
        return new PostDetailPresenter(view, user, database, storage, notificationGenerator, postId);
    }
}
