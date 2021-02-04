package me.angelvc.misaes.kardex.contracts;

import me.angelvc.saes.scraper.models.Kardex;

public interface KardexView {
    void showKardexGrades(Kardex kardex);
    void showEmptyKardex();

    void showProgress();
    void hideProgress();

    void showError();
}
