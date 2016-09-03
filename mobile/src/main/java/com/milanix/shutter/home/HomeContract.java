package com.milanix.shutter.home;

import com.google.firebase.auth.FirebaseUser;
import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract for home related implementations
 *
 * @author milan
 */
public interface HomeContract {
    interface View extends IView {
        void setUser(FirebaseUser user);

        void openProfile(String profileId);

        void addPost();
    }

    interface Presenter extends IPresenter {
        void getUser();
    }
}
