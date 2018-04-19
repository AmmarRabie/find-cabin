package com.custom.findcabine;

import android.widget.TextView;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public class TextObserver extends CabinObserver {

    private CabinTextRepresentation mTextView;

    public TextObserver(CabinTextRepresentation textView) {
        this.mTextView = textView;
    }

    @Override
    void update() {
        Cabin updatedCabin = subject.getCurrCabin();
        mTextView.setAddress(updatedCabin.getAddress());
        mTextView.setId(updatedCabin.getFullId());
        mTextView.setType(CableType.FIBER);
    }
}
