package com.milanix.shutter.muzei;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.App;
import com.milanix.shutter.feed.model.Post;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Implementation of {@link RemoteMuzeiArtSource} that uses trending shutter
 *
 * @author milan
 */
public class ShutterArtSource extends RemoteMuzeiArtSource {
    private static final String TAG = "ShutterArtSource";
    private static final int ROTATE_TIME_MILLIS = 3 * 60 * 60 * 1000;

    @Inject
    protected FirebaseDatabase database;

    public ShutterArtSource() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((App) getApplication()).getAppComponent().inject(this);
        setUserCommands(BUILTIN_COMMAND_ID_NEXT_ARTWORK);
    }

    @Override
    protected void onTryUpdate(int reason) throws RetryException {
        database.getReference().child("posts").orderByKey().limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                publishArtwork(getRandom(dataSnapshot.getChildren()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void publishArtwork(@Nullable Post post) {
        if (null != post) {
            publishArtwork(new Artwork.Builder()
                    .title(post.getTitle())
                    .byline(post.getDescription())
                    .imageUri(Uri.parse(post.getImage()))
                    .token(post.getPostId())
                    .build());

            scheduleUpdate(System.currentTimeMillis() + ROTATE_TIME_MILLIS);
        }
    }

    @Nullable
    private Post getRandom(Iterable<DataSnapshot> snapshots) {
        final ArrayList<Post> posts = new ArrayList<>();

        for (DataSnapshot postSnapshot : snapshots) {
            posts.add(postSnapshot.getValue(Post.class));
        }

        Collections.shuffle(posts);

        if (!posts.isEmpty()) {
            return posts.get(0);
        }

        return null;
    }
}
