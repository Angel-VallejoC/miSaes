package me.angelvc.misaes.me.events;

import me.angelvc.saes.scraper.models.StudentInfo;

public class MeInfoEvent {

    public enum Type {
        INFO_READY,
        ERROR,
        ERROR_SESSION_EXPIRED
    }

    private Type eventType;
    private StudentInfo info;

    public MeInfoEvent(Type eventType, StudentInfo info) {
        this.eventType = eventType;
        this.info = info;
    }

    public Type getEventType() {
        return eventType;
    }

    public StudentInfo getInfo() {
        return info;
    }
}
