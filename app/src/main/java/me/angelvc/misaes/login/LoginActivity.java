package me.angelvc.misaes.login;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import me.angelvc.misaes.databinding.ActivityLoginBinding;
import me.angelvc.misaes.home.HomeActivity;
import me.angelvc.misaes.login.Contracts.LoginPresenter;
import me.angelvc.misaes.login.Contracts.LoginView;

public class LoginActivity extends AppCompatActivity implements TextWatcher, LoginView {
    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding binding;
    private LoginPresenter presenter;

    // TODO: check internet connection before requesting login page
    // TODO: create session when logging in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new LoginPresenterImpl(this);
        presenter.onCreate();

        binding.user.addTextChangedListener(this);
        binding.password.addTextChangedListener(this);
        binding.captcha.addTextChangedListener(this);
        binding.loginButton.setOnClickListener((v) -> {
            presenter.login(binding.user.getText().toString(),
                            binding.password.getText().toString(),
                            binding.captcha.getText().toString());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    // ------------------------  TEXT WATCHER METHODS ---------------------------
    @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
    @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
    @Override
    public void afterTextChanged(Editable editable) {
        if (!binding.user.getText().toString().trim().equals("") &&
                !binding.password.getText().toString().trim().equals("") &&
                !binding.captcha.getText().toString().trim().equals(""))
            binding.loginButton.setEnabled(true);
        else
            binding.loginButton.setEnabled(false);
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
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(binding.rootView, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccessful() {
        startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
