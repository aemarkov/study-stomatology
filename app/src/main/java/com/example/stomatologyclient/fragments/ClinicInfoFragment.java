package com.example.stomatologyclient.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.auth.StomatologyAccountManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import trikita.knork.Knork;

/**
 * Фрагмент с информацией о клинике
 */
public class ClinicInfoFragment extends Fragment {

    @Knork.Id(R.id.clinic_name) TextView clinic_name;
    @Knork.Id(R.id.clinic_adress) TextView clinic_adress;
    @Knork.Id(R.id.clinic_phone) TextView clinic_phone;
    @Knork.Id(R.id.clinic_email) TextView clinic_email;

    public ClinicInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_clinic_info, container, false);

        Knork.inject(root, this);

        Retrofit retrofit = RetrofitFactory.GetRetrofit(StomatologyAccountManager.getAuthToken(getActivity()));
        API api = retrofit.create(API.class);

        final Context context = getActivity();

        Call<Models.ClinicInfo> info_ = api.getClinicInfo();
        info_.enqueue(new Callback<Models.ClinicInfo>() {
            @Override
            public void onResponse(Call<Models.ClinicInfo> call, Response<Models.ClinicInfo> response) {
                if(response.code()==200)
                {
                    Models.ClinicInfo info = response.body();
                    clinic_name.setText(info.Name);
                    clinic_phone.setText(info.PhoneNumber);
                    clinic_email.setText(info.Email);
                    clinic_adress.setText(info.Adress);
                }
                else
                    Toast.makeText(context,"Не удалось загрузить посещение",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Models.ClinicInfo> call, Throwable t) {
                Toast.makeText(context,"Не удалось загрузить посещение",Toast.LENGTH_SHORT).show();
            }
        });

        return  root;
    }

}
