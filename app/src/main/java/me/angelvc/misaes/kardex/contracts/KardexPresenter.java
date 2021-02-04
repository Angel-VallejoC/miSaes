package me.angelvc.misaes.kardex.contracts;

import me.angelvc.misaes.kardex.events.KardexEvent;

public interface KardexPresenter {
    void onAttach();
    void onDetach();

    void onEvent(KardexEvent event);
}
