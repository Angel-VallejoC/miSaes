package me.angelvc.misaes.login.Contracts;

import me.angelvc.misaes.login.Events.LoginEvent;

public interface LoginPresenter {
    void onCreate();
    void onResume();
    void onDestroy();

    void login(String user, String password, String captcha, boolean rememberMe);

    void onEventListener(LoginEvent event);
}
