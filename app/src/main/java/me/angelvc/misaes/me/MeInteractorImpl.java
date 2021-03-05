package me.angelvc.misaes.me;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import me.angelvc.misaes.me.contracts.MeContracts;
import me.angelvc.misaes.me.events.MeInfoEvent;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.exceptions.SessionExpiredException;
import me.angelvc.saes.scraper.models.StudentInfo;

public class MeInteractorImpl implements MeContracts.Interactor {

    private SAEScraper scraper;

    public MeInteractorImpl(SAEScraper scraper) {
        this.scraper = scraper;
    }

    @Override
    public void getInfo() {
        new Thread(() -> {
            MeInfoEvent event;
            StudentInfo info;
            try {
                info = scraper.getStudentInfo();
                event = new MeInfoEvent(MeInfoEvent.Type.INFO_READY, info);
            }
            catch (SessionExpiredException e){
                Log.d("debug", "getInfo: session expired");
                event = new MeInfoEvent(MeInfoEvent.Type.ERROR_SESSION_EXPIRED, null);
            }
            catch (Exception e) {
                e.printStackTrace();
                Log.d("debug", "getInfo: exception " + e.getMessage());
                event = new MeInfoEvent(MeInfoEvent.Type.ERROR, null);
            }
            EventBus.getDefault().post(event);

        }).start();

    }
}
