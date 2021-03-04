package me.angelvc.misaes.me;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.angelvc.misaes.home.HomeActivity;
import me.angelvc.misaes.me.contracts.MeContracts;
import me.angelvc.misaes.me.events.MeInfoEvent;
import me.angelvc.saes.scraper.SAEScraper;

public class MePresenterImpl implements MeContracts.Presenter {

    MeContracts.View view;
    MeContracts.Interactor interactor;

    public MePresenterImpl(MeContracts.View view, SAEScraper scraper){
        this.view = view;
        interactor = new MeInteractorImpl(scraper);
    }

    @Override
    public void load() {
        EventBus.getDefault().register(this);
        interactor.getInfo();
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onEvent(MeInfoEvent event) {
        view.hideProgress();
        switch (event.getEventType()){
            case INFO_READY:
                view.showInfo(event.getInfo());
                break;

            case ERROR:
                view.showError();
                break;

            case ERROR_SESSION_EXPIRED:
                ((HomeActivity)((MeFragment) view).getActivity()).logout();
        }
    }

}
