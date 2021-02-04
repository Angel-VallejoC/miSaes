package me.angelvc.misaes.kardex.events;

import me.angelvc.saes.scraper.models.Kardex;

public class KardexEvent {
    public enum Type {
        KARDEX_GRADES_READY,
        KARDEX_EMPTY,
        ERROR
    }

    private Type type;
    private Kardex kardex;

    public KardexEvent() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }
}
