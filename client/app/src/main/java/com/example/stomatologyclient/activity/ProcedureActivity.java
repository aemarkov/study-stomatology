package com.example.stomatologyclient.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.stomatologyclient.R;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Активити процедуры
 */
public class ProcedureActivity extends AbstractNavigationActivity {

    private Retrofit retrofit;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_with_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setup_actionbar();

        final ImageView image = (ImageView)findViewById(R.id.procedure_image);
        final TextView name = (TextView)findViewById(R.id.procedure_name);
        final TextView description = (TextView)findViewById(R.id.procedure_description);
        final TextView cost = (TextView)findViewById(R.id.procedure_cost);

        //Настройка запроса
        retrofit = RetrofitFactory.GetRetrofit();
        api = retrofit.create(API.class);
        final Call<Models.Procedure> procedure = api.getProcedure(id);

        final Context context = this;

        procedure.enqueue(new Callback<Models.Procedure>() {
            @Override
            public void onResponse(Call<Models.Procedure> call, Response<Models.Procedure> response) {
                Models.Procedure _procedure = response.body();
                setTitle(_procedure.Name);

                name.setText(_procedure.Name);

                if(_procedure.Description!=null)
                    description.setText(_procedure.Description);
                else
                    description.setVisibility(View.GONE);


                cost.setText(String.valueOf(_procedure.Cost));


                Glide.with(context)
                        .load(_procedure.Image)
                        .centerCrop()
                        .placeholder(context.getResources().getDrawable(R.drawable.ic_camera))
                        .into(image);
            }

            @Override
            public void onFailure(Call<Models.Procedure> call, Throwable t) {
                Toast.makeText(context,"Не удалось загрузить процедуру",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
