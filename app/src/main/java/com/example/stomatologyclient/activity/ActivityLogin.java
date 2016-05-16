package com.example.stomatologyclient.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.auth.StomatologyAccountManager;
import com.example.stomatologyclient.utils.Helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityLogin extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        username = (EditText) findViewById(R.id.username_edit);
        password = (EditText) findViewById(R.id.password_edit);
        login = (Button) findViewById(R.id.login_button);
        register = (Button) findViewById(R.id.register_button);

        final Activity context = this;

        //Вход
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(context,username.getText().toString(), password.getText().toString());
            }
        });

        //Регистрация
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = StomatologyAccountManager.getRegistrationIntetn(context, StomatologyAccountManager.ROLE_PATIENT);
                startActivityForResult(intent, 0);
            }
        });
    }

    //Пользователь зарегался
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Получаем логин, пароль и делаем вход
        if(data==null)return;;

        String username = data.getStringExtra("username");
        String password = data.getStringExtra("password");

        login(this, username, password);
    }

    //Осуществляет вход, сохраняет токеи и закрываеет активити
    private void login(final Context context, String name, String password)
    {
        Retrofit retrofit = RetrofitFactory.GetRetrofit();
        API api = retrofit.create(API.class);

        final Activity activity =this;

        Call<ResponseBody> resp = api.getToken("password", name, password);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();

                try
                {

                    if(code==200)
                    {
                        //OK
                        JSONObject message = new JSONObject(response.body().string());
                        String token  = message.getString("access_token");
                        String role = message.getString("role");
                        StomatologyAccountManager.saveToken(activity, token, role);
                        Helpers.ShowMessage(context, "Успешный вход", "", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                    }
                    else
                    {
                        //Ошибка
                        JSONObject error = new JSONObject(response.errorBody().string());
                        Helpers.ShowMessage(context, "Не удалось войти", error.getString("error_description"));
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
