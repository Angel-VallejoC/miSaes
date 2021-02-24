package me.angelvc.misaes.kardex;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import me.angelvc.misaes.kardex.contracts.KardexContracts;
import me.angelvc.misaes.kardex.events.KardexEvent;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.models.Kardex;

public class KardexInteractorImpl implements KardexContracts.Interactor {

    private SAEScraper scraper;

    public KardexInteractorImpl(SAEScraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void getKardexGrades() {
        new Thread(() -> {
            KardexEvent event = new KardexEvent();
            try {
                Kardex kardex = scraper.getKardex();
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
