package com.example.stomatologyclient.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.activity.OrdersActivity;
import com.example.stomatologyclient.activity.VisitsActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends Fragment {



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

    @Knork.Id(R.id.button_orders) Button button_orders;

    int id;

    public PatientFragment() {
        // Required empty public constructor
        id = -1;
    }

    public PatientFragment(int id) {
        // Required empty public constructor
        this.id = id;
    }

    API api;
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_patient, container, false);

        Knork.inject(root, this);

        Retrofit retrofit = RetrofitFactory.GetRetrofit(StomatologyAccountManager.getAuthToken(getActivity()));
        api = retrofit.create(API.class);

        final Call<Models.Patient> patient = get_patient_call(); // api.getPatient(id);
        final Context context = getActivity();

        patient.enqueue(new Callback<Models.Patient>() {
            @Override
            public void onResponse(Call<Models.Patient> call, Response<Models.Patient> response) {
                if (response.code() == 200) {
                    Models.Patient patient_ = response.body();
                    patient_surname.setText(patient_.PersonInfo.Surname);
                    patient_name.setText(patient_.PersonInfo.Name);
                    patient_middlename.setText(patient_.PersonInfo.Middlename);
                    patient_medcard.setText(String.valueOf(patient_.MedicalCardNumber));
                    patient_birth.setText(format.format(patient_.DateOfBirth));

                    //Если загружали текщего - выставляем Id
                    //id = patient_.Id;

                    getActivity().setTitle(patient_.Name());

                } else if (response.code() == 401) {
                    Toast.makeText(context, "Отказано в доступе", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Models.Patient> call, Throwable t) {
                Toast.makeText(context, "Не удалось загрузить информацию о пациенте", Toast.LENGTH_SHORT).show();
            }
        });

        int role = StomatologyAccountManager.getRole(context);
        if(role==StomatologyAccountManager.ROLE_DOCTOR)
            button_orders.setVisibility(View.VISIBLE);
        else
            button_orders.setVisibility(View.GONE);

        return root;
    }

    private Call<Models.Patient> get_patient_call()
    {
        if(id!=-1)
            return api.getPatient(id);
        else
            return api.getCurrentPatient();
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

    void start_activity_and_send_id(Class<? extends AppCompatActivity> activity, int id)
    {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
