package com.milanix.shutter.user.profile.followings;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.milanix.shutter.R;
import com.milanix.shutter.core.BindingViewHolder;
import com.milanix.shutter.core.specification.AbstractFirebaseRecyclerAdapter;
import com.milanix.shutter.databinding.ItemFollowingBinding;
import com.milanix.shutter.user.model.Profile;

import javax.inject.Inject;

/**
 * Adapter containing following list
 *
 * @author milan
 */
public class FollowingListAdapter extends AbstractFirebaseRecyclerAdapter<Profile, FollowingListAdapter.ProfileHolder> implements ValueEventListener {
    private final FollowingListContract.View view;
    private final FollowingListContract.Presenter presenter;
    private final FirebaseUser user;
    private final LayoutInflater inflater;
    private Profile profile;

    @Inject
    public FollowingListAdapter(FollowingListContract.View view, FollowingListContract.Presenter presenter,
                                FirebaseUser user, LayoutInflater inflater) {
        this.view = view;
        this.presenter = presenter;
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
        viewHolder.binding.setMe(profile);
        viewHolder.binding.setUser(user);
        viewHolder.binding.setView(view);
        viewHolder.binding.setPresenter(presenter);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    protected Class<Profile> getTypeClass() {
        return Profile.class;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        profile = dataSnapshot.getValue(Profile.class);
    }

    class ProfileHolder extends BindingViewHolder<ItemFollowingBinding> {
        ProfileHolder(ItemFollowingBinding binding) {
            super(binding);
        }
    }
}
