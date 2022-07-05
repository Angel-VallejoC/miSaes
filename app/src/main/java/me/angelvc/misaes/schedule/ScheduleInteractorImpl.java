package me.angelvc.misaes.schedule;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import me.angelvc.misaes.schedule.contracts.ScheduleContracts;
import me.angelvc.misaes.schedule.events.ScheduleEvent;
import me.angelvc.misaes.util.Cache;
import me.angelvc.misaes.util.CacheItem;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.exceptions.SessionExpiredException;
import me.angelvc.saes.scraper.models.ScheduleClass;

public class ScheduleInteractorImpl implements ScheduleContracts.Interactor {

    private SAEScraper scraper;

    public ScheduleInteractorImpl(SAEScraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void getSchedule(Context context) {
        new Thread(() -> {
            ScheduleEvent event ;
            List<ScheduleClass> schedule;

            if (Cache.exists(Cache.Type.SCHEDULE, context)){
                CacheItem item = Cache.get(Cache.Type.SCHEDULE, context);
                schedule = item.getSchedule();

                event = schedule.size() == 0
                        ? new ScheduleEvent(ScheduleEvent.Type.SCHEDULE_EMPTY, schedule)
                        : new ScheduleEvent(ScheduleEvent.Type.SCHEDULE_READY, schedule);
            }
            else {
                try {
                    schedule = scraper.getStudentSchedule();
                    Cache.save(schedule, context);
                    event = schedule.size() == 0
                            ? new ScheduleEvent(ScheduleEvent.Type.SCHEDULE_EMPTY, schedule)
                            : new ScheduleEvent(ScheduleEvent.Type.SCHEDULE_READY, schedule);

                }
                catch (SessionExpiredException e){
                    event = new ScheduleEvent(ScheduleEvent.Type.ERROR_SESSION_EXPIRED, null);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    event = new ScheduleEvent(ScheduleEvent.Type.ERROR, null);
                }
            }

            EventBus.getDefault().post(event);

        }).start();

    }
}
