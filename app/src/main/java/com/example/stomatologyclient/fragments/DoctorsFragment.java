package com.example.stomatologyclient.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.activity.DoctorActivity;
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
 * Показ врачей
 */
public class DoctorsFragment extends AbstractListFragment
{


    public DoctorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //Загрузка
    @Override
    public void onResume()
    {
        super.onResume();

        Call<List<Models.Doctor>> categories = api.getDoctors();

        //Настройка заголовка
        final Activity context = getActivity();
        context.setTitle(context.getString(R.string.Doctors));
        final OnListInteractListener listner = this;

        // Admin?
        final boolean is_admin = StomatologyAccountManager.getRole(getActivity())==StomatologyAccountManager.ROLE_ADMIN;

        //Запрос
        categories.enqueue(new Callback<List<Models.Doctor>>() {
            @Override
            public void onResponse(Call<List<Models.Doctor>> call, Response<List<Models.Doctor>> response) {
                //Заполняем лист
                List<? extends NamedModel> items = response.body();
                adapter = new UniversalListAdapter(context, items, true, false, is_admin);
                adapter.setOnListInteractListenr( listner);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<List<Models.Doctor>> call, Throwable t) {
                Toast.makeText(context,"Не удалось загрузить врачей",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void OnItemClick(int id) {
        Log.d("WTF","Doctor click");
        start_activity_and_send_id(DoctorActivity.class, id);
    }

    @Override
    public void OnRemoveClick(int id) {
        Log.d("WTF","Doctor remove click");
    }

    @Override
    public void OnEditClick(int id) {
        Log.d("WTF","Doctor edit click");
    }
}
