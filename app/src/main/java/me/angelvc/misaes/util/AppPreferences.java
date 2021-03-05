package me.angelvc.misaes.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.atomic.AtomicReference;

import me.angelvc.misaes.R;
import me.angelvc.saes.scraper.SAEScraper;


public class AppPreferences {

    public static boolean getLoginStatus(Context context) {
        return getAppPreferences(context).getBoolean(
                context.getString(R.string.app_login_status), false
        );
    }

    public static void setLoginStatus(Context context, boolean status) {
        getAppPreferences(context)
                .edit().putBoolean(
                context.getString(R.string.app_login_status),
                status
        ).apply();

    }

    public static void saveScraperInstance(Context context, SAEScraper scraper) {
        Gson gson = new
                GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes field) {
                        return field.getDeclaringClass() == SAEScraper.class && field.getName().equals("workingDocument");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        Log.d("debug", "saveScraperInstance: json=" + gson.toJson(scraper));
        getAppPreferences(context).edit().putString(
                context.getString(R.string.app_scraper_instance), gson.toJson(scraper)
        ).apply();
    }

    public static void deleteScraperInstance(Context context) {
        getAppPreferences(context).edit().remove(
                context.getString(R.string.app_scraper_instance)
        ).apply();
    }

    public static String getScraperJson(Context context) {
        Log.d("debug", "getScraperInstanceJson: json=" + getAppPreferences(context)
                .getString(context.getString(R.string.app_scraper_instance), null));
        return getAppPreferences(context)
                .getString(context.getString(R.string.app_scraper_instance), null);
    }

    public static SAEScraper getScraperInstance(String json) throws Exception {
        Gson gson = new
                GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes field) {
                        return field.getDeclaringClass() == SAEScraper.class && field.getName().equals("workingDocument");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        SAEScraper scraper = gson.fromJson(json, SAEScraper.class);
        AtomicReference<Pair<Boolean, Exception>> result = new AtomicReference<>();
        Thread reloadScraperInstance = new Thread(() -> {
            try {
                scraper.reload();
                result.set(new Pair<>(true, null));
            } catch (Exception e) {
                result.set(new Pair<>(false, e));
            }
        });
        reloadScraperInstance.start();
        while (reloadScraperInstance.isAlive()) ;

        if (!result.get().first)
            throw result.get().second;

        return scraper;
    }

    public static String getSelectedSchool(Context context) {
        return getAppPreferences(context)
                .getString(context.getString(R.string.login_school_preference), null);
    }

    public static SharedPreferences getAppPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_preferences_key),
                Context.MODE_PRIVATE);
    }


}
