package me.angelvc.misaes.kardex.contracts;

import me.angelvc.misaes.kardex.events.KardexEvent;
import me.angelvc.saes.scraper.models.Kardex;

public interface KardexContracts {

    interface View {
        void showKardexGrades(Kardex kardex);
        void showEmptyKardex();

        void showProgress();
        void hideProgress();

        void showError();
    }

    interface Presenter {
        void load();
        void stop();

        void onEvent(KardexEvent event);
    }

    interface Interactor {
        void getKardexGrades();
    }
}
