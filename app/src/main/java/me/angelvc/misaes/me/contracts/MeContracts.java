package me.angelvc.misaes.me.contracts;

import android.content.Context;

import me.angelvc.misaes.me.events.MeInfoEvent;
import me.angelvc.saes.scraper.models.StudentInfo;

public interface MeContracts {

    interface View {
        void showInfo(StudentInfo info);
        void showProgress();
        void hideProgress();
        void showError();
    }

    interface Presenter {
        void load();
        void stop();
        void onEvent(MeInfoEvent event);
    }

    interface Interactor {
        void getInfo(Context context);
    }
}
