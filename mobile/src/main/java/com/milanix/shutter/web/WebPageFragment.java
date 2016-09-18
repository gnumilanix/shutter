package com.milanix.shutter.web;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.milanix.shutter.R;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentWebpageBinding;

import org.parceler.Parcels;

/**
 * Fragment containing a web page
 *
 * @author milan
 */
public class WebPageFragment extends AbstractFragment<WebPageContract.Presenter, FragmentWebpageBinding> implements
        WebPageContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getAppComponent().with(new WebPageModule(this, getWebpage(getArguments()))).inject(this);
        performBinding(inflater, R.layout.fragment_webpage, container);
        presenter.subscribe();

        return binding.getRoot();
    }

    private WebPage getWebpage(Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(WebPageModule.WEB_PAGE));
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);

        binding.setRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        binding.wv.reload();
    }

    @Override
    public void loadUrl(WebPage webPage) {
        binding.setWebpage(webPage);
    }
}
