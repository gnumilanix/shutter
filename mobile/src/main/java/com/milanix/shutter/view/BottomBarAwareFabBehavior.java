package com.milanix.shutter.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

import com.roughike.bottombar.BottomBar;

/**
 * {@link FloatingActionButton} behavior that is aware of {@link BottomBar}
 *
 * @author milan
 */
public class BottomBarAwareFabBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    public BottomBarAwareFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout || dependency instanceof BottomBar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            child.setTranslationY(Math.min(0, dependency.getTranslationY() - dependency.getHeight()));
        } else if (dependency instanceof BottomBar) {
            child.setTranslationY(Math.min(0, dependency.getTranslationY() - dependency.getHeight()));
        }

        return true;
    }
}
