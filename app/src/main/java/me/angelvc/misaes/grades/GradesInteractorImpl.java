package me.angelvc.misaes.grades;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;

import me.angelvc.misaes.grades.contracts.GradesContracts;
import me.angelvc.misaes.grades.events.GradesEvent;
import me.angelvc.saes.scraper.SAESchoolsUrls;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.models.GradeEntry;

public class GradesInteractorImpl implements GradesContracts.Interactor {
    @Override
    public void getGrades() {
        new Thread(() -> {
            SAEScraper saes = SAEScraper.getInstance(SAESchoolsUrls.School.UPIICSA);
            GradesEvent event;
            ArrayList<GradeEntry> grades = new ArrayList<>();
            try {
                grades = saes.getGrades();
                if (grades.size() == 0){
                    event = new GradesEvent(GradesEvent.Type.GRADES_EMPTY, grades);
                }
                else {
                    event = new GradesEvent(GradesEvent.Type.GRADES_READY, grades);
                }
            } catch (IOException e) {
                e.printStackTrace();
                event = new GradesEvent(GradesEvent.Type.ERROR, grades);
            }
            EventBus.getDefault().post(event);

        }).start();

    }
}
