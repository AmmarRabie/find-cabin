package com.custom.findcabine;

/**
 * Created by AmmarRabie on 20/04/2018.
 */

interface IdStateChangeListener {

    void onCableIdValid();

    void onFullIdValid();

    /**
     * called once the cable id was valid and then become invalid
     */
    void onCableIdInvalid();

    /**
     * called once the cabin id was valid and then become invalid
     */
    void onFullIdInvalid();

}
