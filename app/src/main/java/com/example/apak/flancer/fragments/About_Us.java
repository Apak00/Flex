package com.example.apak.flancer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apak.flancer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class About_Us extends Fragment {


    public About_Us() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("About Us");
        return inflater.inflate(R.layout.fragment_about__us, container, false);
    }

}
