package com.milanix.shutter.view.pagination;

/**
 * Interface to be implemented by classes listening to the paging event
 *
 * @author milan
 */
public interface OnPaginateListener {
    /**
     * Returns items per page. Returning different value might result to an error
     *
     * @return items per page
     */
    int getItemPerPage();

    /**
     * Invoked when next page should be retrieved
     *
     * @param page number starting from 1
     */
    void onNextPage(int page);
}
