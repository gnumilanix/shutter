package com.milanix.shutter.home;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;
import com.milanix.shutter.user.model.User;

/**
 * Contract for home related implementations
 *
 * @author milan
 */
public interface HomeContract {
    interface View extends IView {
        void setUser(User user);

        void openProfile(User user);

        void addPost();
    }

    interface Presenter extends IPresenter {
        void getUser();
    }
}
