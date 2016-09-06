package com.milanix.shutter.user.profile.followers;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.R;
import com.milanix.shutter.core.BindingViewHolder;
import com.milanix.shutter.core.specification.AbstractFirebaseRecyclerAdapter;
import com.milanix.shutter.databinding.ItemFollowersBinding;
import com.milanix.shutter.user.model.Profile;

import javax.inject.Inject;

/**
 * Adapter containing list of posts
 *
 * @author milan
 */
public class FollowerListAdapter extends AbstractFirebaseRecyclerAdapter<Profile, FollowerListAdapter.ProfileHolder> {
    private final FollowerListContract.View followerListView;
    private final FollowerListContract.Presenter followerListPresenter;
    private final FirebaseUser user;
    private final LayoutInflater inflater;

    @Inject
    public FollowerListAdapter(FollowerListContract.View followerListView,
                               FollowerListContract.Presenter followerListPresenter,
                               FirebaseUser user, LayoutInflater inflater) {
        this.followerListView = followerListView;
        this.followerListPresenter = followerListPresenter;
        this.user = user;
        this.inflater = inflater;
    }

    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProfileHolder((ItemFollowersBinding) DataBindingUtil.inflate(inflater, R.layout.item_followers, parent, false));
    }

    @Override
    protected void bind(int position, ProfileHolder viewHolder, Profile item) {
        viewHolder.binding.setProfile(item);
        viewHolder.binding.setUser(user);
        viewHolder.binding.setView(followerListView);
        viewHolder.binding.setPresenter(followerListPresenter);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    protected Class<Profile> getTypeClass() {
        return Profile.class;
    }

    class ProfileHolder extends BindingViewHolder<ItemFollowersBinding> {
        ProfileHolder(ItemFollowersBinding binding) {
            super(binding);
        }
    }
}
