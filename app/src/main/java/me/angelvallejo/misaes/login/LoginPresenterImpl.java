package me.angelvallejo.misaes.login;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.angelvallejo.misaes.login.Contracts.LoginInteractor;
import me.angelvallejo.misaes.login.Contracts.LoginPresenter;
import me.angelvallejo.misaes.login.Contracts.LoginView;
import me.angelvallejo.misaes.login.Events.LoginEvent;

public class LoginPresenterImpl implements LoginPresenter {
    private static final String TAG = "LoginPresenterImpl";

    private LoginView view;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView view){
        this.view = view;
        this.interactor = new LoginInteractorImpl();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        view.showProgress();
        interactor.getCaptchaImage();
    }

    @Override
    public void onDestroy() {
        view = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void login(String user, String password, String captcha) {
        view.showProgress();
        interactor.login(user, password, captcha);
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    @Override
    public void onEventListener(LoginEvent event) {
        if (view != null){
            view.hideProgress();

            switch (event.getType()){
                case LOGIN_SUCCESSFUL:
                        view.loginSuccessful();
                    break;
                case CAPTCHA_IMAGE_DOWNLOADED:
                        view.showCaptcha(event.getCaptchaImage());
                    break;
                case LOGIN_ERROR:
                        view.showError(event.getMessage());
                        interactor.getCaptchaImage();
                    break;
            }
        }
    }
}
