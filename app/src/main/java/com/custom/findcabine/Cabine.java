package com.custom.findcabine;

import android.content.ContentValues;

import com.custom.findcabine.data.CabineContract.CabineEntry;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by AmmarRabie on 19/03/2018.
 */

public class Cabine {

    private String name;
    private String desc;
    private long id;
    private LatLng location;

    private ArrayList<String> history;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }


    public void addToHistory(String itemHistory) {
        history.add(itemHistory);
    }


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(CabineEntry.COLUMN_CABINE_DESC, getDesc());
        values.put(CabineEntry.COLUMN_CABINE_LATITUDE, getLocation().latitude);
        values.put(CabineEntry.COLUMN_CABINE_LONGITUDE, getLocation().longitude);
        values.put(CabineEntry.COLUMN_CABINE_NAME, getName());
//        values.put(CabineEntry._ID, getId());

        return values;
    }
}
