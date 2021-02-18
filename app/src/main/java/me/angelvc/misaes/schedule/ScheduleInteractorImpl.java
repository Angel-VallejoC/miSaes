package me.angelvc.misaes.schedule;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.angelvc.misaes.schedule.contracts.ScheduleContracts;
import me.angelvc.misaes.schedule.events.ScheduleEvent;
import me.angelvc.saes.scraper.SAESchoolsUrls;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.models.GradeEntry;
import me.angelvc.saes.scraper.models.ScheduleClass;

public class ScheduleInteractorImpl implements ScheduleContracts.Interactor {
    @Override
    public void getGrades() {
        new Thread(() -> {
            SAEScraper saes = SAEScraper.getInstance(SAESchoolsUrls.School.UPIICSA);
            ScheduleEvent event;
            List<ScheduleClass> schedule;
            try {
                schedule = saes.getStudentSchedule();
                if (schedule.size() == 0){
                    event = new ScheduleEvent(ScheduleEvent.Type.SCHEDULE_EMPTY, schedule);
                }
                else {
                    event = new ScheduleEvent(ScheduleEvent.Type.SCHEDULE_READY, schedule);
                }
            } catch (IOException e) {
                e.printStackTrace();
                event = new ScheduleEvent(ScheduleEvent.Type.ERROR, null);
            }
            EventBus.getDefault().post(event);

        }).start();

    }
}
