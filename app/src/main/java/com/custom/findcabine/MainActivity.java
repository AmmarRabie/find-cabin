package com.custom.findcabine;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.findcabine.data.CabineContract.CabineEntry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>,
        OnMapReadyCallback {

    private static final int CABINE_LOADER = 125;

    private String mCurrCabinNumber = "";

    // views
    private TextView mNumberView;
    private TextView mNameView;
    private TextView mDescView;
    private TextView mLocationView;


    private GoogleMap mGoogleMap;

    private Cabin cabin;

    private boolean isNumbersVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberView = findViewById(R.id.mainActivity_number);
        mDescView = findViewById(R.id.mainActivity_desc);
        mLocationView = findViewById(R.id.mainActivity_location);
        mNameView = findViewById(R.id.mainActivity_name);

        /*mCabinesList = */
        AppUtil.insertInitialCabins(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainActivity_map);
        mapFragment.getMapAsync(this);
/*        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException
                | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }


    public void onNumberClicked(View view) {
        String numberClicked = ((Button) view).getText().toString();
        appendCabineNumber(numberClicked);
    }

    public void onClearClick(View view) {
        setCabineNumber("");
    }

    private void appendCabineNumber(String s) {
        if (mCurrCabinNumber.length() == 4) {
            Toast.makeText(this, "clear the data first", Toast.LENGTH_SHORT).show();
            return;
        }

        mCurrCabinNumber += s;
        mNumberView.setText(mCurrCabinNumber);

        if (mCurrCabinNumber.length() == 3) {
            showCabineInfoIfExist(mCurrCabinNumber);
        }

    }

    private void showCabineInfoIfExist(String mCurrCabineNumber) {
        if (getLoaderManager().getLoader(CABINE_LOADER) == null) {
            getLoaderManager().initLoader(CABINE_LOADER, null, this);
            return;
        }
        getLoaderManager().restartLoader(CABINE_LOADER, null, this);
    }

    private void setCabineNumber(String s) {
        mCurrCabinNumber = s;
        mNumberView.setText(mCurrCabinNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_addNewCabine:
                Intent intent = new Intent(this, AddCabineActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                CabineEntry._ID,
                CabineEntry.COLUMN_CABINE_DESC,
                CabineEntry.COLUMN_CABINE_LATITUDE,
                CabineEntry.COLUMN_CABINE_LONGITUDE,
                CabineEntry.COLUMN_CABINE_NAME};

        String selc = CabineEntry._ID + "=?";
        String[] selcArgs = new String[]{mCurrCabinNumber};
/*        Uri currentPetUri = ContentUris.withAppendedId
                (CabineEntry.CONTENT_URI, mId*//*Long.valueOf(cabin.getCabinId())*//*);*/
        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                CabineEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                selc,                   // No selection clause
                selcArgs,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null) {
            Toast.makeText(this, "the cursor = null", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CabineEntry.COLUMN_CABINE_NAME));
            String id = cursor.getString(cursor.getColumnIndex(CabineEntry._ID));
            String desc = cursor.getString(cursor.getColumnIndex(CabineEntry.COLUMN_CABINE_DESC));
            Double latitude = cursor.getDouble(cursor.getColumnIndex(CabineEntry.COLUMN_CABINE_LATITUDE));
            Double longitude = cursor.getDouble(cursor.getColumnIndex(CabineEntry.COLUMN_CABINE_LONGITUDE));

            Cabin cabin = new Cabin(cableId);

            cabin.setLocation(new LatLng(latitude, longitude));
            cabin.setName(name);
            cabin.setAddress(desc);
            cabin.setCabinId(id);
            onCabineFetched(cabin);
        }
    }

    private void onCabineFetched(Cabin cabin) {

        this.cabin = cabin;
        mNameView.setText(cabin.getFullId());
        mLocationView.setText(cabin.getLocation().latitude + "/" + cabin.getLocation().longitude);
        mDescView.setText(cabin.getAddress());

        cabin.setMarker(
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(cabin.getLocation())
                        .title(cabin.getFullId())
                        .snippet(cabin.getAddress())
                )
        );

//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cabin.getLocation(), 21));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cabin.getLocation(), 16),
                new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        Toast.makeText(MainActivity.this,
                                "ta da", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }
                });

        hideNumbers();
    }

    private void hideNumbers() {
        if (!isNumbersVisible)
            return;
        findViewById(R.id.mainActivity_numbersContainer).setVisibility(View.GONE);
        isNumbersVisible = false;
    }

    private void showNumbers() {
        if (isNumbersVisible)
            return;
        findViewById(R.id.mainActivity_numbersContainer).setVisibility(View.VISIBLE);
        isNumbersVisible = true;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void showNumbers(View v) {
        showNumbers();
    }

}
