package com.milanix.shutter.user.profile.posts;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.milanix.shutter.core.AbstractPresenter;
import com.milanix.shutter.user.profile.ProfileModule;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Profile presenter
 *
 * @author milan
 */
public class PostListPresenter extends AbstractPresenter<PostListContract.View> implements PostListContract.Presenter {

    private final Query postsQuery;

    @Inject
    public PostListPresenter(PostListContract.View view, FirebaseDatabase database,
                             @Named(ProfileModule.PROFILE_ID) String profileId) {
        super(view);
        this.postsQuery = database.getReference().child("posts").orderByChild("author/id").equalTo(profileId);
    }

    @Override
    public void subscribe(ChildEventListener childEventListener) {
        postsQuery.addChildEventListener(childEventListener);
    }

    @Override
    public void unsubscribe(ChildEventListener childEventListener) {
        postsQuery.removeEventListener(childEventListener);
    }

    @Override
    public void refreshPosts() {
        view.hideProgress();
    }


}
