package me.angelvc.misaes.kardex;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.angelvc.misaes.kardex.contracts.KardexContracts;
import me.angelvc.misaes.kardex.events.KardexEvent;
import me.angelvc.saes.scraper.SAEScraper;

public class KardexPresenterImpl implements KardexContracts.Presenter {

    KardexContracts.View view;
    KardexContracts.Interactor interactor;

    public KardexPresenterImpl(KardexContracts.View view, SAEScraper scraper){
        this.view = view;
        interactor = new KardexInteractorImpl(scraper);
    }

    @Override
    public void load() {
        EventBus.getDefault().register(this);
        interactor.getKardexGrades();
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onEvent(KardexEvent event) {
        view.hideProgress();
        switch (event.getType()){
            case KARDEX_GRADES_READY:
                view.showKardexGrades(event.getKardex());
                break;

            case KARDEX_EMPTY:
                view.showEmptyKardex();

            case ERROR:
                view.showEmptyKardex();
                view.showError();
                break;
        }
    }

}
