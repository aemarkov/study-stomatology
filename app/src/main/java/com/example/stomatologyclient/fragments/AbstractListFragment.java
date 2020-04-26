package com.example.stomatologyclient.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.adapters.UniversalListAdapter;
import com.example.stomatologyclient.adapters.OnListInteractListener;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.RetrofitFactory;

import retrofit2.Retrofit;

/**
 * Абстрактный базовый фрагмент для фрагментов, которые показывают список
 */
public abstract class AbstractListFragment extends Fragment implements OnListInteractListener
{
    protected UniversalListAdapter adapter;
    protected RecyclerView recyclerView;
    protected Retrofit retrofit;
    protected API api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.list);

        //Настройка запроса
        retrofit = RetrofitFactory.GetRetrofit();
        api = retrofit.create(API.class);

        return root;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState, String token) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.list);

        //Настройка запроса
        retrofit = RetrofitFactory.GetRetrofit(token);
        api = retrofit.create(API.class);

        return root;
    }

    protected void start_activity_and_send_id(Class<? extends AppCompatActivity> activity, int id)
    {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtra("id",id);
        startActivity(intent);

    }

}
