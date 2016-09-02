package com.milanix.shutter.feed.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentFeedListBinding;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.detail.PostDetailActivity;

import javax.inject.Inject;

/**
 * Fragment containing feeds
 *
 * @author milan
 */
public class FeedListFragment extends AbstractFragment<FeedListContract.Presenter, FragmentFeedListBinding> implements
        FeedListContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    protected FeedListAdapter feedListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserComponent().with(new FeedListModule(this)).inject(this);
        presenter.subscribe(feedListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        performBinding(inflater, R.layout.fragment_feed_list, container);

        return binding.getRoot();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);
        binding.setAdapter(feedListAdapter);
        binding.setRefreshListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe(feedListAdapter);
    }

    @Override
    public void openFeed(String feedId) {
        startActivity(new Intent(getActivity(), PostDetailActivity.class).putExtra(FeedModule.POST_ID, feedId));
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
