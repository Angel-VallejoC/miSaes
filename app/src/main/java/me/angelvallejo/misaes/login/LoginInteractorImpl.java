package me.angelvallejo.misaes.login;

import android.util.Pair;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import me.angelvallejo.misaes.login.Contracts.LoginInteractor;
import me.angelvallejo.misaes.login.Events.LoginEvent;
import me.angelvallejo.misaes.scraper.SAEScraper;

public class LoginInteractorImpl implements LoginInteractor {

    private static final String TAG = "LoginInteractorImpl";

    private SAEScraper saes;

    public LoginInteractorImpl(){
        saes = SAEScraper.getInstance();
    }

    @Override
    public void login(String user, String password, String captcha) {
        if (saes == null){
            post(LoginEvent.Type.LOGIN_ERROR, "Error al ingresar a SAES", null);
        }

        new Thread(() -> {
            try {
                Pair<Boolean, String > result = saes.login(user, password, captcha);
                if (result.first){
                    post(LoginEvent.Type.LOGIN_SUCCESSFUL, "", null);
                }
                else {
                    post(LoginEvent.Type.LOGIN_ERROR, result.second, null);
                }
            } catch (IOException e) {
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
}
