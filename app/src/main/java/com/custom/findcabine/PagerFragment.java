package com.custom.findcabine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by AmmarRabie on 20/04/2018.
 */


public class PagerFragment extends Fragment {
    private Cabin cabin;
    private CabinTextRepresentation cabinTextRepresentation;

    public static PagerFragment newInstance(Cabin cabin) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putSerializable("cabin", cabin);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cabin = ((Cabin) getArguments().getSerializable("cabin"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_pager, container, false);
        cabinTextRepresentation = view.findViewById(R.id.pagerFrag_textRepresentation);
        cabinTextRepresentation.setId(cabin.getFullId());
        cabinTextRepresentation.setType(cabin.getType());
        cabinTextRepresentation.setAddress(cabin.getAddress());
        return view;
    }

}
