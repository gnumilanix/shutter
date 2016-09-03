package com.milanix.shutter.post;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.annotations.Nullable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.milanix.shutter.core.AbstractPresenter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * New post presenter
 *
 * @author milan
 */
public class NewPostPresenter extends AbstractPresenter<NewPostContract.View> implements NewPostContract.Presenter {
    private Context context;
    private final FirebaseUser user;
    private FirebaseDatabase database;
    private FirebaseStorage storage;

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
    public void publishPost(final NewPost post) {
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

    private void createPost(String uid, String postId, NewPost post, String downloadUrl) {
        final Map<String, Object> postValues = post.toMap();
        postValues.put("image", downloadUrl);
        postValues.put("thumbnail", downloadUrl);
        postValues.put("authorId", uid);
        postValues.put("postId", postId);

        final Map<String, Object> userPostValue = new HashMap<>();
        userPostValue.put(postId, true);

        final Map<String, Object> update = new HashMap<>();
        update.put("/posts/" + postId, postValues);
        update.put("/users/" + uid + "/posts", userPostValue);

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
