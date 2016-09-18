package com.milanix.shutter.feed.comment;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.feed.model.Post;
import com.milanix.shutter.notification.NotificationGenerator;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing comment list related dependencies
 *
 * @author milan
 */
@Module
public class CommentListModule {
    public static final String POST = "_post";
    private final Post post;
    private final CommentListContract.View view;

    public CommentListModule(Post post, CommentListContract.View view) {
        this.post = post;
        this.view = view;
    }

    @Provides
    @Named(POST)
    public Post providePost() {
        return post;
    }

    @Provides
    public CommentListContract.View provideView() {
        return view;
    }

    @Provides
    public CommentListContract.Presenter providePresenter(CommentListContract.View view,
                                                          FirebaseUser user,
                                                          FirebaseDatabase database,
                                                          NotificationGenerator notificationGenerator,
                                                          @Named(POST) Post post) {
        return new CommentListPresenter(view, user, database, notificationGenerator, post);
    }
}
