package com.custom.findcabine;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

/**
 * Created by AmmarRabie on 20/04/2018.
 */

public class SearchAnimatedFragment extends AAH_FabulousFragment implements
        View.OnClickListener
        , IdStateChangeListener {
    private static final String TAG = "SearchAnimatedFragment";

    private static int shadowHeight;
    private String mCurrCabinNumber;
    private TextView mCableIdView;
    private TextView mCabinIdView;
    private ViewGroup mFullIdParentView;
    private TextView typeView;
    private ImageView commaDoneView;
    private ImageView closeDialogView;
    private FButton changeTypeView;
    private FullIdSubject fullIdSubject;
    private boolean isCableIdJustValid = false;
    private boolean isFullIdValid = false;
    private boolean isCableIdValid = false;
    private boolean commaInserted = false;
    private FragCallback callback;


    public SearchAnimatedFragment() {
    }

    public static SearchAnimatedFragment newInstance() {
        SearchAnimatedFragment fragment = new SearchAnimatedFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.dfrag_pickid, null);
        commaDoneView = contentView.findViewById(R.id.imgbtn_commaDone);
        closeDialogView = contentView.findViewById(R.id.dfragPickId_close);
        mCableIdView = contentView.findViewById(R.id.dfragPickId_currCableId);
        mCabinIdView = contentView.findViewById(R.id.dfragPickId_currCabinId);
        mFullIdParentView = contentView.findViewById(R.id.fullIdParedt);
        typeView = contentView.findViewById(R.id.dfragPickId_showTypeView);
        changeTypeView = contentView.findViewById(R.id.changeTypeView);

        changeTypeView.setOnClickListener(this);
        contentView.findViewById(R.id.num0).setOnClickListener(this);
        contentView.findViewById(R.id.num1).setOnClickListener(this);
        contentView.findViewById(R.id.num2).setOnClickListener(this);
        contentView.findViewById(R.id.num3).setOnClickListener(this);
        contentView.findViewById(R.id.num4).setOnClickListener(this);
        contentView.findViewById(R.id.num5).setOnClickListener(this);
        contentView.findViewById(R.id.num6).setOnClickListener(this);
        contentView.findViewById(R.id.num7).setOnClickListener(this);
        contentView.findViewById(R.id.num8).setOnClickListener(this);
        contentView.findViewById(R.id.num9).setOnClickListener(this);
        contentView.findViewById(R.id.dfragPickId_clearNumbers).setOnClickListener(this);
        initCommaDoneViewBtn();
        initCloseDialogBtn();

        shadowHeight = changeTypeView.getShadowHeight();

        fullIdSubject = new FullIdSubject(AppUtil.getInitialCabins()
                , this, mCableIdView, mCabinIdView,
                getCurrentType());


        //params to set
        setAnimationDuration(450); //optional; default 500ms
        setPeekHeight(400); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setViewgroupStatic(contentView.findViewById(R.id.staticDialogView));
        setViewMain(contentView.findViewById(R.id.mainDialogView));
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof FragCallback)) {
            throw new RuntimeException("activity should implement SearchAnimatedFragment.FragCallback");
        }
        callback = ((FragCallback) getActivity());
    }

    private void updateToCopper() {
        typeView.setText(CableType.COPPER.name());
        fullIdSubject.changeType(CableType.COPPER);
    }

    private void updateToFiber() {
        typeView.setText(CableType.FIBER.name());
        fullIdSubject.changeType(CableType.FIBER);
    }

    private void onClearNumbersClicked(View view) {
        mCableIdView.setText("");
        mCabinIdView.setText("");
        commaDoneView.setImageResource(R.drawable.btn_comma);
        commaDoneView.setClickable(false);
        commaDoneView.setEnabled(false);
        fullIdSubject = new FullIdSubject(AppUtil.getInitialCabins(),
                this, mCableIdView, mCabinIdView, getCurrentType());
    }

    @Override
    public void onCableIdValid() {
        Toast.makeText(getContext(), "cable id valid", Toast.LENGTH_SHORT).show();
        commaDoneView.setEnabled(true);
        commaDoneView.setClickable(true);
    }


    @Override
    public void onFullIdValid() {
        mFullIdParentView.setBackground(getResources().getDrawable(R.drawable.num_valid));
        commaDoneView.setEnabled(true); // make the done btn clickable
        commaDoneView.setClickable(true); // make the done btn clickable
        callback.onIdValid(fullIdSubject.currCableId + "," + fullIdSubject.currCabinId);
    }

    @Override
    public void onCableIdInvalid() {
        Toast.makeText(getContext(), "cable id invalid", Toast.LENGTH_SHORT).show();
        commaDoneView.setEnabled(false); // make comma btn not clickable
        commaDoneView.setClickable(false); // make comma btn not clickable
    }

    @Override
    public void onFullIdInvalid() {
        mFullIdParentView.setBackground(getResources().getDrawable(R.drawable.num_invalid));
        commaDoneView.setEnabled(false); // make done btn not clickable
        commaDoneView.setClickable(false); // make done btn not clickable
    }

    private void onNumberClicked(String s) {
        if (mCableIdView.getText().toString().trim().equals(getResources().getString(R.string.s_cableId)))
            mCableIdView.setText("");
        fullIdSubject.appendNum(s);
    }


    private void onChangeTypeClick(View view) {
        if (changeTypeView.isShadowEnabled()) { // shadow enabled that mean the button should be now fiber
            updateToFiber();
            fullIdSubject.changeType(CableType.FIBER);
            changeTypeView.setShadowEnabled(false);
        } else {
            updateToCopper();
            fullIdSubject.changeType(CableType.COPPER);
            changeTypeView.setShadowEnabled(true);
            changeTypeView.setShadowHeight(shadowHeight);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeTypeView:
                onChangeTypeClick(view);
                break;
            case R.id.dfragPickId_clearNumbers:
                onClearNumbersClicked(view);
                break;
            default:
                // number clicked
                onNumberClicked(((TextView) view).getText().toString());
        }
    }

    private void initCommaDoneViewBtn() {
        commaDoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isEnabled()) {
                    if (fullIdSubject.isFullIdValid())
                        closeFilter(fullIdSubject.currCableId + "," + fullIdSubject.currCabinId);
                    else if (fullIdSubject.insertComma()) {
                        commaDoneView.setImageResource(R.drawable.ic_mydone);
                        commaDoneView.setEnabled(false);
                        commaDoneView.setClickable(false);
                    } else {
                        Log.e(TAG, "onClick: full id is not valid and can't insert comma or exit");
                    }
                } else if (!view.isClickable())   // the bnt is not enabled
                {
                    if (isCableIdValid)
                        Toast.makeText(getContext(), "cabin Id not valid", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getContext(), "cable id not valid", Toast.LENGTH_LONG).show();
                }
            }
        });
        commaDoneView.setEnabled(false);
    }

    private void initCloseDialogBtn() {
        closeDialogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFilter("closed");
            }
        });
    }

    private CableType getCurrentType() {
        return changeTypeView.isShadowEnabled() ? CableType.COPPER : CableType.FIBER;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.mainActivity_map).setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().findViewById(R.id.mainActivity_map).setVisibility(View.VISIBLE);
    }

    static final class FullIdSubject {
        boolean isCommaInserted = false;
        IdStateChangeListener idStateChangeListener;
        String currCableId = "";
        String currCabinId = "";
        ArrayList<Cabin> validIds;
        TextView cableView = null;
        TextView cabinView = null;
        CableType type;

        FullIdSubject(ArrayList<Cabin> validIds, IdStateChangeListener idStateChangeListener
                , TextView cableView, TextView cabinView, CableType type) {
            this.validIds = validIds;
            this.idStateChangeListener = idStateChangeListener;
            this.cableView = cableView;
            this.cabinView = cabinView;
            this.type = type;
        }


        void appendNum(String s) {
            boolean lastCabinState = isCabinIdValid();
            boolean lastCableState = isCableIdValid();
            if (isCommaInserted) {
                cabinView.append(s);
                currCabinId += s;
            } else {
                cableView.append(s);
                currCableId += s;
            }
            boolean currCabinState = isCabinIdValid();
            boolean currCableState = isCableIdValid();
            updateListeners(lastCabinState, lastCableState, currCabinState, currCableState);
        }

        boolean insertComma() {
            if (!isCableIdValid())
                return false;
            if (isCommaInserted)
                return false;
            // at comma inserted
            isCommaInserted = true; //
            cableView.append(", ");
            return true;
        }

        public boolean isCableIdValid() {
            for (Cabin cabin : validIds) {
                if (cabin.getCableId().equals(currCableId.trim()))
                    return true;
            }
            return false;
        }

        public boolean isFullIdValid() {
            return isCableIdValid() && isCabinIdValid();
        }

        public boolean isCabinIdValid() {
            for (Cabin cabin : validIds) {
                if (cabin.getCabinId().equals(currCabinId.trim()))
                    return true;
            }
            return false;
        }

        public void changeType(CableType type) {
            boolean lastCabinState = isCabinIdValid();
            boolean lastCableState = isCableIdValid();
            this.type = type;
            boolean currCabinState = isCabinIdValid();
            boolean currCableState = isCableIdValid();
            updateListeners(lastCabinState, lastCableState, currCabinState, currCableState);
        }

        private void updateListeners(boolean lastCabinState, boolean lastCableState,
                                     boolean currCabinState, boolean currCableState) {
            if (lastCableState && !lastCabinState && currCabinState)
                idStateChangeListener.onFullIdValid();
            else if (lastCableState && lastCabinState && currCableState && !currCabinState)
                idStateChangeListener.onFullIdInvalid();
            else if (lastCableState && !currCableState)
                idStateChangeListener.onCableIdInvalid();
            else if (!lastCableState && currCableState)
                idStateChangeListener.onCableIdValid();
        }

        void reset() {
            currCabinId = "";
            currCableId = "";
            isCommaInserted = false;
        }
    }


    public interface FragCallback
    {
        void onIdValid(String fullId);
    }
}
