package me.angelvc.misaes.kardex;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import me.angelvc.misaes.kardex.contracts.KardexInteractor;
import me.angelvc.misaes.kardex.contracts.KardexPresenter;
import me.angelvc.misaes.kardex.contracts.KardexView;
import me.angelvc.misaes.kardex.events.KardexEvent;

public class KardexPresenterImpl implements KardexPresenter {

    KardexView view;
    KardexInteractor interactor;

    public KardexPresenterImpl(KardexView view){
        this.view = view;
        interactor = new KardexInteractorImpl();
    }

    @Override
    public void onAttach() {
        EventBus.getDefault().register(this);
        interactor.getKardexGrades();
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onEvent(KardexEvent event) {
        switch (event.getType()){
            case KARDEX_GRADES_READY:
                view.hideProgress();
                view.showKardexGrades(event.getKardex());
                break;

            case KARDEX_EMPTY:
                view.hideProgress();
                view.showEmptyKardex();

            case ERROR:
                view.showError();
                break;
        }
    }

}
