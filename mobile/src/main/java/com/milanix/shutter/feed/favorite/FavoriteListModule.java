package com.milanix.shutter.feed.favorite;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing favorites related dependencies
 *
 * @author milan
 */
@Module
public class FavoriteListModule {
    private final FavoriteListContract.View view;

    public FavoriteListModule(FavoriteListContract.View view) {
        this.view = view;
    }

    @Provides
    public FavoriteListContract.View provideView() {
        return view;
    }

    @Provides
    public FavoriteListContract.Presenter providePresenter(FavoriteListContract.View view,
                                                           FirebaseUser user,
                                                           FirebaseDatabase database) {
        return new FavoriteListPresenter(view, user, database);
    }
}
