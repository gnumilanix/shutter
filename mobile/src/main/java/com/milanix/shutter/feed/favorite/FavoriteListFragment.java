package com.milanix.shutter.feed.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentFavoriteListBinding;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.detail.FeedDetailActivity;
import com.milanix.shutter.feed.model.Feed;

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
    FavoriteListAdapter favoriteListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getUserComponent().with(new FavoriteListModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_favorite_list, container);

        presenter.getFeeds();

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);
        binding.setAdapter(favoriteListAdapter);
        binding.setRefreshListener(this);
    }

    @Override
    public void showFeeds(List<Feed> feeds) {
        binding.getAdapter().replaceItems(feeds);
    }

    @Override
    public void openFeed(long feedId) {
        startActivity(new Intent(getActivity(), FeedDetailActivity.class).putExtra(FeedModule.FEED_ID, feedId));
    }

    @Override
    public void handleFeedRefreshError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0), R.string.error_refresh_favorites, Snackbar.LENGTH_SHORT);
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
        presenter.refreshFeeds();
    }
}
