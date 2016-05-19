package com.example.stomatologyclient.adapters;


import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stomatologyclient.R;

/**
 * ViewHolder для UniversalListAdapter
 */
public class NameViewHolder extends RecyclerView.ViewHolder
{
    public ImageView imageView;             //Картинка
    public TextView nameTextView;           //Название
    public TextView costTextView;           //Цена
    public  ImageView imageViewRemove;      //Удалить
    public ImageView imageViewEdit;         //Редактировать

    public View editor_panel;               //Область редактироания
    public View view;                       //Вся вьюха

    public NameViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView)itemView.findViewById(R.id.list_image);
        nameTextView = (TextView) itemView.findViewById(R.id.list_name);
        costTextView = (TextView)itemView.findViewById(R.id.list_cost);
        imageViewRemove = (ImageView)itemView.findViewById(R.id.list_remove);
        imageViewEdit = (ImageView)itemView.findViewById(R.id.list_edit);
        editor_panel = itemView.findViewById(R.id.editor_panel);
        view = itemView;
    }
}