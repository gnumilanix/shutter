package com.milanix.shutter.user.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentProfileBinding;
import com.milanix.shutter.user.model.User;

/**
 * Fragment containing profile
 *
 * @author milan
 */
public class ProfileFragment extends AbstractFragment<ProfileContract.Presenter, FragmentProfileBinding> implements ProfileContract.View {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getUserComponent().with(new ProfileModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_profile, container);
        presenter.getProfile();

        return binding.getRoot();
    }

    @Override
    public void showProfile(User user) {
        binding.setUser(user);
    }

    @Override
    public void handleProfileRefreshError() {
        Snackbar.make(((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0), R.string.error_refresh_profile, Snackbar.LENGTH_SHORT);
    }
}
