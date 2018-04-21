package com.custom.findcabine.common;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by AmmarRabie on 19/03/2018.
 */

public class Cabin implements Serializable{
    private String cableId;
    private String cabinId;
    private String address;
    private LatLng location;
    private CableType type;

    public Cabin() {
    }


    public Cabin(String cableId, String cabinId, CableType type, String address, LatLng location) {
        this.cableId = cableId;
        this.cabinId = cabinId;
        this.type = type;
        this.address = address;
        this.location = location;
    }


    public Cabin(String fullId, String address, LatLng location) {
        this(fullId, address);
        this.location = location;
    }

    public Cabin(String fullId, String address, LatLng location, CableType type) {
        this(fullId, address);
        this.location = location;
        this.type = type;
    }

    public Cabin(String fullId, String address) {
        String[] ids = fullId.split(",");
        if (ids.length != 2)
            throw new IllegalArgumentException("full id can't be split to only 2 ids");
        this.cableId = ids[0];
        this.cabinId = ids[1];
        this.address = address;
    }

    public boolean isValid() {
        if (cableId == null || cabinId == null)
            return false;

        return cabinId.matches("[0-9]{3,}") && cabinId.length() == 3;
    }


    public String getCableId() {
        return cableId;
    }

    public String getCabinId() {
        return cabinId;
    }

    public String getFullId() {
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

    public CableType getType() {
        return type;
    }
}
