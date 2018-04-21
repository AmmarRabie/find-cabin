package com.custom.findcabine.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.custom.findcabine.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * This is to test the google map on its own activity
 */
public class MapTestActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private LatLng mLatLng;
//    private TilesFrameLayout mTilesFrameLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

//        mTilesFrameLayout = (TilesFrameLayout) findViewById(R.id.tiles_frame_layout);
//        mTilesFrameLayout.setOnAnimationFinishedListener(this);

        mLatLng = new LatLng(45, 54);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(mLatLng)
                .title("Ammar test map"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
//        googleMap.setMyLocationEnabled(false);
//        mTilesFrameLayout.startAnimation();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        mTilesFrameLayout.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mTilesFrameLayout.onPause();
    }
}
