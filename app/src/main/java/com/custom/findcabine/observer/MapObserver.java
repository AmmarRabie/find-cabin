package com.custom.findcabine.observer;

import com.custom.findcabine.CabinsSubject;
import com.custom.findcabine.abstrct.CabinObserver;
import com.custom.findcabine.common.AppUtil;
import com.custom.findcabine.common.Cabin;
import com.custom.findcabine.common.CableType;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public class MapObserver extends CabinObserver {


    private GoogleMap mMapView;
    private HashMap<Integer, Marker> cabinMarkers;


    public MapObserver(final CabinsSubject subject, GoogleMap mapView, HashMap<Integer,Marker> markers) {
        super(subject);
        subject.attach(this);
        this.mMapView = mapView;
        cabinMarkers = markers;
    }

    @Override
    public void update() {
        Cabin updatedCabin = subject.getCurrCabin();
        LatLng location = updatedCabin.getLocation();
        Marker marker = cabinMarkers.get(subject.getCurrSelected());

        // update view
        mMapView.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
        reloadAll();
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

    }

    private void reloadAll() {
/*        for (CabinIdType key : cabinMarkers.keySet()) {
            BitmapDescriptor copperIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            BitmapDescriptor fiberIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
            cabinMarkers.get(key).setIcon(key.getType() == CableType.COPPER ? copperIcon : fiberIcon);
        }*/
        for (Marker marker : cabinMarkers.values()) {
            marker.setIcon(AppUtil.getIconForCabin(subject.getCabinAt(((int) marker.getTag()))));
        }
    }

}
