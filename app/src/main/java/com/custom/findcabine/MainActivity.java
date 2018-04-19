package com.custom.findcabine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private String mCurrCabinNumber = "";

    // views
    private CabinTextRepresentation mTextRepresentationView;
    private TextObserver textObserver;
    private MapObserver mapObserver;

    private CabinsSubject cabins;


    private Cabin cabin;

    private boolean isNumbersVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextRepresentationView = findViewById(R.id.mainActivity_textRepresentation);
        textObserver = new TextObserver(mTextRepresentationView);

        cabins = new CabinsSubject(AppUtil.getInitialCabins());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainActivity_map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.mainActivity_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "djksfnk", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapObserver = new MapObserver(googleMap);

        Intent intent = new Intent(this,MapTestActivity.class);
        startActivity(intent);
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
//        mNumberView.setText(mCurrCabinNumber);

        if (mCurrCabinNumber.length() == 3) {
//            showCabineInfoIfExist(mCurrCabinNumber);
        }

    }



    private void setCabineNumber(String s) {
        mCurrCabinNumber = s;
//        mNumberView.setText(mCurrCabinNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_searchById:
                showSearchDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSearchDialog() {

    }

    private void hideNumbers() {
        if (!isNumbersVisible)
            return;
//        findViewById(R.id.mainActivity_numbersContainer).setVisibility(View.GONE);
        isNumbersVisible = false;
    }

    private void showNumbers() {
        if (isNumbersVisible)
            return;
//        findViewById(R.id.mainActivity_numbersContainer).setVisibility(View.VISIBLE);
        isNumbersVisible = true;
    }


    public void showNumbers(View v) {
        showNumbers();
    }

}
