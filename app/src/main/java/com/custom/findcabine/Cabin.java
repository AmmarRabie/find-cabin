package com.custom.findcabine;

import android.content.ContentValues;

import com.custom.findcabine.data.CabineContract.CabineEntry;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by AmmarRabie on 19/03/2018.
 */

public class Cabin {
    private String cableId;
    private String cabinId;
    private String address;
    private LatLng location;
    private Marker marker;


    public Cabin() {
    }


    public Cabin(String cableId, String cabinId, String address, LatLng location) {
        this.cableId = cableId;
        this.cabinId = cabinId;
        this.address = address;
        this.location = location;
    }


    public Cabin(String fullId, String address, LatLng location) {
        this(fullId, address);
        this.location = location;
    }

    public Cabin(String fullId, String address) {
        String[] ids = fullId.split(",");
        if (ids.length != 2)
            throw new IllegalArgumentException("full id can't be split to only 2 ids");
        this.cableId = ids[0];
        this.cabinId = ids[1];
        this.address = address;
    }

    public boolean isMarkedOnMap() {
        return marker != null;
    }

    /**
     * @return true if the marker is actually exist, false if it not associated with any map
     */
    public boolean removeFromMap() {
        if (isMarkedOnMap()) {
            marker.remove();
            marker = null;
            return true;
        }
        return false;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(CabineEntry.COLUMN_CABINE_DESC, getAddress());
        values.put(CabineEntry.COLUMN_CABINE_LATITUDE, getLocation().latitude);
        values.put(CabineEntry.COLUMN_CABINE_LONGITUDE, getLocation().longitude);
        values.put(CabineEntry._ID, getCabinId());

        return values;
    }

    public boolean isValid() {
        if (cabinId == null)
            return false;

        return cabinId.matches("[0-9]{3,}") && cabinId.length() == 3;
    }



    public String getCableId() {
        return cableId;
    }

    public String getCabinId() {
        return cabinId;
    }

    public String getFullId()
    {
        return cableId + "," + cabinId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Marker getMarker() {
        return marker;
    }
}
