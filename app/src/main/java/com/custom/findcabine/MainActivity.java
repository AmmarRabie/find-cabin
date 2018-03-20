package com.custom.findcabine;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.findcabine.data.CabineContract.CabineEntry;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements
        NumbersFragment.OnFragmentInteractionListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PET_LOADER = 125;
    private int PLACE_PICKER_REQUEST = 1;
    private String mCurrCabineNumber = "";
    private TextView mNumberView;
    private TextView mNameView;
    private TextView mDescView;
    private TextView mLocationView;
    private Cabine cabine;

    private long mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberView = findViewById(R.id.mainActivity_number);

        mDescView = findViewById(R.id.mainActivity_desc);
        mLocationView = findViewById(R.id.mainActivity_location);
        mNameView = findViewById(R.id.mainActivity_name);


        cabine = new Cabine();
        cabine.setName("cabine al3aresh");
        cabine.setDesc("this is the cabine layed in the aressh alharam street");
        cabine.setId(1);
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
                cabine.setLocation(latLng);

                Uri uri = getContentResolver().insert(CabineEntry.CONTENT_URI, cabine.toContentValues());
                mId = ContentUris.parseId(uri);

                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public void onNumberClicked(View view) {
        String numberClicked = ((Button) view).getText().toString();
        appendCabineNumber(numberClicked);
    }


    public void onClearClick(View view) {
        setCabineNumber("");
    }

    private void appendCabineNumber(String s) {
        if (mCurrCabineNumber.length() == 4) {
            Toast.makeText(this, "clear the data first", Toast.LENGTH_SHORT).show();
            return;
        }

        mCurrCabineNumber += s;
        mNumberView.setText(mCurrCabineNumber);

        if (mCurrCabineNumber.length() == 3) {
            showCabineInfoIfExist(mCurrCabineNumber);
        }

    }

    private void showCabineInfoIfExist(String mCurrCabineNumber) {
        getLoaderManager().initLoader(PET_LOADER, null, this);
    }

    private void setCabineNumber(String s) {
        mCurrCabineNumber = s;
        mNumberView.setText(mCurrCabineNumber);
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

        Uri currentPetUri = ContentUris.withAppendedId
                (CabineEntry.CONTENT_URI, mId/*Long.valueOf(cabine.getId())*/);
        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                currentPetUri,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null) {
            Toast.makeText(this, "the cursor = null", Toast.LENGTH_SHORT).show();
        }
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CabineEntry.COLUMN_CABINE_NAME));
//        String id = cursor.getString(cursor.getColumnIndex(CabineEntry._ID));
            String desc = cursor.getString(cursor.getColumnIndex(CabineEntry.COLUMN_CABINE_DESC));
            Double latitude = cursor.getDouble(cursor.getColumnIndex(CabineEntry.COLUMN_CABINE_LATITUDE));
            Double longitude = cursor.getDouble(cursor.getColumnIndex(CabineEntry.COLUMN_CABINE_LONGITUDE));

            mNameView.append(name);
            mLocationView.append(latitude.toString() + "/" + longitude.toString());
            mDescView.append(desc);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
