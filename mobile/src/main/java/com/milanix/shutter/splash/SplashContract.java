package com.milanix.shutter.splash;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract defining splash related view/presenter implementations
 *
 * @author milan
 */
public interface SplashContract {
    interface View extends IView {
        void setSessionAvailable();

        void setSessionUnavailable();

    }

    interface Presenter extends IPresenter {
    }
}
