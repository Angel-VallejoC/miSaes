package me.angelvc.misaes.schedule;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import me.angelvc.misaes.schedule.contracts.ScheduleContracts;
import me.angelvc.misaes.schedule.events.ScheduleEvent;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.models.ScheduleClass;

public class ScheduleInteractorImpl implements ScheduleContracts.Interactor {

    private SAEScraper scraper;

    public ScheduleInteractorImpl(SAEScraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void getGrades() {
        new Thread(() -> {
            ScheduleEvent event;
            List<ScheduleClass> schedule;
            try {
                schedule = scraper.getStudentSchedule();
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
