package com.custom.findcabine.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custom.findcabine.R;
import com.custom.findcabine.common.CableType;

/**
 * Created by AmmarRabie on 19/04/2018.
 */

public class CabinTextRepresentation extends LinearLayout {
    View rootView;
    TextView idView;
    TextView typeView;
    TextView addressView;

    public CabinTextRepresentation(Context context) {
        super(context);
        init(context);
    }

    public CabinTextRepresentation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        rootView = inflate(context, R.layout.cv_cabin_text_view, this);
        idView = rootView.findViewById(R.id.cabinTextView_id);
        typeView = rootView.findViewById(R.id.cabinTextView_type);
        addressView = rootView.findViewById(R.id.cabinTextView_address);
    }


    public void setId(String fullId) {
        idView.setText(fullId);
    }

    public void setId(String cableId, String cabinId) {
        idView.setText("(" + cableId + ", " + cabinId + ")");
    }


    public void setType(CableType type) {
        switch (type) {
            case FIBER:
                rootView.setBackgroundResource(R.color.color_FiberColor);
                typeView.setText(R.string.fiber);
                break;
            case COPPER:
                rootView.setBackgroundResource(R.color.color_CooperColor);
                typeView.setText(R.string.copper);
                break;
        }
        updateTextColors(type);
    }

    private void updateTextColors(CableType type) {
        switch (type) {
            case FIBER:
                addressView.setTextColor(getResources().getColor(R.color.white));
                idView.setTextColor(getResources().getColor(R.color.white));
                typeView.setTextColor(getResources().getColor(R.color.white));
                break;
            case COPPER:
                addressView.setTextColor(getResources().getColor(R.color.white));
                idView.setTextColor(getResources().getColor(R.color.white));
                typeView.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }


    public void setAddress(String address) {
        addressView.setText(address);
    }
}
