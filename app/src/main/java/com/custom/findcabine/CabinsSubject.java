package com.custom.findcabine;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * This class represent the list of the cabins and the selected one.
 * Once the selected is changed, the cabinListView and the cabinMapView should also change
 */
public class CabinsSubject {

    private ArrayList<Cabin> cabins;

    private ArrayList<CabinObserver> observers = new ArrayList<>();

    private int currSelected;


    public CabinsSubject(ArrayList<Cabin> cabins) {
        this.cabins = cabins;
    }

    void attachObserver(CabinObserver observer) {
        observers.add(observer);
        observer.update();
    }

    public int getCurrSelected() {
        return currSelected;
    }

    public boolean setCurrSelected(int selected) {

        if (selected >= cabins.size()) return false;
        this.currSelected = selected;
        notifyAllObservers();
        return true;
    }

    public void attach(CabinObserver observer) {
        observers.add(observer);
    }

    private void notifyAllObservers() {
        for (CabinObserver observer : observers) {
            observer.update();
        }
    }


    public @NonNull
    Cabin getCurrCabin() {
        return cabins.get(currSelected);
    }

    public @Nullable
    Cabin getCabinAt(int index) {
        if (index >= cabins.size()) return null;
        return cabins.get(currSelected);
    }


}
