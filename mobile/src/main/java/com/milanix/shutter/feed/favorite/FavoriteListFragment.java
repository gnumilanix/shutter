package com.milanix.shutter.feed.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentFavoriteListBinding;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.detail.PostDetailActivity;
import com.milanix.shutter.feed.model.Post;

import java.util.List;

import javax.inject.Inject;

/**
 * Fragment containing feeds
 *
 * @author milan
 */
public class FavoriteListFragment extends AbstractFragment<FavoriteListContract.Presenter, FragmentFavoriteListBinding> implements
        FavoriteListContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    protected FavoriteListAdapter favoriteListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserComponent().with(new FavoriteListModule(this)).inject(this);
        presenter.subscribe(favoriteListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        performBinding(inflater, R.layout.fragment_favorite_list, container);

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);
        
        binding.setAdapter(favoriteListAdapter);
        binding.setRefreshListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe(favoriteListAdapter);
    }

    @Override
    public void showFeeds(List<Post> posts) {
        binding.getAdapter().replaceItems(posts);
    }

    @Override
    public void openFeed(String feedId) {
        startActivity(new Intent(getActivity(), PostDetailActivity.class).putExtra(FeedModule.POST_ID, feedId));
    }

    @Override
    public void handleFeedRefreshError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0),
                R.string.error_refresh_favorites, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.refreshFavorites();
    }
}
