package com.custom.findcabine;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.custom.findcabine.abstrct.CabinObserver;
import com.custom.findcabine.common.Cabin;
import com.custom.findcabine.common.CableType;

import java.util.ArrayList;

/**
 * This class represent the list of the cabins and the selected one.
 * Once the selected is changed, the cabinListView and the cabinMapView should also change
 */
public class CabinsSubject {

    private static final String TAG = "CabinsSubject";

    private ArrayList<Cabin> cabins;

    private ArrayList<CabinObserver> observers;

    private int currSelected;


    public CabinsSubject(ArrayList<Cabin> cabins) {
        this.cabins = cabins;
        observers = new ArrayList<>();
    }

    void attachObserver(CabinObserver observer) {
        observers.add(observer);
        observer.update();
    }

    public int getCurrSelected() {
        return currSelected;
    }

    public void setCurrSelected(int selected) {

        if (selected >= cabins.size()) return;
        this.currSelected = selected;
        notifyAllObservers();
    }

    public void setCurrSelected(String fullId, CableType type) {
        setCurrSelected(getPositionOf(fullId, type));
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
        return cabins.get(index);
    }


    public int getPositionOf(String cabinId, CableType type) {
        for (int i = 0; i < cabins.size(); i++) {
            boolean sameId = cabins.get(i).getFullId().equals(cabinId);
            boolean sameType = cabins.get(i).getType().name().equals(type.name());
            if (sameId && sameType)
                return i;
        }
        Log.w(TAG, "getPositionOf: the cabinId " + cabinId + "not exist");
        return -1;
    }


    public int getSize() {
        return cabins.size();
    }
}
