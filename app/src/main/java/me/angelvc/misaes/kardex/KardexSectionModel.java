package me.angelvc.misaes.kardex;

import java.util.List;

import me.angelvc.saes.scraper.models.KardexClass;

public class KardexSectionModel {

    private final List<KardexClass> classes;
    private boolean isExpanded;

    public KardexSectionModel(List<KardexClass> classes){
        this.classes = classes;
        isExpanded = false;
    }

    public List<KardexClass> getClasses() {
        return classes;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
