package me.angelvc.misaes.grades;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import me.angelvc.misaes.grades.contracts.GradesContracts;
import me.angelvc.misaes.grades.events.GradesEvent;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.exceptions.SessionExpiredException;
import me.angelvc.saes.scraper.models.GradeEntry;

public class GradesInteractorImpl implements GradesContracts.Interactor {

    private SAEScraper scraper;

    public GradesInteractorImpl(SAEScraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void getGrades() {
        new Thread(() -> {
            GradesEvent event;
            ArrayList<GradeEntry> grades = new ArrayList<>();
            try {
                grades = scraper.getGrades();
                if (grades.size() == 0){
                    event = new GradesEvent(GradesEvent.Type.GRADES_EMPTY, grades);
                }
                else {
                    event = new GradesEvent(GradesEvent.Type.GRADES_READY, grades);
                }
            }
            catch (SessionExpiredException e){
                event = new GradesEvent(GradesEvent.Type.ERROR_SESSION_EXPIRED, grades);
            }
            catch (Exception e) {
                e.printStackTrace();
                event = new GradesEvent(GradesEvent.Type.ERROR, grades);
            }
            EventBus.getDefault().post(event);

        }).start();

    }
}
