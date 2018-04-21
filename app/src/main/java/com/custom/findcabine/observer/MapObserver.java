package com.custom.findcabine.observer;

import com.custom.findcabine.abstrct.CabinObserver;
import com.custom.findcabine.CabinsSubject;
import com.custom.findcabine.common.Cabin;
import com.custom.findcabine.common.CableType;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public class MapObserver extends CabinObserver {


    private GoogleMap mMapView;
    private HashMap<CabinIdType, Marker> cabinMarkers;
    private String lastSelectedMarkerKey = null;

    public MapObserver(final CabinsSubject subject , GoogleMap mapView) {
        super(subject);
        subject.attach(this);
        this.mMapView = mapView;
        cabinMarkers = new HashMap<CabinIdType, Marker>();

        // [TODO]: move the logic of updating the subject in the mainActivity
        mMapView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                subject.setCurrSelected(Integer.valueOf(marker.getTag().toString()));
                return false; // to also view the snippet
            }
        });
    }

    @Override
    public void update() {
        Cabin updatedCabin = subject.getCurrCabin();
        CabinIdType cabinIdType = new CabinIdType(updatedCabin.getFullId(),updatedCabin.getType());
        LatLng location = updatedCabin.getLocation();
        Marker marker = cabinMarkers.get(cabinIdType);
        if (marker == null)
        {
            BitmapDescriptor copperIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            BitmapDescriptor fiberIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .title(cabinIdType.getId())
                    .icon(updatedCabin.getType() == CableType.COPPER ? copperIcon:fiberIcon)
                    .snippet(updatedCabin.getAddress());

            marker = mMapView.addMarker(markerOptions);
            marker.setTag(subject.getCurrSelected());
            cabinMarkers.put(new CabinIdType(cabinIdType.getId(), cabinIdType.getType()), marker);
            lastSelectedMarkerKey = cabinIdType.getId();
        }
        mMapView.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
        inVisibleAll();
        marker.setVisible(true);
    }

    private void inVisibleAll() {
        for (CabinIdType key: cabinMarkers.keySet())
        {
//            cabinMarkers.get(key).setVisible(false);
        }
    }



    public static final class CabinIdType
    {
        String id;
        CableType type;

        public CabinIdType(String id, CableType type) {
            this.id = id;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public CableType getType() {
            return type;
        }

        public void setType(CableType type) {
            this.type = type;
        }
    }
}
