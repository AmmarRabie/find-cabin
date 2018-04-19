package com.custom.findcabine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.findcabine.data.CabineContract;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

public class AddCabineActivity extends AppCompatActivity {


    private final int PLACE_PICKER_REQUEST = 646;
    private EditText mNameView;
    private EditText mDescView;
    private EditText mIdView;
    private Button mTakeLocationView;
    private TextView mLocationView;
    private boolean isNewCabine = true;
    private Cabin mCurrCabin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cabine);

        if (getIntent().hasExtra(getString(R.string.intentKey_cabineId))) {
            mCurrCabin.setCabinId(getIntent().getStringExtra(getString(R.string.intentKey_cabineId)));
            isNewCabine = false;
            // TODO: get all the cabine info from the database
        } else {
            mCurrCabin = new Cabin(cableId);
        }

        mIdView = findViewById(R.id.addCabineActivity_id);
        mDescView = findViewById(R.id.addCabineActivity_desc);
        mNameView = findViewById(R.id.addCabineActivity_name);
        mLocationView = findViewById(R.id.addCabineActivity_location);
        mTakeLocationView = findViewById(R.id.addCabineActivity_takeLocation);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addcabine, menu);
        return true;
    }

/*    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
*//*        if (isNewCabine) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }*//*
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.menuAddCabine_save:
                // Save pet to database
                if (saveCabine())
                    // Exit activity
                    finish();

                return true;

/*            case android.R.id.home:
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean saveCabine() {
        String name = mNameView.getText().toString();
        String id = mIdView.getText().toString();
        String desc = mDescView.getText().toString();

        mCurrCabin.setCabinId(id);
        mCurrCabin.setAddress(desc);
        mCurrCabin.setName(name);

        if (mCurrCabin.isValid()) {
            Uri newUri = getContentResolver().insert(CabineContract.CabineEntry.CONTENT_URI,
                    mCurrCabin.toContentValues());

            if (newUri == null) {
                Toast.makeText(this, "This cabine can't be inserted right now",
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        }
        Toast.makeText(this, "this data isn't valid", Toast.LENGTH_SHORT).show();
        return false;
    }


    public void onTakeLocation(View v) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException
                | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                LatLng latLng = place.getLatLng();
//                Toast.makeText(this, place.getViewport().toString(), Toast.LENGTH_LONG).show();
                mCurrCabin.setLocation(latLng);
//                mCurrCabin.setViewPort(place.getViewport());

                mLocationView.setText("" + latLng.toString());

                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

}
