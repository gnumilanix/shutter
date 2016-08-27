package com.milanix.shutter.feed.favorite;

import com.milanix.shutter.feed.model.IFeedRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing feed related dependencies
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
    public FavoriteListContract.View provideFavoriteListView() {
        return view;
    }

    @Provides
    public FavoriteListContract.Presenter provideFavoriteListPresenter(FavoriteListContract.View view, IFeedRepository feedRepository) {
        return new FavoriteListPresenter(view, feedRepository);
    }
}
