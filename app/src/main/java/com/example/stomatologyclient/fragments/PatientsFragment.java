package com.example.stomatologyclient.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.activity.PatientActivity;
import com.example.stomatologyclient.adapters.UniversalListAdapter;
import com.example.stomatologyclient.adapters.OnListInteractListener;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.auth.StomatologyAccountManager;
import com.example.stomatologyclient.models.NamedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Фрагент списка пациентов
 */
public class PatientsFragment extends AbstractListFragment {


    public PatientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState, StomatologyAccountManager.getAuthToken(getActivity()));
    }

    @Override
    public void onResume()
    {
        super.onResume();

        Call<List<Models.Patient>> patients = api.getPatients();

        //Настройка заголовка
        final Activity context = getActivity();
        context.setTitle(context.getString(R.string.Patients));
        final OnListInteractListener listner = this;

        //Запрос
        patients.enqueue(new Callback<List<Models.Patient>>() {
            @Override
            public void onResponse(Call<List<Models.Patient>> call, Response<List<Models.Patient>> response) {
                //Заполняем лист
                int status = response.code();

                if (status == 200)
                {
                    List<? extends NamedModel> items = response.body();
                    adapter = new UniversalListAdapter(context, items, false, false, false);
                    adapter.setOnListInteractListenr(listner);

                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                }
                else if(status==201)
                {
                    Toast.makeText(context,"Отказано в доступе",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Models.Patient>> call, Throwable t) {
                Toast.makeText(context,"Не удалось загрузить пациентов",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnItemClick(int id)
    {
        start_activity_and_send_id(PatientActivity.class, id);
    }

    @Override
    public void OnRemoveClick(int id) {

    }

    @Override
    public void OnEditClick(int id) {

    }
}
