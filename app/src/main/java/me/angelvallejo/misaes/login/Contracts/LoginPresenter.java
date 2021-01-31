package me.angelvallejo.misaes.login.Contracts;

import me.angelvallejo.misaes.login.Events.LoginEvent;

public interface LoginPresenter {
    void onCreate();
    void onResume();
    void onDestroy();

    void login(String user, String password, String captcha);

    void onEventListener(LoginEvent event);
}
