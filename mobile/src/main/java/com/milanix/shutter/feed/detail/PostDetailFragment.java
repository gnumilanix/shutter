package com.milanix.shutter.feed.detail;

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
import com.milanix.shutter.databinding.FragmentPostDetailBinding;
import com.milanix.shutter.feed.FeedModule;
import com.milanix.shutter.feed.model.Post;

/**
 * Fragment containing feeds
 *
 * @author milan
 */
public class PostDetailFragment extends AbstractFragment<PostDetailContract.Presenter, FragmentPostDetailBinding> implements PostDetailContract.View {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getApp().createFeedComponent(getArguments().getString(FeedModule.POST_ID)).with(new PostDetailModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_post_detail, container);
        setStatusColor(getActivity(), android.R.color.black);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
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
    public void showPost(Post post) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(post.getTitle());
        binding.setPost(post);
    }

    @Override
    public void handlePostRetrieveError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0),
                R.string.error_refresh_feed, Snackbar.LENGTH_SHORT).show();
    }
}
