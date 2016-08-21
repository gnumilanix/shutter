package com.milanix.shutter.dependencies.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Qualifier defining remote data dependencies
 *
 * @author milan
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Remote {
}