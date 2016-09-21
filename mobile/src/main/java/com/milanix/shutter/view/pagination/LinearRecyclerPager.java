package com.milanix.shutter.view.pagination;

import android.support.v7.widget.LinearLayoutManager;

/**
 * Implementation for {@link RecyclerPager} that supports {@link LinearLayoutManager}
 *
 * @author milan
 */
public class LinearRecyclerPager extends RecyclerPager<LinearLayoutManager> {

    public LinearRecyclerPager(LinearLayoutManager layoutManager, OnPaginateListener onPaginateListener) {
        super(layoutManager, onPaginateListener);
    }

    @Override
    protected int getFirstVisibleItemPosition() {
        return layoutManager.findFirstVisibleItemPosition();
    }
}
