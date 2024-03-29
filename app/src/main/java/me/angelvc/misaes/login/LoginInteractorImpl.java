package me.angelvc.misaes.login;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import me.angelvc.misaes.login.Contracts.LoginInteractor;
import me.angelvc.misaes.login.Events.LoginEvent;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.util.Pair;

public class LoginInteractorImpl implements LoginInteractor {

    private static final String TAG = "LoginInteractorImpl";

    private SAEScraper saes;

    public LoginInteractorImpl(SAEScraper scraper){
        saes = scraper;
    }

    @Override
    public void login(String user, String password, String captcha, boolean rememberMe) {
        if (saes == null){
            post(LoginEvent.Type.LOGIN_ERROR, "Error al ingresar a SAES", null);
        }

        new Thread(() -> {
            try {
                Pair<Boolean, String > result = saes.login(user, password, captcha);
                if (result.getKey()){
                    postLoginSuccess(user, password, rememberMe);
                }
                else {
                    post(LoginEvent.Type.LOGIN_ERROR, result.getValue(), null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                post(LoginEvent.Type.LOGIN_ERROR, "Error al iniciar sesión", null);
            }
        }).start();
    }

    @Override
    public void getCaptchaImage() {
        if (saes == null){
            post(LoginEvent.Type.LOGIN_ERROR, "Error al ingresar a SAES", null);
            return;
        }

        new Thread(() -> {
            try {
                byte[] image = saes.loadLoginPage();
                post(LoginEvent.Type.CAPTCHA_IMAGE_DOWNLOADED, "", image);
            } catch (IOException e) {
                e.printStackTrace();
                post(LoginEvent.Type.LOGIN_ERROR, "Error al cargar página de inicio", null);
            }
        }).start();
    }

    public void post(LoginEvent.Type type, String message, byte[] image){
        LoginEvent event = new LoginEvent(type, message, image);
        EventBus.getDefault().post(event);
    }

    public void postLoginSuccess(String user, String password, boolean rememberMe){
        LoginEvent event = new LoginEvent(LoginEvent.Type.LOGIN_SUCCESSFUL, "", null);
        event.setUser(user);
        event.setPassword(password);
        event.setRememberMe(rememberMe);
        EventBus.getDefault().post(event);
    }
}
