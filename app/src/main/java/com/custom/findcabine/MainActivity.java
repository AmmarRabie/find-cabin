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
        , AAH_FabulousFragment.Callbacks
 {

    //
    private SearchAnimatedFragment dialogFrag;
    private FloatingActionButton fab;
    private ViewPager mViewPager;
    private CabinPagerAdapter mCabinPagerAdapter;
    private TextObserver textObserver;
    private MapObserver mapObserver;
    private CabinsSubject cabins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cabins = new CabinsSubject(AppUtil.getInitialCabins());

        fab = (FloatingActionButton) findViewById(R.id.mainActivity_fab);
//        mTextRepresentationView = findViewById(R.id.mainActivity_textRepresentation);
        mViewPager = (ViewPager) findViewById(R.id.vpPager);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                cabins.setCurrSelected(position);
            }
        });

        mCabinPagerAdapter = new CabinPagerAdapter(getSupportFragmentManager(), AppUtil.getInitialCabins().size());
        mViewPager.setAdapter(mCabinPagerAdapter);

        textObserver = new TextObserver(cabins, mViewPager);

        dialogFrag = SearchAnimatedFragment.newInstance();
        dialogFrag.setParentFab(fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
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
        mapObserver = new MapObserver(cabins, googleMap);
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
        dialogFrag.show(getSupportFragmentManager(),dialogFrag.getTag());
        lastPositionBeforeSearch = cabins.getCurrSelected();
    }


    @Override
    public void onResult(Object result) {
        String resultedString = result.toString();
        Toast.makeText(this, resultedString, Toast.LENGTH_LONG).show();
        if (resultedString.equals("closed") || resultedString.equals("swiped_down"))
        {
            cabins.setCurrSelected(lastPositionBeforeSearch);
        }
        // resultedString = the id of the search
//        cabins.setCurrSelected(resultedString);
    }

    private int lastPositionBeforeSearch;
/*    @Override
    public void onIdValid(String fullId) {
        cabins.setCurrSelected(fullId);
    }*/


    public class CabinPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 3;

        public CabinPagerAdapter(FragmentManager fragmentManager, int size) {
            super(fragmentManager);
            NUM_ITEMS = size;
        }

        // Returns total number of pages.
        @Override
        public int getCount() {
            return NUM_ITEMS - 1;
        }

        // Returns the fragment to display for a particular page.
        @Override
        public Fragment getItem(int position) {
            return PagerFragment.newInstance(cabins.getCabinAt(position));
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Cabin " + (position + 1);
        }


    }

}
