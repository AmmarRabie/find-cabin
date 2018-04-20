package com.custom.findcabine;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback
        , AAH_FabulousFragment.Callbacks {

    //
    private SearchAnimatedFragment dialogFrag;
    private FloatingActionButton fab;
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

        fab = (FloatingActionButton) findViewById(R.id.mainActivity_fab);
//        mTextRepresentationView = findViewById(R.id.mainActivity_textRepresentation);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);

        textObserver = new TextObserver(mTextRepresentationView);

        dialogFrag = SearchAnimatedFragment.newInstance();
        dialogFrag.setParentFab(fab);

        cabins = new CabinsSubject(AppUtil.getInitialCabins());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainActivity_map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.mainActivity_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapObserver = new MapObserver(googleMap);
    }


    public void onClearClick(View view) {
        setCabineNumber("");
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
        switch (item.getItemId()) {
            case R.id.action_searchById:
                showSearchDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSearchDialog() {
        dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
    }


    @Override
    public void onResult(Object result) {
        Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
    }


    public class CabinPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 3;

        public CabinPagerAdapter(FragmentManager fragmentManager, int size) {
            super(fragmentManager);
            NUM_ITEMS = size;
        }

        // Returns total number of pages.
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for a particular page.
        @Override
        public Fragment getItem(int position) {
            if (position != cabins.getCurrSelected())
                cabins.setCurrSelected(position);

            return PagerFragment.newInstance(cabins.getCabinAt(position));
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab " + position;
        }

    }

}
