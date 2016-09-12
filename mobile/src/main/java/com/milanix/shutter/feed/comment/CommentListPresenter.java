package com.milanix.shutter.feed.comment;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.feed.model.Post;
import com.milanix.shutter.notification.NotificationGenerator;
import com.milanix.shutter.notification.model.Notification;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Notification list presenter
 *
 * @author milan
 */
public class CommentListPresenter extends AbstractPresenter<CommentListContract.View> implements CommentListContract.Presenter {
    private final FirebaseUser user;
    private final FirebaseDatabase database;
    private final NotificationGenerator notificationGenerator;
    private final Post post;
    private final Query query;

    @Inject
    public CommentListPresenter(CommentListContract.View view, FirebaseUser user, FirebaseDatabase database,
                                NotificationGenerator notificationGenerator, Post post) {
        super(view);
        this.user = user;
        this.database = database;
        this.notificationGenerator = notificationGenerator;
        this.post = post;
        this.query = database.getReference().child("posts").child(post.getPostId()).child("comments").orderByChild("time");
    }

    @Override
    public void subscribe(ChildEventListener notificationEventListener) {
        query.addChildEventListener(notificationEventListener);
        view.showPost(post);
    }

    @Override
    public void unsubscribe(ChildEventListener notificationEventListener) {
        query.removeEventListener(notificationEventListener);
    }

    @Override
    public void sendComment(Post post, CommentModel comment) {
        final Map<String, Object> update = new HashMap<>();
        update.put("/posts/" + post.getPostId() + "/commenters/" + user.getUid(), true);
        update.put("/users/" + user.getUid() + "/comments/" + post.getPostId(), true);

        if (!post.getAuthor().getId().equals(user.getUid()))
            update.putAll(notificationGenerator.generate(Notification.Type.COMMENT, post.getAuthor().getId(),
                    new Notification.Post(post)));

        database.getReference().updateChildren(update).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (isActive()) {
                    view.handleSendCommentError();
                }
            }
        });
    }

}
