package me.angelvc.misaes.grades.events;

import java.util.ArrayList;

import me.angelvc.saes.scraper.models.GradeEntry;

public class GradesEvent {

    public enum Type {
        GRADES_READY,
        GRADES_EMPTY,
        ERROR,
        ERROR_SESSION_EXPIRED
    }

    private Type eventType;
    private ArrayList<GradeEntry> grades;

    public GradesEvent(Type eventType, ArrayList<GradeEntry> grades) {
        this.eventType = eventType;
        this.grades = grades;
    }

    public Type getEventType() {
        return eventType;
    }

    public ArrayList<GradeEntry> getGrades() {
        return grades;
    }
}
