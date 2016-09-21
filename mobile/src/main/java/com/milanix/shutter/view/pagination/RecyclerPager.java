package com.milanix.shutter.view.pagination;

import android.support.v7.widget.RecyclerView;

/**
 * Helper class for allowing pagination across {@link android.support.v7.widget.RecyclerView}
 *
 * @author milan
 */
public abstract class RecyclerPager<T extends RecyclerView.LayoutManager> extends RecyclerView.OnScrollListener {
    private boolean isLoading = true;
    private int previousTotal;
    private int currentPage = 1;
    private final OnPaginateListener onPaginateListener;
    protected final T layoutManager;

    public RecyclerPager(T layoutManager, OnPaginateListener onPaginateListener) {
        this.layoutManager = layoutManager;
        this.onPaginateListener = onPaginateListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy > 0)
            paginate(layoutManager.getChildCount(), getFirstVisibleItemPosition(), layoutManager.getItemCount());
    }

    /**
     * Paginates if required
     *
     * @param visibleItemCount in the layout
     * @param firstVisibleItem in the layout
     * @param totalItemCount   in the layout
     */
    private void paginate(int visibleItemCount, int firstVisibleItem, int totalItemCount) {
        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }

        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + onPaginateListener.getItemPerPage())) {
            isLoading = true;
            onPaginateListener.onNextPage(currentPage);
        }
    }

    /**
     * Resets this paging
     */
    public void reset() {
        isLoading = true;
        previousTotal = 0;
        currentPage = 0;
    }

    protected abstract int getFirstVisibleItemPosition();

}
