package me.angelvc.misaes.me;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import me.angelvc.misaes.me.contracts.MeContracts;
import me.angelvc.misaes.me.events.MeInfoEvent;
import me.angelvc.misaes.util.Cache;
import me.angelvc.misaes.util.CacheItem;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.exceptions.SessionExpiredException;
import me.angelvc.saes.scraper.models.StudentInfo;

public class MeInteractorImpl implements MeContracts.Interactor {

    private SAEScraper scraper;

    public MeInteractorImpl(SAEScraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void getInfo(Context context) {
        new Thread(() -> {
            MeInfoEvent event;
            StudentInfo info;

            if (Cache.exists(Cache.Type.STUDENT_INFO, context)){
                CacheItem item = Cache.get(Cache.Type.STUDENT_INFO, context);
                info = item.getStudentInfo();
                event = new MeInfoEvent(MeInfoEvent.Type.INFO_READY, info);
            }
            else {
                try {
                    info = scraper.getStudentInfo();
                    Cache.save(info,context);
                    event = new MeInfoEvent(MeInfoEvent.Type.INFO_READY, info);
                }
                catch (SessionExpiredException e){
                    event = new MeInfoEvent(MeInfoEvent.Type.ERROR_SESSION_EXPIRED, null);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    event = new MeInfoEvent(MeInfoEvent.Type.ERROR, null);
                }
            }

            EventBus.getDefault().post(event);

        }).start();

    }
}
