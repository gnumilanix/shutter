package com.milanix.shutter.core.specification;

/**
 * Interface to be implemented by all stores
 *
 * @author milan
 */
public interface IStore {
    /**
     * Callback to notify callers about results
     */
    interface Callback<T> {
        void onSuccess(T result);

        void onFailure(Throwable t);
    }
}
