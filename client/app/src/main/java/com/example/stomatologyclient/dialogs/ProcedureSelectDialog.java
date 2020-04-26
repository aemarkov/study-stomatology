package com.example.stomatologyclient.dialogs;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.api.API;
import com.example.stomatologyclient.api.Models;
import com.example.stomatologyclient.api.RetrofitFactory;
import com.example.stomatologyclient.auth.StomatologyAccountManager;
import com.example.stomatologyclient.models.NamedModel;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import trikita.knork.Knork;

/**
 * Диалогвое окно выбора процедуры
 * Представляет древовидный список
 */
public class ProcedureSelectDialog extends DialogFragment implements TreeNode.TreeNodeClickListener {

    TreeNode tree_root;
    AndroidTreeView tree_view;
    FrameLayout fr;

    //Для запросов
    private Retrofit retrofit;
    private API api;

    //Слушатель обработчиков нажатия на процедуру
    //(сам фрагмент. надо передать во вложенный клсс
    TreeNode.TreeNodeClickListener listener;

    OnItemeSelectedListener item_listener;

    public ProcedureSelectDialog(){}

    public ProcedureSelectDialog(OnItemeSelectedListener listener)
    {
        this.item_listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_procedure_select_dialog, container, false);
        Knork.inject(root, this);

        fr = (FrameLayout) root.findViewById(R.id.tree_container);

        //Добавляем дерево
        tree_root = TreeNode.root();

        //Загрузка дерева
        retrofit = RetrofitFactory.GetRetrofit(StomatologyAccountManager.getAuthToken(getActivity()));
        api = retrofit.create(API.class);

        listener = this;

        getDialog().setTitle("Выбор процедуры");

        //Процедуры
        LoadTask task = new LoadTask();
        task.execute();

        return root;
    }

    //Выводит сообщение о неудаче и закрыавает диалог
    private void failed() {
        Toast.makeText(getActivity(), "Не удалось загрузить процедуры", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    //ОК
    /*@Knork.On(Knork.CLICK + R.id.button_cancel)
    public void cancel_click(View v) {
        dismiss();
    }

    //Отмена
    @Knork.On(Knork.CLICK + R.id.button_ok)
    public void ok_click(View v) {
        dismiss();
    }*/

    //Нажатие на элемент процедуры
    @Override
    public void onClick(TreeNode node, Object value)
    {
        Object ovalue =  node.getValue();
        if(ovalue instanceof NamedModel && item_listener!=null) {
           item_listener.itemSelected((NamedModel) ovalue);
            dismiss();
        }
    }


    /**
     * Загрузка процедур
     */
    class LoadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {

                Call<List<Models.Category>> categories = api.getCategories();
                List<Models.Category> categories_ = categories.execute().body();

                for (Models.Category category : categories_) {
                    final TreeNode category_node = new TreeNode(category).setViewHolder(new TreeHolder(getActivity()));

                    //Категории
                    Call<Models.Category> category_call = api.getCategory(category.Id);
                    List<Models.Subcategory> subcategories = category_call.execute().body().Subcategories;

                    //Подкатегории
                    for (Models.Subcategory subcategory : subcategories) {
                        TreeNode subcategory_node = new TreeNode(subcategory).setViewHolder(new TreeHolder(getActivity()));;

                        //Процедуры
                        for (Models.Procedure procedure : subcategory.Procedures) {
                            TreeNode procedure_node = new TreeNode(procedure).setViewHolder(new TreeHolder(getActivity()));;
                            procedure_node.setClickListener(listener);
                            subcategory_node.addChild(procedure_node);
                        }

                        category_node.addChild(subcategory_node);
                    }


                    //Добавляем категорию в дерево
                    tree_root.addChild(category_node);
                }

            } catch (IOException exp) {
                exp.printStackTrace();
                failed();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tree_view = new AndroidTreeView(getActivity(), tree_root);
            fr.addView(tree_view.getView());
        }
    }


    public interface OnItemeSelectedListener
    {
        void itemSelected(Object item);
    }

}