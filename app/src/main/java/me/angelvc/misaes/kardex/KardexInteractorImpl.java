package me.angelvc.misaes.kardex;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import me.angelvc.misaes.kardex.contracts.KardexContracts;
import me.angelvc.misaes.kardex.events.KardexEvent;
import me.angelvc.misaes.util.Cache;
import me.angelvc.misaes.util.CacheItem;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.exceptions.SessionExpiredException;
import me.angelvc.saes.scraper.models.Kardex;

public class KardexInteractorImpl implements KardexContracts.Interactor {

    private SAEScraper scraper;

    public KardexInteractorImpl(SAEScraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void getKardexGrades(Context context) {
        new Thread(() -> {
            KardexEvent event = new KardexEvent();
            Kardex kardex;

            if (Cache.exists(Cache.Type.KARDEX, context)){
                CacheItem item = Cache.get(Cache.Type.KARDEX, context);
                kardex = item.getKardex();

                if (kardex.size() == 0){
                    event.setType(KardexEvent.Type.KARDEX_EMPTY);
                }
                else {
                    event.setType(KardexEvent.Type.KARDEX_GRADES_READY);
                    event.setKardex(kardex);
                }
            }
            else {
                try {
                    kardex = scraper.getKardex();
                    Cache.save(kardex,context);

                    if (kardex.size() == 0){
                        event.setType(KardexEvent.Type.KARDEX_EMPTY);
                    }
                    else {
                        event.setType(KardexEvent.Type.KARDEX_GRADES_READY);
                        event.setKardex(kardex);
                    }
                }
                catch (SessionExpiredException e){
                    event.setType(KardexEvent.Type.ERROR_SESSION_EXPIRED);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    event.setType(KardexEvent.Type.ERROR);
                }
            }

            EventBus.getDefault().post(event);
        }).start();

    }
}
