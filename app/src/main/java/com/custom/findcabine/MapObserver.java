package com.custom.findcabine;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public class MapObserver extends CabinObserver {


    private GoogleMap mMapView;
    private HashMap<String, MarkerOptions> cabinMarkers;
    private String lastSelectedMarkerKey = null;

    public MapObserver(GoogleMap mapView) {
        this.mMapView = mapView;
        cabinMarkers = new HashMap<String, MarkerOptions>();
    }

    @Override
    void update() {
        Cabin updatedCabin = subject.getCurrCabin();
        String fullId = updatedCabin.getFullId();
        LatLng location = updatedCabin.getLocation();
        MarkerOptions marker = cabinMarkers.get(fullId);
        if (marker == null)
        {
            BitmapDescriptor copperIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            BitmapDescriptor fiberIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            marker = new MarkerOptions()
                    .position(location)
                    .title(fullId)
                    .icon(updatedCabin.getType() == CableType.COPPER ? copperIcon:fiberIcon)
                    .snippet(updatedCabin.getAddress());

            cabinMarkers.put(fullId,marker);
            lastSelectedMarkerKey = fullId;
        }
        mMapView.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 21));
        inVisibleAll();
        marker.visible(true);
    }

    private void inVisibleAll() {
        Set<String> strings = cabinMarkers.keySet();
        for (String key: strings)
        {
            cabinMarkers.get(key).visible(false);
        }
    }


}
