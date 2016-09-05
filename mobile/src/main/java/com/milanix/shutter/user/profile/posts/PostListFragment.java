package com.milanix.shutter.user.profile.posts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.core.specification.IComponentProvider;
import com.milanix.shutter.databinding.FragmentPostListBinding;
import com.milanix.shutter.feed.PostModule;
import com.milanix.shutter.feed.detail.PostDetailActivity;
import com.milanix.shutter.user.profile.ProfileComponent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Fragment containing profile
 *
 * @author milan
 */
public class PostListFragment extends AbstractFragment<PostListContract.Presenter, FragmentPostListBinding> implements
        PostListContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    protected PostListAdapter postListAdapter;
    private IComponentProvider<ProfileComponent> componentProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        componentProvider.getComponent().with(new PostListModule(this)).inject(this);
        presenter.subscribe(postListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        performBinding(inflater, R.layout.fragment_post_list, container);

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        final Activity activity = getActivity();

        if (activity instanceof IComponentProvider) {
            this.componentProvider = (IComponentProvider) activity;
        } else {
            Timber.d("Caller did not implement OnReadyListener<ProfileComponent>");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe(postListAdapter);
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

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);
        binding.setAdapter(postListAdapter);
        binding.setRefreshListener(this);
    }

    @Override
    public void openPost(android.view.View view, String postId) {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view,
                getString(R.string.transition_post_image));

        startActivity(new Intent(getActivity(), PostDetailActivity.class).putExtra(PostModule.POST_ID, postId),
                options.toBundle());
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
        presenter.refreshPosts();
    }

}
