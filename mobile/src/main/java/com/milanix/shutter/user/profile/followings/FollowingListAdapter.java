package com.milanix.shutter.user.profile.followings;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.R;
import com.milanix.shutter.core.BindingViewHolder;
import com.milanix.shutter.core.specification.AbstractFirebaseRecyclerAdapter;
import com.milanix.shutter.databinding.ItemFollowingBinding;
import com.milanix.shutter.user.model.Profile;

import javax.inject.Inject;

/**
 * Adapter containing list of posts
 *
 * @author milan
 */
public class FollowingListAdapter extends AbstractFirebaseRecyclerAdapter<Profile, FollowingListAdapter.ProfileHolder> {
    private final FollowingListContract.View followingListView;
    private final FollowingListContract.Presenter followingListPresenter;
    private final FirebaseUser user;
    private final LayoutInflater inflater;

    @Inject
    public FollowingListAdapter(FollowingListContract.View followingListView,
                                FollowingListContract.Presenter followingListPresenter,
                                FirebaseUser user, LayoutInflater inflater) {
        this.followingListView = followingListView;
        this.followingListPresenter = followingListPresenter;
        this.user = user;
        this.inflater = inflater;
    }

    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProfileHolder((ItemFollowingBinding) DataBindingUtil.inflate(inflater, R.layout.item_following, parent, false));
    }

    @Override
    protected void bind(int position, ProfileHolder viewHolder, Profile item) {
        viewHolder.binding.setProfile(item);
        viewHolder.binding.setView(followingListView);
        viewHolder.binding.setPresenter(followingListPresenter);
        viewHolder.binding.setUser(user);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    protected Class<Profile> getTypeClass() {
        return Profile.class;
    }

    class ProfileHolder extends BindingViewHolder<ItemFollowingBinding> {
        ProfileHolder(ItemFollowingBinding binding) {
            super(binding);
        }
    }
}
