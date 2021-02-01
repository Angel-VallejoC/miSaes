package me.angelvc.misaes.login.Contracts;

public interface LoginView {
    void showCaptcha(byte[] captchaImage);
    void showProgress();
    void hideProgress();
    void showError(String errorMessage);
    void loginSuccessful();
}
