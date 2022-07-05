package me.angelvc.misaes.util;

import static me.angelvc.misaes.util.AppPreferences.getAppPreferences;

import android.content.Context;

import com.google.gson.Gson;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import me.angelvc.saes.scraper.models.GradeEntry;
import me.angelvc.saes.scraper.models.Kardex;
import me.angelvc.saes.scraper.models.ScheduleClass;
import me.angelvc.saes.scraper.models.StudentInfo;

public class Cache {

    public enum Type {
        GRADES,
        KARDEX,
        STUDENT_INFO,
        SCHEDULE
    }

    private static final Gson gson = new Gson();


    public static boolean exists(Type type, Context context){

        String cacheString = getAppPreferences(context).getString(type.toString(), null);

        if(cacheString == null)
            return false;


        CacheItem item = gson.fromJson(cacheString, CacheItem.class);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(item.getSavedAt(), now);

        switch (type){
            case KARDEX:
            case STUDENT_INFO:
            case SCHEDULE:
                if (duration.toDays() > 5){
                    getAppPreferences(context).edit().remove(type.toString()).apply();
                    return false;
                }
                return true;

            case GRADES:
                if (duration.toHours() > 24){
                    getAppPreferences(context).edit().remove(type.toString()).apply();
                    return false;
                }
                return true;
        }

        return false;
    }


    public static CacheItem get(Type type, Context context){
        String cacheString = getAppPreferences(context).getString(type.toString(), null);
        return gson.fromJson(cacheString, CacheItem.class);
    }

    public static void delete(Type type, Context context){
        getAppPreferences(context).edit().remove(type.toString()).apply();
    }

    public static void deleteAll(Context context){
        delete(Type.GRADES, context);
        delete(Type.KARDEX, context);
        delete(Type.SCHEDULE, context);
        delete(Type.STUDENT_INFO, context);
    }

    public static void save(Kardex kardex, Context context){
        CacheItem item = new CacheItem();
        item.setSavedAt(LocalDateTime.now());
        item.setKardex(kardex);
        getAppPreferences(context).edit().putString(Type.KARDEX.toString(), gson.toJson(item)).apply();
    }

    public static void save(StudentInfo studentInfo, Context context){
        CacheItem item = new CacheItem();
        item.setSavedAt(LocalDateTime.now());
        item.setStudentInfo(studentInfo);
        getAppPreferences(context).edit().putString(Type.STUDENT_INFO.toString(), gson.toJson(item)).apply();
    }

    public static void save(ArrayList<GradeEntry> grades, Context context){
        CacheItem item = new CacheItem();
        item.setSavedAt(LocalDateTime.now());
        item.setGrades(grades);
        getAppPreferences(context).edit().putString(Type.GRADES.toString(), gson.toJson(item)).apply();
    }

    public static void save(List<ScheduleClass> schedule, Context context){
        CacheItem item = new CacheItem();
        item.setSavedAt(LocalDateTime.now());
        item.setSchedule(schedule);
        getAppPreferences(context).edit().putString(Type.SCHEDULE.toString(), gson.toJson(item)).apply();
    }

}
