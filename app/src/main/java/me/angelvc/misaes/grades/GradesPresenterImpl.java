package me.angelvc.misaes.grades;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.angelvc.misaes.grades.contracts.GradesContracts;
import me.angelvc.misaes.grades.events.GradesEvent;
import me.angelvc.misaes.home.HomeActivity;
import me.angelvc.saes.scraper.SAEScraper;

public class GradesPresenterImpl implements GradesContracts.Presenter {

    GradesContracts.View view;
    GradesContracts.Interactor interactor;

    public GradesPresenterImpl(GradesContracts.View view, SAEScraper scraper){
        this.view = view;
        interactor = new GradesInteractorImpl(scraper);
    }

    @Override
    public void load() {
        EventBus.getDefault().register(this);
        interactor.getGrades(((HomeActivity)((GradesFragment) view).getActivity()));
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onEvent(GradesEvent event) {
        view.hideProgress();
        switch (event.getEventType()){
            case GRADES_READY:
                view.showGrades(event.getGrades());
                break;

            case GRADES_EMPTY:
                view.showEmptyGrades();
                break;

            case ERROR:
                view.showEmptyGrades();
                view.showError();
                break;

            case ERROR_SESSION_EXPIRED:
                ((HomeActivity)((GradesFragment) view).getActivity()).logout();
                break;
        }
    }

}
