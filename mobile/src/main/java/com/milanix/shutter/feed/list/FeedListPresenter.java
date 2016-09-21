package com.milanix.shutter.feed.list;

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

import static com.milanix.shutter.dependencies.module.FirebaseModule.ITEM_PER_PAGE;

/**
 * Feeds presenter
 *
 * @author milan
 */
public class FeedListPresenter extends AbstractPresenter<FeedListContract.View> implements
        FeedListContract.Presenter {
    private final FirebaseUser user;
    private final FirebaseDatabase database;
    private final NotificationGenerator notificationGenerator;
    private Query query;

    @Inject
    public FeedListPresenter(FeedListContract.View view, FirebaseUser user, FirebaseDatabase database,
                             NotificationGenerator notificationGenerator) {
        super(view);
        this.user = user;
        this.database = database;
        this.notificationGenerator = notificationGenerator;
        this.query = createQuery(1);
    }

    @Override
    public void subscribe(ChildEventListener childEventListener) {
        super.subscribe();
        this.query.addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscribe(ChildEventListener childEventListener) {
        super.unsubscribe();
        this.query.removeEventListener(childEventListener);
    }

    @Override
    public void refreshFeeds() {
        view.hideProgress();
    }

    @Override
    public void markFavorite(Post post) {
        final String uid = user.getUid();

        if (post.getFavoriters().containsKey(uid)) {
            removeFavorite(uid, post);
        } else {
            addFavorite(uid, post);
        }
    }

    private void addFavorite(String uid, Post post) {
        final Map<String, Object> update = new HashMap<>();
        update.put("/posts/" + post.getPostId() + "/favoriters/" + uid, true);
        update.put("/users/" + uid + "/favorites/" + post.getPostId(), true);

        if (!isMe(post.getAuthor().getId()))
            update.putAll(notificationGenerator.generate(Notification.Type.FAVORITE, post.getAuthor().getId(),
                    new Notification.Post(post)));

        database.getReference().updateChildren(update);
    }

    private void removeFavorite(final String uid, final Post post) {
        final Map<String, Object> update = new HashMap<>();
        update.put("/posts/" + post.getPostId() + "/favoriters/" + uid, null);
        update.put("/users/" + user.getUid() + "/favorites/" + post.getPostId(), null);
        update.putAll(notificationGenerator.generate(Notification.Type.UNFAVORITE, post.getAuthor().getId(),
                new Notification.Post(post)));

        database.getReference().updateChildren(update);
    }

    private boolean isMe(String uid) {
        return uid.equals(user.getUid());
    }

    @Override
    public int getItemPerPage() {
        return ITEM_PER_PAGE;
    }

    @Override
    public void onNextPage(int page) {
        //createQuery(page);
    }

    private Query createQuery(int page) {
        query = database.getReference().child("posts").orderByPriority().limitToFirst(page * getItemPerPage());
        return query;
    }
}
