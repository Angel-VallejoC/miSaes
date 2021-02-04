package me.angelvc.misaes.kardex;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import me.angelvc.misaes.kardex.contracts.KardexInteractor;
import me.angelvc.misaes.kardex.events.KardexEvent;
import me.angelvc.saes.scraper.SAESchoolsUrls;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.models.Kardex;

public class KardexInteractorImpl implements KardexInteractor {
    @Override
    public void getKardexGrades() {
        new Thread(() -> {
            SAEScraper saes = SAEScraper.getInstance(SAESchoolsUrls.School.UPIICSA);
            KardexEvent event = new KardexEvent();
            try {
                Kardex kardex = saes.getKardex();
                if (kardex.size() == 0){
                    event.setType(KardexEvent.Type.KARDEX_EMPTY);
                }
                else {
                    event.setType(KardexEvent.Type.KARDEX_GRADES_READY);
                    event.setKardex(kardex);
                }
            } catch (IOException e) {
                e.printStackTrace();
                event.setType(KardexEvent.Type.ERROR);
            }
            EventBus.getDefault().post(event);

        }).start();

    }
}
