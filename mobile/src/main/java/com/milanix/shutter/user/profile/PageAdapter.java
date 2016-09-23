package com.milanix.shutter.user.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {
    private final List<Page> pages;
    private final Context context;

    public PageAdapter(Context context, FragmentManager fragmentManager, List<Page> pages) {
        super(fragmentManager);
        this.context = context;
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(pages.get(position));
    }

    private Fragment getFragment(Page page) {
        return Fragment.instantiate(context, page.fragmentClass.getName(), page.args);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    /**
     * Page definition for this adapter
     *
     * @author milan
     */
    public static final class Page {
        private final Class<? extends Fragment> fragmentClass;
        private final Bundle args;

        public Page(Class<? extends Fragment> fragmentClass, Bundle args) {
            this.fragmentClass = fragmentClass;
            this.args = args;
        }
    }
}
