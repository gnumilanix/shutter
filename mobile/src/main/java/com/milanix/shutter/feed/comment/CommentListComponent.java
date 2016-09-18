package com.milanix.shutter.feed.comment;

import com.milanix.shutter.dependencies.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Component that binds comment list related component and provide dependencies to it
 *
 * @author milan
 */
@ActivityScope
@Subcomponent(modules = CommentListModule.class)
public interface CommentListComponent {
    void inject(CommentListActivity activity);
}
