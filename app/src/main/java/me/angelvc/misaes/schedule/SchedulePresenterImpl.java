package me.angelvc.misaes.schedule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.angelvc.misaes.schedule.contracts.ScheduleContracts;
import me.angelvc.misaes.schedule.events.ScheduleEvent;

public class SchedulePresenterImpl implements ScheduleContracts.Presenter {

    ScheduleContracts.View view;
    ScheduleContracts.Interactor interactor;

    public SchedulePresenterImpl(ScheduleContracts.View view){
        this.view = view;
        interactor = new ScheduleInteractorImpl();
    }

    @Override
    public void load() {
        EventBus.getDefault().register(this);
        interactor.getGrades();
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onEvent(ScheduleEvent event) {
        view.hideProgress();
        switch (event.getEventType()){
            case SCHEDULE_READY:
                view.showSchedule(event.getSchedule());
                break;

            case SCHEDULE_EMPTY:
                view.showEmptySchedule();

            case ERROR:
                view.showEmptySchedule();
                view.showError();
                break;
        }
    }

}
