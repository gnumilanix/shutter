package com.milanix.shutter.feed.detail.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.databinding.FragmentFeedDetailBinding;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.detail.FeedDetailContract;
import com.milanix.shutter.feed.detail.FeedDetailModule;
import com.milanix.shutter.feed.model.Feed;
import com.milanix.shutter.specs.AbstractFragment;

/**
 * Fragment containing feeds
 *
 * @author milan
 */
public class FeedDetailFragment extends AbstractFragment<FeedDetailContract.Presenter, FragmentFeedDetailBinding> implements FeedDetailContract.View {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        performBinding(R.layout.fragment_feed_detail);

        getApp().createFeedComponent(getArguments().getLong(FeedModule.FEED_ID)).with(new FeedDetailModule(this)).inject(this);

        return binding.getRoot();
    }

    @Override
    public void showFeed(Feed feeds) {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0), "New incoming feed", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void handleFeedRefreshError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0), "Error refreshing feed", Snackbar.LENGTH_SHORT);
    }
}
