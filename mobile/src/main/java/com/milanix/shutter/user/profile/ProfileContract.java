package com.milanix.shutter.user.profile;

import com.milanix.shutter.core.IPresenter;
import com.milanix.shutter.core.IView;
import com.milanix.shutter.user.model.User;

/**
 * Contract for profile related implementations
 *
 * @author milan
 */
public interface ProfileContract {
    interface View extends IView {
        void showProfile(User user);

        void handleProfileRefreshError();
    }

    interface Presenter extends IPresenter {
        void getProfile();

        void refreshProfile();
    }
}
