package com.milanix.shutter.user.profile.followings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.core.specification.IComponentProvider;
import com.milanix.shutter.databinding.FragmentFollowingListBinding;
import com.milanix.shutter.user.profile.ProfileActivity;
import com.milanix.shutter.user.profile.ProfileComponent;
import com.milanix.shutter.user.profile.ProfileModule;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Fragment containing profile
 *
 * @author milan
 */
public class FollowingListFragment extends AbstractFragment<FollowingListContract.Presenter, FragmentFollowingListBinding> implements
        FollowingListContract.View {

    @Inject
    protected FollowingListAdapter followingListAdapter;
    private IComponentProvider<ProfileComponent> componentProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        componentProvider.getComponent().with(new FollowingListModule(this)).inject(this);
        presenter.subscribe(followingListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        performBinding(inflater, R.layout.fragment_following_list, container);

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
        presenter.unsubscribe(followingListAdapter);
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
        binding.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.setAdapter(followingListAdapter);
    }


    @Override
    public void openProfile(View view, String authorId) {
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view,
                getString(R.string.transition_profile_image));

        startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra(ProfileModule.PROFILE_ID, authorId),
                options.toBundle());
    }

    @Override
    public void handleUnfollowError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0), R.string.error_unfollow_user,
                Snackbar.LENGTH_SHORT).show();
    }
}