package com.example.stomatologyclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.adapters.OnListInteractListener;
import com.example.stomatologyclient.adapters.UniversalListAdapter;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.auth.StomatologyAccountManager;
import com.example.stomatologyclient.models.NamedModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Активити, отображающая список посещений пациента
 */
public class VisitsActivity extends AbstractNavigationActivity implements OnListInteractListener
{

    RecyclerView recyclerView;

    private UniversalListAdapter adapter;
    private Retrofit retrofit;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.list);
        final Context context = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Создание нового посещения
                Models.Visit visit = new Models.Visit();
                visit.PatientId = id;
                Call<ResponseBody> resp = api.putVisit(visit);

                resp.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200)
                            update_visits();
                        else
                            Toast.makeText(context,"Не удалось добавить посещение",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context,"Не удалось добавть посещени",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Настройка запроса
        retrofit = RetrofitFactory.GetRetrofit(StomatologyAccountManager.getAuthToken(this));
        api = retrofit.create(API.class);

        update_visits();
    }

    private void update_visits()
    {
        final Context context = this;
        final OnListInteractListener listner = this;

        //Запрос
        Call<Models.Patient> visits = api.getPatient(id);
        visits.enqueue(new Callback<Models.Patient>() {
            @Override
            public void onResponse(Call<Models.Patient> call, Response<Models.Patient> response) {
                List<? extends NamedModel> items = response.body().Visits;
                adapter = new UniversalListAdapter(context, items, false, false, false, true);
                adapter.setOnListInteractListenr( listner);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<Models.Patient> call, Throwable t) {
                Toast.makeText(context,"Не удалось загрузить посещения",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnItemClick(int id) {
        //Выбор визита
        start_activity_and_send_id(VisitActivity.class, id);
    }

    @Override
    public void OnRemoveClick(final int id)
    {
        //Удаление визита
        final Call<ResponseBody> resp = api.deleteVisit(id);
        final Context context = this;
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200)
                    adapter.remove(id);
                else if(response.code()==404)
                    Toast.makeText(context,"Не удалось удалить посещение: не найдено",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"Не удалось удалить посещение",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnEditClick(int id) {

    }
}
