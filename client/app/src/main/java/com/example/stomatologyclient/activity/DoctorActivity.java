package com.example.stomatologyclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.stomatologyclient.R;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DoctorActivity extends AbstractNavigationActivity {

    private Retrofit retrofit;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_with_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageView image = (ImageView)findViewById(R.id.procedure_image);
        final TextView name = (TextView)findViewById(R.id.procedure_name);
        final TextView description = (TextView)findViewById(R.id.procedure_description);
        final TextView cost = (TextView)findViewById(R.id.procedure_cost);

        //Убираем цену
        cost.setVisibility(View.GONE);

        //Настройка запроса
        retrofit = RetrofitFactory.GetRetrofit();
        api = retrofit.create(API.class);
        final Call<Models.Doctor> doctor = api.getDoctor(id);

        final Context context = this;

        //ЗАпрос
        doctor.enqueue(new Callback<Models.Doctor>() {
            @Override
            public void onResponse(Call<Models.Doctor> call, Response<Models.Doctor> response) {
                Models.Doctor _doctor = response.body();
                setTitle(_doctor.Name());

                name.setText(_doctor.Name());

                if(_doctor.Text!=null)
                    description.setText(_doctor.Text);
                else
                    description.setVisibility(View.GONE);

                Glide.with(context)
                        .load(_doctor.Image)
                        .centerCrop()
                        .placeholder(context.getResources().getDrawable(R.drawable.ic_camera))
                        .into(image);
            }

            @Override
            public void onFailure(Call<Models.Doctor> call, Throwable t) {

            }
        });
    }

}
