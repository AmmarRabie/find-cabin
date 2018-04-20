package com.custom.findcabine;

import android.app.Dialog;
import android.util.TypedValue;
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
    private String mCurrCabinNumber;
    private static int shadowHeight;
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

    public static SearchAnimatedFragment newInstance() {
        return new SearchAnimatedFragment();
    }

    public SearchAnimatedFragment() {
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
        initCommaDoneViewBtn();
        initCloseDialogBtn();

        shadowHeight = changeTypeView.getShadowHeight();

        fullIdSubject = new FullIdSubject(AppUtil.getInitialCabins()
                , this, mCableIdView, mCabinIdView,
                getCurrentType());


        //params to set
        setAnimationDuration(450); //optional; default 500ms
        setPeekHeight(350); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setViewgroupStatic(contentView.findViewById(R.id.staticDialogView));
        setViewMain(contentView.findViewById(R.id.mainDialogView));
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }

    private void insertComma() {
        if (commaInserted)
            return;
        if (isCableIdJustValid) {

            commaInserted = true;
        }

    }


    private String getParsedId() {
        String[] ids = mCableIdView.getText().toString().split(", ");
        return ids[0] + "," + ids[1];

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
        fullIdSubject = new FullIdSubject(AppUtil.getInitialCabins(),
                this,mCableIdView, mCabinIdView, getCurrentType());
    }

    @Override
    public void onCableIdValid() {
        commaDoneView.setEnabled(true);
        commaDoneView.setImageResource(R.drawable.ic_done);
    }


    @Override
    public void onFullIdValid() {
        mFullIdParentView.setBackground(getResources().getDrawable(R.drawable.num_valid));
        commaDoneView.setEnabled(true);
    }

    @Override
    public void onCableIdInvalid() {
        Toast.makeText(getContext(), "cable id valid", Toast.LENGTH_SHORT).show();
        commaDoneView.setImageResource(R.drawable.btn_comma);
    }

    @Override
    public void onFullIdInvalid() {
        mFullIdParentView.setBackground(getResources().getDrawable(R.drawable.num_invalid));
        commaDoneView.setEnabled(false);
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
            case R.id.clearNumbersView:
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
                        closeFilter(fullIdSubject.currCableId+ "," + fullIdSubject.currCabinId);
                    else if (fullIdSubject.insertComma()) {
                        commaDoneView.setImageResource(R.drawable.ic_done);
                        commaDoneView.setEnabled(false);
                    }
                } else    // the bnt is not enabled
                {
                    if (isCableIdValid)
                        Toast.makeText(getContext(), "cabin Id not valid", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getContext(), "cable id not valid", Toast.LENGTH_LONG).show();
                }
            }
        });
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
            if (!isCommaInserted) // append to the cable id
            {
                boolean lastStateValid = isCableIdValid();
                currCableId += s;
                if (isCableIdValid())
                    idStateChangeListener.onCableIdInvalid();
                if (lastStateValid && !isCableIdValid()) // if last state valid and now invalid
                    idStateChangeListener.onFullIdInvalid();
            } else                  // append the the cabin id
            {
                boolean lastStateValid = isFullIdValid();
                currCabinId += s;
                if (isCabinIdValid())
                    idStateChangeListener.onFullIdValid();
                if (lastStateValid && !isFullIdValid()) // if last state valid and now invalid
                    idStateChangeListener.onFullIdInvalid();
            }
            cableView.append(s); // append number at the end
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

}
