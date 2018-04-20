package com.custom.findcabine;

import android.content.Intent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public class MapObserver extends CabinObserver {


    private GoogleMap mMapView;
    private HashMap<String, Marker> cabinMarkers;
    private String lastSelectedMarkerKey = null;

    public MapObserver(final CabinsSubject subject , GoogleMap mapView) {
        super(subject);
        subject.attach(this);
        this.mMapView = mapView;
        cabinMarkers = new HashMap<String, Marker>();

        // [TODO]: move the logic of updating the subject in the mainActivity
        mMapView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                subject.setCurrSelected(Integer.valueOf(marker.getTag().toString()));
                return true;
            }
        });
    }

    @Override
    void update() {
        Cabin updatedCabin = subject.getCurrCabin();
        String fullId = updatedCabin.getFullId();
        LatLng location = updatedCabin.getLocation();
        Marker marker = cabinMarkers.get(fullId);
        if (marker == null)
        {
            BitmapDescriptor copperIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            BitmapDescriptor fiberIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .title(fullId)
                    .icon(updatedCabin.getType() == CableType.COPPER ? copperIcon:fiberIcon)
                    .snippet(updatedCabin.getAddress());

            marker = mMapView.addMarker(markerOptions);
            marker.setTag(subject.getCurrSelected());
            cabinMarkers.put(fullId,marker);
            lastSelectedMarkerKey = fullId;
        }
        mMapView.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 21));
        inVisibleAll();
        marker.setVisible(true);
    }

    private void inVisibleAll() {
        Set<String> strings = cabinMarkers.keySet();
        for (String key: strings)
        {
            cabinMarkers.get(key).setVisible(false);
        }
    }


}
