package com.example.stomatologyclient.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.adapters.OnListInteractListener;
import com.example.stomatologyclient.adapters.UniversalListAdapter;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.auth.StomatologyAccountManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import trikita.knork.Knork;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddToothWorkDialog extends DialogFragment implements  OnListInteractListener{

    @Knork.Id(R.id.tooth_number) EditText tooth_number;
    @Knork.Id(R.id.tech_procedure_list) RecyclerView list;

    UniversalListAdapter adapter;

    private Retrofit retrofit;
    private API api;

    private OnToothWorkSelectedListener listener;

    private List<Models.TechnicanProcedure> procedures;

    public AddToothWorkDialog() {
        // Required empty public constructor
    }

    public AddToothWorkDialog(OnToothWorkSelectedListener listener) {
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_tooth_work_dialog, container, false);
        getDialog().setTitle("Добавление изделия");

        Knork.inject(root, this);

        retrofit = RetrofitFactory.GetRetrofit(StomatologyAccountManager.getAuthToken(getActivity()));
        api = retrofit.create(API.class);

        final Context context = getActivity();
        final OnListInteractListener listener = this;

        //Загрузка списка TechnicanProcedure
        Call<List<Models.TechnicanProcedure>> procedures_ = api.getTechnicanProcedures();
        procedures_.enqueue(new Callback<List<Models.TechnicanProcedure>>() {
            @Override
            public void onResponse(Call<List<Models.TechnicanProcedure>> call, Response<List<Models.TechnicanProcedure>> response) {
                if(response.code()==200)
                {
                    procedures = response.body();
                    adapter = new UniversalListAdapter(context, procedures, false, true, false);
                    adapter.setOnListInteractListenr(listener);
                    list.setAdapter(adapter);
                    list.setLayoutManager(new LinearLayoutManager(context));
                }
                else
                    Toast.makeText(context,"Не удалось загрузить",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Models.TechnicanProcedure>> call, Throwable t) {
                Toast.makeText(context,"Не удалось загрузить",Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    //Элемент выбран
    @Override
    public void OnItemClick(int id) {

        //Валидация ввода зубов
        int number;
        try
        {
            number = Integer.parseInt(tooth_number.getText().toString());
            if((number<11)||(number>18 && number<21)||(number>28 && number<31)||(number>38&&number<41)||(number>48))
                throw new NumberFormatException();
        }
        catch (NumberFormatException exp)
        {
            Toast.makeText(getActivity(),"Неверный номер зуба",Toast.LENGTH_LONG).show();
            return;
        }

        Models.Tooth tooth = new Models.Tooth();
        tooth.ToothNo = number;


        tooth.ProcedureId = id;
        tooth.Procedure = get_by_id(id);
        if(listener!=null)
            listener.OnToothWorkSelected(tooth);

        dismiss();
    }

    //Найти процедуру по Id
    private Models.TechnicanProcedure get_by_id(int id)
    {
        for(Models.TechnicanProcedure procedure: procedures)
            if(procedure.Id ==id)
                return  procedure;

        return  null;
    }

    @Override
    public void OnRemoveClick(int id) {

    }

    @Override
    public void OnEditClick(int id) {

    }


    public interface OnToothWorkSelectedListener
    {
        void OnToothWorkSelected(Models.Tooth tooth);
    }

}
