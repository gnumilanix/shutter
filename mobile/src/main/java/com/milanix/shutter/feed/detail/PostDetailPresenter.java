package com.milanix.shutter.feed.detail;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.feed.PostModule;
import com.milanix.shutter.feed.model.Post;
import com.milanix.shutter.notification.NotificationGenerator;
import com.milanix.shutter.notification.model.Notification;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import timber.log.Timber;

/**
 * Feeds presenter
 *
 * @author milan
 */
public class PostDetailPresenter extends AbstractPresenter<PostDetailContract.View> implements PostDetailContract.Presenter, ValueEventListener {
    private FirebaseUser user;
    private final FirebaseDatabase database;
    private final FirebaseStorage storage;
    private NotificationGenerator notificationGenerator;
    private final String postId;

    @Inject
    public PostDetailPresenter(PostDetailContract.View view, FirebaseUser user, FirebaseDatabase database,
                               FirebaseStorage storage, NotificationGenerator notificationGenerator,
                               @Named(PostModule.POST_ID) String postId) {
        super(view);
        this.user = user;
        this.database = database;
        this.storage = storage;
        this.notificationGenerator = notificationGenerator;
        this.postId = postId;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        database.getReference().child("posts/" + postId).addValueEventListener(this);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        database.getReference().child("posts/" + postId).removeEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        final Post post = dataSnapshot.getValue(Post.class);

        if (isActive()) {
            view.showPost(post);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        if (isActive()) {
            view.handlePostRetrieveError();
        }
    }

    @Override
    public void share(final Post post) {
        final Uri localUri = getShareUri(post);

        if (null != localUri) {
            view.showProgress();
            storage.getReferenceFromUrl(post.getImage()).getFile(localUri).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if (isActive()) {
                        view.hideProgress();
                        view.share(post.getTitle(), post.getDescription(), localUri);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (isActive()) {
                        view.hideProgress();
                        view.handlePostShareError();
                    }
                }
            });
        }
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
        update.put("/posts/" + postId + "/favoriters/" + uid, true);
        update.put("/users/" + uid + "/favorites/" + postId, true);
        update.putAll(notificationGenerator.generate(Notification.Type.FAVORITE, post.getAuthor().getId(), new Notification.Post(post.getPostId(), post.getImage())));

        database.getReference().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (isActive()) {
                    view.completeMarkFavorite();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (isActive()) {
                    view.handleMarkFavoriteError();
                }
            }
        });
    }

    private void removeFavorite(String uid, Post post) {
        final Map<String, Object> update = new HashMap<>();
        update.put("/posts/" + postId + "/favoriters/" + uid, null);
        update.put("/users/" + user.getUid() + "/favorites/" + postId, null);
        update.putAll(notificationGenerator.generate(Notification.Type.UNFAVORITE));

        database.getReference().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (isActive()) {
                    view.completeMarkFavorite();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (isActive()) {
                    view.handleMarkFavoriteError();
                }
            }
        });
    }

    @Nullable
    private Uri getShareUri(final Post post) {
        try {
            return Uri.fromFile(File.createTempFile(post.getPostId(), "jpeg"));
        } catch (IOException e) {
            Timber.w(e, "Error while creating temporary file");
            return null;
        }
    }
}
