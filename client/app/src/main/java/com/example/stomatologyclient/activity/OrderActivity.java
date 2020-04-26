package com.example.stomatologyclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.adapters.OnListInteractListener;
import com.example.stomatologyclient.adapters.UniversalListAdapter;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.auth.StomatologyAccountManager;
import com.example.stomatologyclient.dialogs.AddToothWorkDialog;
import com.example.stomatologyclient.dialogs.ProcedureSelectDialog;
import com.example.stomatologyclient.models.NamedModel;

import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import trikita.knork.Knork;

public class OrderActivity extends AbstractNavigationActivity implements
        OnListInteractListener, AddToothWorkDialog.OnToothWorkSelectedListener
{

    @Knork.Id(R.id.button_close) Button close_order;
    @Knork.Id(R.id.button_save) Button save_order;
    @Knork.Id(R.id.button_finish) Button finish_order;
    @Knork.Id(R.id.order_date) TextView order_date;
    @Knork.Id(R.id.order_finish_date) TextView order_finish_date;
    @Knork.Id(R.id.order_doctor) TextView order_doctor;
    @Knork.Id(R.id.order_technic) TextView order_technic;
    @Knork.Id(R.id.order_cost) TextView order_cost;
    @Knork.Id(R.id.order_finished) TextView order_finished;
    @Knork.Id(R.id.order_annotations) EditText order_annotations;
    @Knork.Id(R.id.list) RecyclerView teeth_list;
    @Knork.Id(R.id.fab) FloatingActionButton fab;
    @Knork.Id(R.id.list) RecyclerView recyclerView;


    Retrofit retrofit;
    API api;
    UniversalListAdapter adapter;

    Models.Order order;
    List<Models.Tooth> teeth;

    boolean is_doctor;
    boolean is_technican;
    boolean is_finished;

    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Knork.inject(getWindow().getDecorView(), this);
        final Context context = this;
        final AddToothWorkDialog.OnToothWorkSelectedListener listener = this;

        //Добавление нового ToothWork
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Добавление ToothWork
                AddToothWorkDialog dlg = new AddToothWorkDialog(listener);
                dlg.show(getSupportFragmentManager(), "add_toothwork");
            }
        });

        retrofit = RetrofitFactory.GetRetrofit(StomatologyAccountManager.getAuthToken(this));
        api = retrofit.create(API.class);

        //Проверка ролей
        int role = StomatologyAccountManager.getRole(this);
        is_doctor = role == StomatologyAccountManager.ROLE_DOCTOR;
        is_technican = role == StomatologyAccountManager.ROLE_TECHNICAN;

        if(is_doctor) {
            fab.setVisibility(View.VISIBLE);
            save_order.setVisibility(View.VISIBLE);
            close_order.setVisibility(View.VISIBLE);
        }
        else {
            fab.setVisibility(View.GONE);
            save_order.setVisibility(View.GONE);
            close_order.setVisibility(View.GONE);
        }

        if(is_technican)
            finish_order.setVisibility(View.VISIBLE);
        else
            finish_order.setVisibility(View.GONE);

        load_order();
    }

    //Показ заказа
    private void load_order()
    {
        final Context context = this;
        final OnListInteractListener listener = this;

        final Call<Models.Order> order_ = api.getOrder(id);
        order_.enqueue(new Callback<Models.Order>() {
            @Override
            public void onResponse(Call<Models.Order> call, Response<Models.Order> response) {
                if(response.code()==200)
                {
                    order = response.body();

                    if(order.Date!=null)
                        order_date.setText(format.format(order.Date));

                    if(order.FinishDate!=null)
                        order_finish_date.setText(format.format(order.FinishDate));

                    if(order.Doctor!=null)
                        order_doctor.setText(order.Doctor.Name());

                    if(order.DentalTechnican!=null)
                        order_technic.setText(order.DentalTechnican.Name());

                    order_cost.setText(String.valueOf(order.Cost));
                    order_annotations.setText(order.Annotation);

                    teeth = order.Teeth;
                    adapter=new UniversalListAdapter(context, teeth, false, true,false, is_doctor && !order.IsClosed);
                    adapter.setOnListInteractListenr(listener);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));

                    if(order.IsClosed)
                        set_closed();

                    if(order.IsFinished)
                        order_finished.setVisibility(View.VISIBLE);

                }
                else
                    Toast.makeText(context,"Не удалось загрузить посещение",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Models.Order> call, Throwable t) {
                Toast.makeText(context,"Не удалось загрузить посещение",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Деактивирует элементы
    private void set_closed()
    {
        order_annotations.setEnabled(false);
        fab.setEnabled(false);
        save_order.setEnabled(false);
        close_order.setText("Заказ подтвержден");
        close_order.setEnabled(false);
    }



    @Override
    public void OnItemClick(int id) {

    }

    //Удаление ToothWork
    @Override
    public void OnRemoveClick(final int id) {
        final Context context = this;

        final Call<ResponseBody> resp = api.removeTooth(id);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200)
                {
                    adapter.remove(id);
                }
                else
                    Toast.makeText(context,"Не удалось удалить",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"Не удалось удалить",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnEditClick(int id) {

    }

    //Сохранение заказа
    @Knork.On(Knork.CLICK+R.id.button_save)
    public  void save_clicked(View v)
    {
        order.Annotation = order_annotations.getText().toString();
        final Context context = this;

        Call<ResponseBody> order_ = api.postOrder(order);
        order_.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200)
                    Toast.makeText(context,"Сохранено",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context,"Не удалось сохранить заказ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"Не удалось сохранить заказ",Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Закрытие заказа
    //(нельзя больше редактировать)
    @Knork.On(Knork.CLICK+R.id.button_close)
    public  void close_clicked(View v)
    {
        final Context context = this;
        final Call<ResponseBody> resp = api.closeOrder(order.Id);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200)
                    set_closed();
                else
                    Toast.makeText(context,"Не удалось подтвердить заказ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"Не удалось подтвердить заказ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Knork.On(Knork.CLICK+R.id.button_finish)
    public  void finish_clicked(View v)
    {
        final Context context = this;
        final Call<ResponseBody> resp = api.finishOrder(order.Id);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200)
                    order_finished.setVisibility(View.VISIBLE);
                else
                    Toast.makeText(context,"Не удалось закрыть посещение",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"Не удалось закрыть посещение",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Выбрано изделие для зуба
    @Override
    public void OnToothWorkSelected(final Models.Tooth tooth)
    {
        final Context context = this;
        tooth.OrderId = id;
        Call<ResponseBody>  resp = api.addToith(id,tooth.ToothNo, tooth.ProcedureId);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200)
                {
                    //Зуб добавлен. теперь по айдишнику надо загрузить и добавить
                    teeth.add(tooth);
                    adapter.notifyItemInserted(teeth.size()-1);

                }
                else
                    Toast.makeText(context,"Не удалось добавить",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                Toast.makeText(context,"Не удалось добавить",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
