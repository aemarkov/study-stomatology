package com.example.stomatologyclient.activity;

import android.accounts.AccountAuthenticatorActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Activity для регистрации нового пользователя
 */
public class NewAccountActivity extends AccountAuthenticatorActivity implements View.OnClickListener
{

    public  final static String EXTRA_TOKEN_TYPE = "EXTRA_TOKEN_TYPE";
    String tokenType;

    EditText email;
    EditText pass;
    EditText pass_confirm;
    EditText surname;
    EditText name;
    EditText middlename;

    EditText age;
    EditText polis;
    Spinner sex;

    Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        
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

        button.setOnClickListener(this);

        tokenType = getIntent().getExtras().getString(EXTRA_TOKEN_TYPE);

        //findal Call<Response> resp = api.getToken()
    }


    // Регистрация
    @Override
    public void onClick(View v) {
        //Настройка запроса
        Retrofit retrofit = RetrofitFactory.GetRetrofit();
        API api = retrofit.create(API.class);

        Models.PatientRegistrationViewModel model = new Models.PatientRegistrationViewModel();
        model.Email = email.getText().toString();
        model.Password = pass.getText().toString();
        model.ConfirmPassword = pass_confirm.getText().toString();
        model.Name = name.getText().toString();
        model.Surname = surname.getText().toString();
        model.Middlename = middlename.getText().toString();

        String[] sex_arr = getResources().getStringArray(R.array.sex);

        model.IsMen = sex.getSelectedItem().toString().equals(sex_arr[0]);
        model.Age = Integer.parseInt(age.getText().toString());
        model.MedicalCardNumber = Integer.parseInt(polis.getText().toString());

        final Call<Response> resp = api.registerPatient(model);
        resp.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {
                int code = response.code();
                String str = response.message();
                Log.d("WTF",str);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}
