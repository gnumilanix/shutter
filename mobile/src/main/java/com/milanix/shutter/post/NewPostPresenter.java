package com.milanix.shutter.post;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.annotations.Nullable;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.feed.model.Author;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * New post presenter
 *
 * @author milan
 */
public class NewPostPresenter extends AbstractPresenter<NewPostContract.View> implements NewPostContract.Presenter {
    private final Context context;
    private final FirebaseUser user;
    private final FirebaseDatabase database;
    private final FirebaseStorage storage;

    @Inject
    public NewPostPresenter(NewPostContract.View view, Context context, FirebaseUser user, FirebaseDatabase database,
                            FirebaseStorage storage) {
        super(view);
        this.context = context;
        this.user = user;
        this.database = database;
        this.storage = storage;
    }

    @Override
    public void publishPost(final PostModel post) {
        view.showProgress();
        final InputStream avatarStream = getInputStream(post.getImageUri());

        if (null != avatarStream) {
            final String postId = database.getReference().child("posts").push().getKey();
            final String uid = user.getUid();
            storage.getReference().child("users/" + uid + "/posts/" + postId + ".jpeg").putStream(avatarStream).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            createPost(uid, postId, post, taskSnapshot.getDownloadUrl().toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    handleUploadPostError();
                }
            });
        } else {
            handleUploadPostError();
        }
    }

    private void createPost(String uid, final String postId, PostModel post, String downloadUrl) {
        final Map<String, Object> author = new Author(user.getUid(), user.getDisplayName(),
                user.getPhotoUrl().toString()).toMap();

        final Map<String, Object> postValues = post.toMap();
        postValues.put("image", downloadUrl);
        postValues.put("thumbnail", downloadUrl);
        postValues.put("author", author);
        postValues.put("postId", postId);
        postValues.put("time", ServerValue.TIMESTAMP);

        final Map<String, Object> update = new HashMap<>();
        update.put("/posts/" + postId, postValues);
        update.put("/users/" + uid + "/posts/" + postId, true);

        database.getReference().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (isActive()) {
                    view.completePublishPost();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                handleUploadPostError();
            }
        }).continueWith(new Continuation<Void, Void>() {
            @Override
            public Void then(@NonNull Task<Void> task) throws Exception {
                database.getReference("/posts/" + postId).setPriority(0 - new Date().getTime());
                return null;
            }
        });
    }

    private void handleUploadPostError() {
        if (isActive()) {
            view.hideProgress();
            view.handleUploadPostError();
        }
    }

    @Nullable
    public InputStream getInputStream(Uri uri) {
        try {
            return context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
