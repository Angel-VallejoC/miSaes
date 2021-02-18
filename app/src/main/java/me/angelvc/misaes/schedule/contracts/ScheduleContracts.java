package me.angelvc.misaes.schedule.contracts;

import java.util.ArrayList;
import java.util.List;

import me.angelvc.misaes.schedule.events.ScheduleEvent;
import me.angelvc.saes.scraper.models.GradeEntry;
import me.angelvc.saes.scraper.models.ScheduleClass;

public interface ScheduleContracts {

    interface View {
        void showSchedule(List<ScheduleClass> schedule);
        void showEmptySchedule();
        void showProgress();
        void hideProgress();
        void showError();
    }

    interface Presenter {
        void load();
        void stop();
        void onEvent(ScheduleEvent event);
    }

    interface Interactor {
        void getGrades();
    }
}
