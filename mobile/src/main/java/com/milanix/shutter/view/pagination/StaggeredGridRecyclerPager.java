package com.milanix.shutter.view.pagination;

import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Implementation for {@link RecyclerPager} that supports {@link StaggeredGridLayoutManager}
 *
 * @author milan
 */
public class StaggeredGridRecyclerPager extends RecyclerPager<StaggeredGridLayoutManager> {

    public StaggeredGridRecyclerPager(StaggeredGridLayoutManager layoutManager,
                                      OnPaginateListener onPaginateListener) {
        super(layoutManager, onPaginateListener);
    }

    @Override
    protected int getFirstVisibleItemPosition() {
        final int[] firstVisibleItems = layoutManager.findFirstVisibleItemPositions(null);

        return (firstVisibleItems.length > 0) ? firstVisibleItems[0] : 0;
    }
}
