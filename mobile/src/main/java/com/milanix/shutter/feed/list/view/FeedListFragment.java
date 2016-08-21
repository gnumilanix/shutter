package com.milanix.shutter.feed.list.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.databinding.FragmentFeedListBinding;
import com.milanix.shutter.feed.list.FeedListContract;
import com.milanix.shutter.feed.list.FeedListModule;
import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.specs.AbstractFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * Fragment containing feeds
 *
 * @author milan
 */
public class FeedListFragment extends AbstractFragment<FeedListContract.Presenter, FragmentFeedListBinding> implements FeedListContract.View {

    @Inject
    FeedListAdapter feedListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getUserComponent().with(new FeedListModule(this)).inject(this);
        performBinding(R.layout.fragment_feed_list);

        return binding.getRoot();
    }

    @Override
    protected void performBinding(@LayoutRes int layout) {
        super.performBinding(layout);
        binding.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.setAdapter(feedListAdapter);
    }

    @Override
    public void showFeeds(List<Feed> feeds) {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0), "New incoming feeds", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void handleFeedRefreshError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0), "Error refreshing feeds", Snackbar.LENGTH_SHORT);
    }
}
