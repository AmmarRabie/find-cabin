package com.custom.findcabine.abstrct;

import com.custom.findcabine.CabinsSubject;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public abstract class CabinObserver {


    protected CabinsSubject subject;

    public CabinObserver(CabinsSubject subject) {
        this.subject = subject;
    }

    public abstract void update();

}
