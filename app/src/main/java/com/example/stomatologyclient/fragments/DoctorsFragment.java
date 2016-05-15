package com.example.stomatologyclient.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.activity.DoctorActivity;
import com.example.stomatologyclient.adapters.NamedListAdapter;
import com.example.stomatologyclient.adapters.OnListInteractListener;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.models.NamedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Показ врачей
 */
public class DoctorsFragment extends AbstractNavigationFragment implements OnListInteractListener
{

    private NamedListAdapter adapter;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private API api;

    public DoctorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView)root.findViewById(R.id.categories_list);

        //Настройка запроса
        retrofit = RetrofitFactory.GetRetrofit();
        api = retrofit.create(API.class);
        Call<List<Models.Doctor>> categories = api.getDoctors();

        //Настройка заголовка
        final Activity context = getActivity();
        context.setTitle(context.getString(R.string.Categories));

        final OnListInteractListener listner = this;

        //Запрос
        categories.enqueue(new Callback<List<Models.Doctor>>() {
            @Override
            public void onResponse(Call<List<Models.Doctor>> call, Response<List<Models.Doctor>> response) {
                //Заполняем лист
                List<? extends NamedModel> items = response.body();
                adapter = new NamedListAdapter(context, items, true, false, false);
                adapter.setOnListInteractListenr( listner);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<List<Models.Doctor>> call, Throwable t) {
                Toast.makeText(context,"Не удалось загрузить врачей",Toast.LENGTH_SHORT).show();
            }
        });

        return  root;
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
