package com.custom.findcabine;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public abstract class CabinObserver {


    protected CabinsSubject subject;

    public CabinObserver(CabinsSubject subject) {
        this.subject = subject;
    }

    abstract void update();

}
