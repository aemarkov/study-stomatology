package com.example.stomatologyclient.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.activity.OrderActivity;
import com.example.stomatologyclient.adapters.OnListInteractListener;
import com.example.stomatologyclient.adapters.UniversalListAdapter;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.auth.StomatologyAccountManager;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import trikita.knork.Knork;

/**
 * Список заказов
 */
public class OrdersFragment extends Fragment implements  OnListInteractListener
{


    @Knork.Id(R.id.list)
    RecyclerView recyclerView;

    int id;
    Retrofit retrofit;
    API api;
    UniversalListAdapter adapter;

    List<Models.Order> orders;


    public OrdersFragment()
    {
        id = -1;
    }

    public OrdersFragment(int id)
    {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_orders, container, false);


        Knork.inject(root, this);
        final Context context = getActivity();

        //Создание нового заказа
        final FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Models.Order order = new Models.Order();
                order.PatientId = id;

                final Call<Models.PutResponse> resp = api.putOrder(order);
                resp.enqueue(new Callback<Models.PutResponse>() {
                    @Override
                    public void onResponse(Call<Models.PutResponse> call, Response<Models.PutResponse> response) {
                        if(response.code()==200)
                        {
                            int id = response.body().Id;

                            //Загрузка вновь созданного заказ
                            Call<Models.Order> order_ = api.getOrder(id);
                            order_.enqueue(new Callback<Models.Order>() {
                                @Override
                                public void onResponse(Call<Models.Order> call, Response<Models.Order> response) {
                                    if(response.code()==200)
                                    {
                                        orders.add(response.body());
                                        adapter.notifyItemInserted(orders.size());
                                    }
                                    else
                                        Toast.makeText(context, "Не удалось загрузить заказ", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<Models.Order> call, Throwable t) {
                                    Toast.makeText(context, "Не удалось загрузить заказ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                            Toast.makeText(context, "Не удалось создать заказ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Models.PutResponse> call, Throwable t) {
                        Toast.makeText(context, "Не удалось создать заказ", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        //Просмотр списка заказов пациента
        retrofit = RetrofitFactory.GetRetrofit(StomatologyAccountManager.getAuthToken(context));
        api = retrofit.create(API.class);


        //Определение роли:
        int role = StomatologyAccountManager.getRole(getActivity());
        boolean is_doctor = role == StomatologyAccountManager.ROLE_DOCTOR;
        final boolean is_admin = role == StomatologyAccountManager.ROLE_ADMIN;

        //Вкл\выкл кнопку добавления заказа
        if (is_doctor && id!=-1)
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.GONE);

        //Загрузка заказов пациента
        if(id!=-1)
            load_patient_orders(id, is_admin);
        else
            load_all_orders(is_admin);

        return root;
    }


    //Загрузка заказов пациента
    private void load_patient_orders(final int id, final boolean is_admin) {
        final Context context = getActivity();
        Call<Models.Patient> patient = api.getPatient(id);

        patient.enqueue(new Callback<Models.Patient>() {
            @Override
            public void onResponse(Call<Models.Patient> call, Response<Models.Patient> response) {
                if (response.code() == 200) {
                    orders = response.body().Orders;
                    setup_adapter(context, is_admin, orders);
                }
                else
                    Toast.makeText(context, "Не удалось загрузить список заказов", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Models.Patient> call, Throwable t) {
                Toast.makeText(context, "Не удалось загрузить список заказов", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Загружает список всех заказов
    private void load_all_orders(final boolean is_admin)
    {
        final Context context = getActivity();
        Call<List<Models.Order>> orders_ = api.getOrders();

        orders_.enqueue(new Callback<List<Models.Order>>() {
            @Override
            public void onResponse(Call<List<Models.Order>> call, Response<List<Models.Order>> response) {
                if(response.code()==200)
                {
                    List<Models.Order> orders = response.body();
                    setup_adapter(context, is_admin, orders);
                }
                else
                    Toast.makeText(context, "Не удалось загрузить список заказов", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Models.Order>> call, Throwable t) {
                Toast.makeText(context, "Не удалось загрузить список заказов", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Настраивает адаптер
    private  void setup_adapter(Context context, boolean is_admin, List<Models.Order> orders)
    {
        adapter = new UniversalListAdapter(context, orders, false, false, false, is_admin);
        adapter.setOnListInteractListenr(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    //Показ заказа
    @Override
    public void OnItemClick(int id)
    {
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //Удаление заказа
    @Override
    public void OnRemoveClick(final int id) {

        final Context context = getActivity();
        final Call<ResponseBody> resp = api.deleteOrder(id);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200)
                    adapter.remove(id);
                else
                    Toast.makeText(context, "Не удалось удалить заказ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Не удалось удалить заказ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnEditClick(int id) {

    }


}
