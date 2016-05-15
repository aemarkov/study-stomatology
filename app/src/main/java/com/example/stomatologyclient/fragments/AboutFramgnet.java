package com.example.stomatologyclient.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stomatologyclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFramgnet extends Fragment {


    public AboutFramgnet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Activity activity = getActivity();
        activity.setTitle(activity.getString(R.string.About));

        return inflater.inflate(R.layout.fragment_about_framgnet, container, false);
    }

}
