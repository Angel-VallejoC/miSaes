package me.angelvallejo.misaes.scraper.models;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Kardex {

    private HashMap<Integer, Pair<String, ArrayList<KardexClass>>> kardex;

    public Kardex() {
        kardex = new HashMap<>();
    }

    public void addClass(int level, String levelName, KardexClass _class) {
        if (!kardex.containsKey(level))
            kardex.put(level, new Pair<String, ArrayList<KardexClass>>(levelName, new ArrayList<KardexClass>()) {
            });

        //noinspection ConstantConditions
        kardex.get(level).second.add(_class);
    }

    public int size() {
        return kardex.size();
    }

    public Pair<String, ArrayList<KardexClass>> getLevelClasses(int level) {

        if (!kardex.containsKey(level))
            throw new IllegalArgumentException("Level " + level + " does not exist");

        return kardex.get(level);
    }
}
