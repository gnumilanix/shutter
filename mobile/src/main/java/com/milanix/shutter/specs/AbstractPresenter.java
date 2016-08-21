package com.milanix.shutter.specs;

/**
 * Abstract implementation of presenter that requires view injection to all classes extending it
 *
 * @author milan
 */
public abstract class AbstractPresenter<T extends IView> implements IPresenter {
    protected T view;

    public AbstractPresenter(T view) {
        this.view = view;
    }
}
