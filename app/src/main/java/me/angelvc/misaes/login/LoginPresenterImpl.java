package me.angelvc.misaes.login;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.angelvc.misaes.login.Contracts.LoginInteractor;
import me.angelvc.misaes.login.Contracts.LoginPresenter;
import me.angelvc.misaes.login.Contracts.LoginView;
import me.angelvc.misaes.login.Events.LoginEvent;
import me.angelvc.misaes.login.fragments.EnterCredentialsFragments;
import me.angelvc.misaes.util.AppPreferences;
import me.angelvc.misaes.util.Cache;
import me.angelvc.saes.scraper.SAEScraper;

public class LoginPresenterImpl implements LoginPresenter {
    private static final String TAG = "LoginPresenterImpl";

    private LoginView view;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView view, SAEScraper scraper){
        this.view = view;
        this.interactor = new LoginInteractorImpl(scraper);
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
    public void login(String user, String password, String captcha, boolean rememberMe) {
        view.showProgress();
        interactor.login(user, password, captcha, rememberMe);
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    @Override
    public void onEventListener(LoginEvent event) {
        if (view != null){
            view.hideProgress();

            Context context = ((EnterCredentialsFragments) view).getActivity();
            switch (event.getType()){
                case LOGIN_SUCCESSFUL:
                        AppPreferences.setLoginStatus( context, true);

                        if (!event.getUser().concat(AppPreferences.getSelectedSchool(context)).equals(AppPreferences.getSessionId(context))){
                            AppPreferences.saveSessionId(context, event.getUser());
                            Cache.deleteAll(context);

                        }

                        if (event.isRememberMeChecked()){
                            AppPreferences.setRememberMeStatus(context, true);
                            AppPreferences.saveUserAndPassword(context, event.getUser(), event.getPassword());
                        }
                        else {
                            AppPreferences.setRememberMeStatus(context, false);
                            AppPreferences.removeUserAndPassword(context);
                        }
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
