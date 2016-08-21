package com.milanix.shutter.dependencies.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Scope that lasts through user lifecycle
 *
 * @author milan
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope {
}