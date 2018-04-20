package com.custom.findcabine;

import android.support.v4.view.ViewPager;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public class TextObserver extends CabinObserver {

    private ViewPager mTextView;

    public TextObserver(CabinsSubject subject_, ViewPager textView) {
        super(subject_);
        subject.attach(this);
        this.mTextView = textView;
    }

    @Override
    void update() {
        mTextView.setCurrentItem(subject.getCurrSelected(), true);
    }
}
