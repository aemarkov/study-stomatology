package com.example.stomatologyclient.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.auth.StomatologyAccountManager;
import com.example.stomatologyclient.utils.Helpers;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    public  final static String EXTRA_TOKEN_TYPE = "EXTRA_TOKEN_TYPE";

    EditText email;
    EditText pass;
    EditText pass_confirm;
    EditText surname;
    EditText name;
    EditText middlename;

    EditText age;
    EditText polis;
    Spinner sex;

    int token_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        token_type = getIntent().getExtras().getInt(EXTRA_TOKEN_TYPE,-1);

        //Находим компоненты
        email = (EditText)findViewById(R.id.email_edit);
        pass = (EditText)findViewById(R.id.password_edit);
        pass_confirm = (EditText)findViewById(R.id.password_check_edit);
        surname = (EditText)findViewById(R.id.surname_edit);
        name = (EditText)findViewById(R.id.name_edit);
        middlename = (EditText)findViewById(R.id.middlename_edit);

        age = (EditText)findViewById(R.id.age_edit);
        polis = (EditText)findViewById(R.id.polis_edit);
        sex = (Spinner) findViewById(R.id.sex_spinner);

        Button button = (Button)findViewById(R.id.register_button);

        CardView patient_card = (CardView)findViewById(R.id.registration_patient);

        //Показываем нужные поля ввода
        if(token_type== StomatologyAccountManager.ROLE_PATIENT)
            patient_card.setVisibility(View.VISIBLE);


        button.setOnClickListener(this);

    }

    // Регистрация
    @Override
    public void onClick(View v) {
        //Настройка запроса
        Retrofit retrofit = RetrofitFactory.GetRetrofit();
        API api = retrofit.create(API.class);

        final Call<ResponseBody> resp;

        //Заполняем модель
        final Models.PatientRegistrationViewModel model = new Models.PatientRegistrationViewModel();
        model.Email = email.getText().toString();
        model.Password = pass.getText().toString();
        model.ConfirmPassword = pass_confirm.getText().toString();
        model.Name = name.getText().toString();
        model.Surname = surname.getText().toString();
        model.Middlename = middlename.getText().toString();

        //Роле-специифичные поля
        if(token_type==StomatologyAccountManager.ROLE_PATIENT) {
            String[] sex_arr = getResources().getStringArray(R.array.sex);
            model.IsMen = sex.getSelectedItem().toString().equals(sex_arr[0]);
            model.Age = parse_int(age.getText().toString(),0);
            model.MedicalCardNumber = parse_int(polis.getText().toString(),0);
            resp = api.registerPatient(model);
        }
        else
            return;

        final Context context = this;

        //Запрос
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                try
                {
                    if(code==200)
                    {
                        //OK
                        Intent result = new Intent();
                        result.putExtra("username",model.Email);
                        result.putExtra("password",model.Password);
                        setResult(0, result);
                        Helpers.ShowMessage(context, "Спасибо за регистрацию!", "", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                    }
                    else {
                        //Error
                        ResponseBody body = response.errorBody();
                        String str = body.string();
                        Log.d("WTF", str);
                        Helpers.ShowMessage(context, "Не удалось зарегестрироваться","JSON сложный, парсить лень, читайте сами:\n"+str);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



    //Парсит строку, если фейл - пол умолчанию
    private int parse_int(String str, int default_value)
    {
        try {
            return Integer.parseInt(str);
        } catch(NumberFormatException nfe) {
            // Log exception.
            return default_value;
        }
    }

}
