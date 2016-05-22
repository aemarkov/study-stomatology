package com.example.stomatologyclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.auth.StomatologyAccountManager;


import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import trikita.knork.Knork;

public class PatientActivity extends AbstractNavigationActivity {

    @Knork.Id(R.id.patient_surname_view)
    TextView patient_surname;
    @Knork.Id(R.id.patient_name_view)
    TextView patient_name;
    @Knork.Id(R.id.patient_middlename_view)
    TextView patient_middlename;
    @Knork.Id(R.id.patient_birth)
    TextView patient_birth;
    @Knork.Id(R.id.patient_medcard)
    TextView patient_medcard;


    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Knork.inject(getWindow().getDecorView(), this);

        Retrofit retrofit = RetrofitFactory.GetRetrofit(StomatologyAccountManager.getAuthToken(this));
        API api = retrofit.create(API.class);

        final Call<Models.Patient> patient = api.getPatient(id);
        final Context context = this;

        patient.enqueue(new Callback<Models.Patient>() {
            @Override
            public void onResponse(Call<Models.Patient> call, Response<Models.Patient> response) {
                if (response.code() == 200) {
                    Models.Patient patient_ = response.body();
                    patient_surname.setText(patient_.PersonInfo.Surname);
                    patient_name.setText(patient_.PersonInfo.Name);
                    patient_middlename.setText(patient_.PersonInfo.Middlename);
                    patient_medcard.setText(String.valueOf(patient_.MedicalCardNumber));
                    //TODO: дата рождения
                    patient_birth.setText(format.format(patient_.DateOfBirth));

                    setTitle(patient_.Name());

                } else if (response.code() == 401) {
                    Toast.makeText(context, "Отказано в доступе", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Models.Patient> call, Throwable t) {
                Toast.makeText(context, "Не удалось загрузить информацию о пациенте", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Knork.On({Knork.CLICK + R.id.button_visits})
    public void visits_click(View v) {
        start_activity_and_send_id(VisitsActivity.class, id);
    }

    @Knork.On({Knork.CLICK + R.id.button_orders})
    public  void orders_click(View v)
    {
        start_activity_and_send_id(OrdersActivity.class, id);
    }

}
