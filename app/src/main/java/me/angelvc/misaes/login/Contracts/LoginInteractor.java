package me.angelvc.misaes.login.Contracts;

public interface LoginInteractor {
    void login(String user, String password, String captcha);
    void getCaptchaImage();
}
