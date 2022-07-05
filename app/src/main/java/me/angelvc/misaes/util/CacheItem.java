package me.angelvc.misaes.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import me.angelvc.saes.scraper.models.GradeEntry;
import me.angelvc.saes.scraper.models.Kardex;
import me.angelvc.saes.scraper.models.ScheduleClass;
import me.angelvc.saes.scraper.models.StudentInfo;

public class CacheItem {

    private LocalDateTime savedAt;
    private Cache.Type type;
    private Kardex kardex;
    private StudentInfo studentInfo;
    private ArrayList<GradeEntry> grades;
    private List<ScheduleClass> schedule;


    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }

    public Cache.Type getType() {
        return type;
    }

    public void setType(Cache.Type type) {
        this.type = type;
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public ArrayList<GradeEntry> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<GradeEntry> grades) {
        this.grades = grades;
    }

    public List<ScheduleClass> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleClass> schedule) {
        this.schedule = schedule;
    }
}
