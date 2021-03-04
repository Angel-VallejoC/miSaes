package me.angelvc.misaes.login;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.angelvc.misaes.login.Contracts.LoginInteractor;
import me.angelvc.misaes.login.Contracts.LoginPresenter;
import me.angelvc.misaes.login.Contracts.LoginView;
import me.angelvc.misaes.login.Events.LoginEvent;
import me.angelvc.misaes.login.fragments.EnterCredentialsFragments;
import me.angelvc.misaes.util.AppPreferences;
import me.angelvc.saes.scraper.SAEScraper;

public class LoginPresenterImpl implements LoginPresenter {
    private static final String TAG = "LoginPresenterImpl";

    private LoginView view;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView view, SAEScraper scraper){
        this.view = view;
        this.interactor = new LoginInteractorImpl(scraper);
        interactor.getCaptchaImage();
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
                        AppPreferences.setLoginStatus( ((EnterCredentialsFragments) view).getActivity(), true);
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
