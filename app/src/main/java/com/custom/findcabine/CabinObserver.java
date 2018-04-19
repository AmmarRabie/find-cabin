package com.custom.findcabine;

import javax.security.auth.Subject;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public abstract class CabinObserver {


    protected CabinsSubject subject;

    abstract void update();

}
