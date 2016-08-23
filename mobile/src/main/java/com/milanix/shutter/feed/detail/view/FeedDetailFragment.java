package com.milanix.shutter.feed.detail.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentFeedDetailBinding;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.detail.FeedDetailContract;
import com.milanix.shutter.feed.detail.FeedDetailModule;
import com.milanix.shutter.feed.model.Feed;

/**
 * Fragment containing feeds
 *
 * @author milan
 */
public class FeedDetailFragment extends AbstractFragment<FeedDetailContract.Presenter, FragmentFeedDetailBinding> implements FeedDetailContract.View {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getApp().createFeedComponent(getArguments().getLong(FeedModule.FEED_ID)).with(new FeedDetailModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_feed_detail, container);
        setStatusColor(getActivity(), android.R.color.black);
        presenter.getFeed();

        return binding.getRoot();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusColor(Activity activity, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(activity, color));
        }
    }

    @Override
    public void showFeed(Feed feed) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(feed.getTitle());
        binding.setFeed(feed);
    }

    @Override
    public void handleFeedRefreshError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0), "Error refreshing feed", Snackbar.LENGTH_SHORT);
    }
}
