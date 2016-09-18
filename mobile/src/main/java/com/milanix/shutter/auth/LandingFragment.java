package com.milanix.shutter.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.milanix.shutter.R;
import com.milanix.shutter.auth.login.LoginActivity;
import com.milanix.shutter.auth.signup.SignUpActivity;
import com.milanix.shutter.core.AbstractFragment;
import com.milanix.shutter.databinding.FragmentLandingBinding;
import com.milanix.shutter.home.HomeActivity;
import com.milanix.shutter.web.WebPage;
import com.milanix.shutter.web.WebPageActivity;
import com.milanix.shutter.web.WebPageBuilder;
import com.milanix.shutter.web.WebPageModule;

import org.parceler.Parcels;

import java.util.List;

/**
 * Fragment containing not logged in users view
 *
 * @author milan
 */
public class LandingFragment extends AbstractFragment<LandingContract.Presenter, FragmentLandingBinding> implements LandingContract.View {
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getAppComponent().with(new LandingModule(this)).inject(this);
        performBinding(inflater, R.layout.fragment_landing, container);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void performBinding(LayoutInflater inflater, @LayoutRes int layout, ViewGroup container) {
        super.performBinding(inflater, layout, container);

        binding.setView(this);
        binding.setPresenter(presenter);

        setBoldUnderlineSpan(binding.tvLogin, getString(R.string.action_signin_existing),
                getString(R.string.action_signin_existing_highlight));
        setBoldUnderlineSpan(binding.tvAgreement, getString(R.string.action_signin_accept_terms),
                getString(R.string.action_signin_accept_terms_highlight));
    }

    private void setBoldUnderlineSpan(TextView view, String fullText, String textToStyle) {
        final int beginIndex = fullText.indexOf(textToStyle);
        final int endIndex = beginIndex + textToStyle.length();

        final SpannableString spannableString = new SpannableString(fullText);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), beginIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), beginIndex, endIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        view.setText(spannableString);
    }

    @Override
    public void login() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void signUp() {
        startActivity(new Intent(getActivity(), SignUpActivity.class));
    }

    @Override
    public void completeLogin() {
        getActivity().finish();
        startActivity(new Intent(getActivity(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }

    @Override
    public void handleLoginError() {
        new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialog)
                .setTitle(R.string.title_login_error)
                .setMessage(R.string.message_login_error)
                .setPositiveButton(getText(R.string.action_ok), null)
                .show();
    }

    @Override
    public void openTerms() {
        final WebPage webPage = new WebPageBuilder().setTitle(getString(R.string.title_terms)).
                setUrl(presenter.getTermsUrl()).build();

        startActivity(new Intent(getActivity(), WebPageActivity.class).putExtra(WebPageModule.WEB_PAGE,
                Parcels.wrap(webPage)));
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(getActivity(), getString(R.string.title_logging_in), getString(R.string.message_logging_in), true);
    }

    @Override
    public void hideProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();
    }

    @Override
    public void loginWithGoogle(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void handleGoogleApiFailure() {
        binding.btnGoogle.setVisibility(View.GONE);
    }

    @Override
    public void loginWithFacebook(List<String> permissions) {
        LoginManager.getInstance().logInWithReadPermissions(this, permissions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        presenter.handleActivityResult(requestCode, resultCode, data);
    }
}
