package com.milanix.shutter.view.pagination;

import android.support.v7.widget.LinearLayoutManager;

/**
 * Implementation for {@link RecyclerPager} that supports {@link android.support.v7.widget.GridLayoutManager}
 *
 * @author milan
 */
public class GridRecyclerPager extends LinearRecyclerPager {

    public GridRecyclerPager(LinearLayoutManager layoutManager, OnPaginateListener onPaginateListener) {
        super(layoutManager, onPaginateListener);
    }
}
