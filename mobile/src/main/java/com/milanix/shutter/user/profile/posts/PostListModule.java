package com.milanix.shutter.user.profile.posts;

import com.google.firebase.database.FirebaseDatabase;
import com.milanix.shutter.user.profile.ProfileModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing profile related dependencies
 *
 * @author milan
 */
@Module
public class PostListModule {
    private final PostListContract.View view;

    public PostListModule(PostListContract.View view) {
        this.view = view;
    }

    @Provides
    public PostListContract.View provideView() {
        return view;
    }

    @Provides
    public PostListContract.Presenter providePresenter(PostListContract.View view,
                                                       FirebaseDatabase database,
                                                       @Named(ProfileModule.PROFILE_ID) String profileId) {
        return new PostListPresenter(view, database, profileId);
    }
}
