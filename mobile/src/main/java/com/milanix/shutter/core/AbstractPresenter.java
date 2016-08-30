package com.milanix.shutter.core;

import com.milanix.shutter.core.specification.IPresenter;
import com.milanix.shutter.core.specification.IView;

/**
 * Abstract implementation of presenter that requires view injection to all classes extending it
 *
 * @author milan
 */
public abstract class AbstractPresenter<T extends IView> implements IPresenter {
    protected final T view;

    public AbstractPresenter(T view) {
        this.view = view;
    }

    public boolean hasView() {
        return null != view;
    }
}
