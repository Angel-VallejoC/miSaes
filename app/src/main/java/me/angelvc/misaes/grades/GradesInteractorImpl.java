package me.angelvc.misaes.grades;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import me.angelvc.misaes.grades.contracts.GradesContracts;
import me.angelvc.misaes.grades.events.GradesEvent;
import me.angelvc.misaes.util.Cache;
import me.angelvc.misaes.util.CacheItem;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.exceptions.SessionExpiredException;
import me.angelvc.saes.scraper.models.GradeEntry;

public class GradesInteractorImpl implements GradesContracts.Interactor {

    private SAEScraper scraper;

    public GradesInteractorImpl(SAEScraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void getGrades(Context context) {
        new Thread(() -> {
            GradesEvent event;
            ArrayList<GradeEntry> grades;

            if (Cache.exists(Cache.Type.GRADES, context)){
                CacheItem item = Cache.get(Cache.Type.GRADES, context);
                grades = item.getGrades();

                event = grades.size() == 0
                        ?  new GradesEvent(GradesEvent.Type.GRADES_EMPTY, grades)
                        :  new GradesEvent(GradesEvent.Type.GRADES_READY, grades);
            }
            else {
                try {
                    grades = scraper.getGrades();
                    Cache.save(grades, context);

                    event = grades.size() == 0
                            ?  new GradesEvent(GradesEvent.Type.GRADES_EMPTY, grades)
                            :  new GradesEvent(GradesEvent.Type.GRADES_READY, grades);
                }
                catch (SessionExpiredException e){
                    grades = new ArrayList<>();
                    event = new GradesEvent(GradesEvent.Type.ERROR_SESSION_EXPIRED, grades);
                }
                catch (Exception e) {
                    grades = new ArrayList<>();
                    e.printStackTrace();
                    event = new GradesEvent(GradesEvent.Type.ERROR, grades);
                }
            }

            EventBus.getDefault().post(event);
        }).start();

    }
}
