package com.example.stomatologyclient.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.stomatologyclient.R;

public class OrderActivity extends AbstractNavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(id==-1)
            setTitle("Новый заказ");
        else
            setTitle("Редактирование заказа");

    }

}
