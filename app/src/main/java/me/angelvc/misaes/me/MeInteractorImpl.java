package me.angelvc.misaes.me;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import me.angelvc.misaes.me.contracts.MeContracts;
import me.angelvc.misaes.me.events.MeInfoEvent;
import me.angelvc.saes.scraper.SAESchoolsUrls;
import me.angelvc.saes.scraper.SAEScraper;
import me.angelvc.saes.scraper.models.StudentInfo;

public class MeInteractorImpl implements MeContracts.Interactor {
    @Override
    public void getInfo() {
        new Thread(() -> {
            SAEScraper saes = SAEScraper.getInstance(SAESchoolsUrls.School.UPIICSA);
            MeInfoEvent event;
            StudentInfo info;
            try {
                info = saes.getStudentInfo();
                event = new MeInfoEvent(MeInfoEvent.Type.INFO_READY, info);
            } catch (IOException e) {
                e.printStackTrace();
                event = new MeInfoEvent(MeInfoEvent.Type.ERROR, null);
            }
            EventBus.getDefault().post(event);

        }).start();

    }
}
