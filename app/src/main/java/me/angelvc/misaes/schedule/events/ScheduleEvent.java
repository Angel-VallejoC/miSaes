package me.angelvc.misaes.schedule.events;

import java.util.ArrayList;
import java.util.List;

import me.angelvc.saes.scraper.models.GradeEntry;
import me.angelvc.saes.scraper.models.ScheduleClass;

public class ScheduleEvent {

    public enum Type {
        SCHEDULE_READY,
        SCHEDULE_EMPTY,
        ERROR
    }

    private final Type eventType;
    private final List<ScheduleClass> schedule;

    public ScheduleEvent(Type eventType, List<ScheduleClass> schedule) {
        this.eventType = eventType;
        this.schedule = schedule;
    }

    public Type getEventType() {
        return eventType;
    }

    public List<ScheduleClass> getSchedule() {
        return schedule;
    }
}
