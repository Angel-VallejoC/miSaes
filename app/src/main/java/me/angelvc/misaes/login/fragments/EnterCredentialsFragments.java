package me.angelvc.misaes.login.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.LoginFragmentEnterCredentialsFragmentsBinding;
import me.angelvc.misaes.home.HomeActivity;
import me.angelvc.misaes.login.Contracts.LoginPresenter;
import me.angelvc.misaes.login.Contracts.LoginView;
import me.angelvc.misaes.login.LoginActivity;
import me.angelvc.misaes.login.LoginPresenterImpl;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.School;

public class EnterCredentialsFragments extends Fragment implements TextWatcher, LoginView {

    LoginFragmentEnterCredentialsFragmentsBinding binding;
    private LoginPresenter presenter;

    public EnterCredentialsFragments() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment_enter_credentials_fragments, container, false);
        binding = LoginFragmentEnterCredentialsFragmentsBinding.bind(view);
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void init() {
        String  schoolName = getActivity()
                .getSharedPreferences(getString(R.string.app_preferences_key), Context.MODE_PRIVATE)
                .getString(getString(R.string.login_school_preference), null);
        presenter = new LoginPresenterImpl(this, SAEScraper.getInstance(School.getSchoolByName(schoolName)));
        presenter.onCreate();

        binding.user.addTextChangedListener(this);
        binding.password.addTextChangedListener(this);
        binding.captcha.addTextChangedListener(this);
        binding.loginButton.setOnClickListener((v) -> {
            presenter.login(binding.user.getText().toString(),
                    binding.password.getText().toString(),
                    binding.captcha.getText().toString());
        });

        binding.selectSchool.setOnClickListener((v) ->
                Navigation.findNavController(v).navigate(R.id.action_enterCredentialsFragments_to_selectSchoolFragment));
    }

    // ------------------------  TEXT WATCHER METHODS ---------------------------
    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
    @Override
    public void afterTextChanged(Editable editable) {
        binding.loginButton.setEnabled(
                !binding.user.getText().toString().trim().equals("") &&
                !binding.password.getText().toString().trim().equals("") &&
                !binding.captcha.getText().toString().trim().equals("")
        );
    }



    // ------------------------  VIEW METHODS ---------------------------
    @Override
    public void showCaptcha(byte[] captchaImage) {
        binding.captchaImage.setImageBitmap(
                BitmapFactory.decodeByteArray(
                        captchaImage, 0, captchaImage.length
                )
        );
    }

    @Override
    public void showProgress() {
        ((LoginActivity) getActivity()).binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        ((LoginActivity) getActivity()).binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(((LoginActivity) getActivity()).binding.rootView, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccessful() {
        startActivity(new Intent(getActivity(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}