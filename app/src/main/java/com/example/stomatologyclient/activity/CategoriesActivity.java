package com.example.stomatologyclient.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.adapters.NamedListAdapter;
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
 * Просмотр всх категорий
 */
public class CategoriesActivity extends AbstractNavigationActivity implements NamedListAdapter.OnListInteractListener
{

    private NamedListAdapter adapter;
    private RecyclerView recyclerView;
    private  Retrofit retrofit;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setup_actionbar();

        recyclerView = (RecyclerView)findViewById(R.id.categories_list);

        //Настройка запроса
        retrofit = RetrofitFactory.GetRetrofit();
        api = retrofit.create(API.class);
        Call<List<Models.Category>> categories = api.getCategories();
        final Context context = this;

        //Запрос
        categories.enqueue(new Callback<List<Models.Category>>() {
            @Override
            public void onResponse(Call<List<Models.Category>> call, Response<List<Models.Category>> response) {

                //Заполняем лист
                List<? extends NamedModel> items = response.body();
                adapter = new NamedListAdapter(context, items, true, false, false);
                adapter.setOnListInteractListenr((NamedListAdapter.OnListInteractListener) context);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<List<Models.Category>> call, Throwable t) {
                Toast.makeText(context,"Не удалось загрузить категории",Toast.LENGTH_SHORT).show();
            }
        });


    }

    //////////////////////// ОБРАБОТЧИКИИ СОБЫТИЙ //////////////////////////////////////////////////

    @Override
    public void OnItemClick(NamedModel model) {
        start_activity_and_send_id(CategoryActivity.class, model.Id);
    }

    @Override
    public void OnRemoveClick(NamedModel model) {
        //Удаление


    }
}
