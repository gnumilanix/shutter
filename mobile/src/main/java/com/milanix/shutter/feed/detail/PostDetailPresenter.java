package com.milanix.shutter.feed.detail;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.model.Post;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Feeds presenter
 *
 * @author milan
 */
public class PostDetailPresenter extends AbstractPresenter<PostDetailContract.View> implements PostDetailContract.Presenter, ValueEventListener {
    private final FirebaseDatabase database;
    private final String feedId;

    @Inject
    public PostDetailPresenter(PostDetailContract.View view, FirebaseDatabase database, @Named(FeedModule.POST_ID) String feedId) {
        super(view);
        this.database = database;
        this.feedId = feedId;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        database.getReference().child("posts/" + feedId).addValueEventListener(this);
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        database.getReference().child("posts/" + feedId).removeEventListener(this);
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
}
