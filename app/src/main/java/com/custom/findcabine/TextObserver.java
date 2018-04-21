package com.custom.findcabine;

import android.support.v4.view.ViewPager;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public class TextObserver extends CabinObserver {

    private ViewPager mViewPager;

    public TextObserver(CabinsSubject subject_, ViewPager textView) {
        super(subject_);
        subject.attach(this);
        this.mViewPager = textView;
    }

    @Override
    void update() {
        mViewPager.setCurrentItem(subject.getCurrSelected(), true);
    }
}
