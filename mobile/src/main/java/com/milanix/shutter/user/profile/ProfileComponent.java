package com.milanix.shutter.user.profile;

import com.milanix.shutter.dependencies.scope.ActivityScope;
import com.milanix.shutter.user.profile.followers.FollowerListComponent;
import com.milanix.shutter.user.profile.followers.FollowerListModule;
import com.milanix.shutter.user.profile.followings.FollowingListComponent;
import com.milanix.shutter.user.profile.followings.FollowingListModule;
import com.milanix.shutter.user.profile.posts.PostListComponent;
import com.milanix.shutter.user.profile.posts.PostListModule;

import dagger.Subcomponent;

/**
 * Component that binds profile related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = ProfileModule.class)
public interface ProfileComponent {

    void inject(ProfileActivity activity);

    PostListComponent with(PostListModule module);

    FollowingListComponent with(FollowingListModule module);

    FollowerListComponent with(FollowerListModule module);
}
