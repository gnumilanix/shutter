package com.milanix.shutter.core;

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
}
