package me.angelvc.misaes.grades.contracts;

import android.content.Context;

import java.util.ArrayList;

import me.angelvc.misaes.grades.events.GradesEvent;
import me.angelvc.saes.scraper.models.GradeEntry;

public interface GradesContracts {

    interface View {
        void showGrades(ArrayList<GradeEntry> grades);
        void showEmptyGrades();
        void showProgress();
        void hideProgress();
        void showError();
    }

    interface Presenter {
        void load();
        void stop();
        void onEvent(GradesEvent event);
    }

    interface Interactor {
        void getGrades(Context context);
    }
}
