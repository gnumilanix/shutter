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
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.model.Post;

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
    private final String postId;

    @Inject
    public PostDetailPresenter(PostDetailContract.View view, FirebaseUser user, FirebaseDatabase database,
                               FirebaseStorage storage, @Named(FeedModule.POST_ID) String postId) {
        super(view);
        this.user = user;
        this.database = database;
        this.storage = storage;
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
            removeFavorite(uid);
        } else {
            addFavorite(uid);
        }

    }

    private void addFavorite(String uid) {
        final Map<String, Object> favoriterValue = new HashMap<>();
        favoriterValue.put(uid, true);

        final Map<String, Object> favoriteValue = new HashMap<>();
        favoriteValue.put(postId, true);

        final Map<String, Object> update = new HashMap<>();
        update.put("/posts/" + postId + "/favoriters", favoriterValue);
        update.put("/users/" + uid + "/favorites", favoriteValue);

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

    private void removeFavorite(String uid) {
        final Map<String, Object> update = new HashMap<>();
        update.put("/posts/" + postId + "/favoriters/" + uid, null);
        update.put("/users/" + user.getUid() + "/favorites/" + postId, null);

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

    @Override
    public FirebaseUser getUser() {
        return user;
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
