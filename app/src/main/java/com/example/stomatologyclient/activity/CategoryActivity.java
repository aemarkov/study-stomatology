package com.example.stomatologyclient.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.adapters.NamedListAdapter;
import com.example.stomatologyclient.adapters.OnListInteractListener;
import com.example.stomatologyclient.adapters.SubcategoriesAdapter;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.auth.StomatologyAccountManager;
import com.example.stomatologyclient.models.NamedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Просмотр категории
 */
public class CategoryActivity extends AbstractNavigationActivity {

    private SubcategoriesAdapter adapter;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setup_actionbar();

        recyclerView = (RecyclerView)findViewById(R.id.procedures_list);

        //Настройка запроса
        retrofit = RetrofitFactory.GetRetrofit();
        api = retrofit.create(API.class);
        final Call<Models.Category> categories = api.getCategory(id);
        final Activity context = this;

        // Admin?
        final boolean is_admin = StomatologyAccountManager.GetRole(this)==StomatologyAccountManager.ROLE_ADMIN;

        //Запрос
        categories.enqueue(new Callback<Models.Category>() {
            @Override
            public void onResponse(Call<Models.Category> call, Response<Models.Category> response) {

                //Заполняем лист
                Models.Category category = response.body();
                context.setTitle(category.Name());
                adapter = new SubcategoriesAdapter(context, category.Subcategories, is_admin);

                //Обработчик нажатия на подкатегории
                adapter.setOnHeaerEditListener(new OnListInteractListener() {
                    @Override
                    public void OnItemClick(int id) {

                    }

                    @Override
                    public void OnRemoveClick(int id) {
                        Log.d("WTF","Subcategory remove click");
                    }

                    @Override
                    public void OnEditClick(int id) {
                        Log.d("WTF","Subcategory edit click");
                    }
                });

                //Обоаботчик нажатия на процедуры
                adapter.setOnItemsInterractListener(new OnListInteractListener() {
                    @Override
                    public void OnItemClick(int id) {
                        Log.d("WTF","Procedure click");
                        start_activity_and_send_id(ProcedureActivity.class, id);
                    }

                    @Override
                    public void OnRemoveClick(int id) {
                        Log.d("WTF","Procedure remove click");
                    }

                    @Override
                    public void OnEditClick(int id) {
                        Log.d("WTF","Procedure edit click");
                    }
                });

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<Models.Category> call, Throwable t) {
                Toast.makeText(context,"Не удалось загрузить процедуры",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
