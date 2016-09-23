package com.milanix.shutter.core;

import android.animation.AnimatorListenerAdapter;
import android.view.View;

/**
 * Utility for performing some generic animations
 *
 * @author milan
 */
public class Animator {

    public static void animateHideUp(final View view) {
        view.setAlpha(1.0f);
        view.animate()
                .translationY(-view.getHeight())
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        view.setVisibility(View.GONE);
                    }
                });
    }

    public static void animateShowDown(final View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);
        view.setTranslationY(-view.getHeight());
        view.animate()
                .translationY(0.0f)
                .alpha(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        view.setTranslationY(0);
                        view.setAlpha(1.0f);
                    }
                });
    }

    public static void animateShowUp(final View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);
        view.setTranslationY(view.getHeight());
        view.animate()
                .translationY(0.0f)
                .alpha(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        view.setTranslationY(0);
                        view.setAlpha(1.0f);
                    }
                });
    }

    public static void animateHideDown(final View view) {
        view.setAlpha(1.0f);
        view.animate()
                .translationY(view.getHeight())
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        view.setVisibility(View.GONE);
                    }
                });
    }
}
