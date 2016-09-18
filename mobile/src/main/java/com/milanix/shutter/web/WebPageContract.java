package com.milanix.shutter.web;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Contract defining web page related view/presenter implementations
 *
 * @author milan
 */
public interface WebPageContract {
    interface View extends IView {
        void loadUrl(WebPage webPage);
    }

    interface Presenter extends IPresenter {

    }
}
