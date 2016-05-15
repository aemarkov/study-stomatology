package com.example.stomatologyclient.fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Garrus on 15.05.2016.
 */
public class AbstractNavigationFragment extends  Fragment
{
    protected void start_activity_and_send_id(Class<? extends AppCompatActivity> activity, int id)
    {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtra("id",id);
        startActivity(intent);

    }


}
