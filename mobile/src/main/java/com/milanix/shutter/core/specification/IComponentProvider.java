package com.milanix.shutter.core.specification;

/**
 * Interface to be implemented by class providing dagger component. Usually an activity which hosts
 * a fragment that requires accessing component of type {#T} from the parent activity
 *
 * @param <T> type of the component
 * @author milan
 */
public interface IComponentProvider<T> {
    T getComponent();
}
