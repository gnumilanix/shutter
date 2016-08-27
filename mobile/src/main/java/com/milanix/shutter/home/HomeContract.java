package com.milanix.shutter.home;

import com.milanix.shutter.core.IPresenter;
import com.milanix.shutter.core.IView;
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
    }

    interface Presenter extends IPresenter {
        void getUser();
    }
}
