package com.example.stomatologyclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.stomatologyclient.R;
import com.example.stomatologyclient.models.NamedModel;

import java.util.List;

/**
 * ViewHolder для NamedListAdapter
 */
class NameViewHolder extends RecyclerView.ViewHolder
{
    public ImageView imageView;
    public TextView nameTextView;
    public TextView costTextView;
    public  ImageView imageViewRemove;

    public View view;

    public NameViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView)itemView.findViewById(R.id.list_image);
        nameTextView = (TextView) itemView.findViewById(R.id.list_name);
        costTextView = (TextView)itemView.findViewById(R.id.list_cost);
        imageViewRemove = (ImageView)itemView.findViewById(R.id.list_remove);
        view = itemView;
    }
}

/**
 * Адаптер для отображения всяких списков от сервера
 */

public class NamedListAdapter extends  RecyclerView.Adapter<NameViewHolder> implements View.OnClickListener
{
    private List<? extends NamedModel> items;
    private LayoutInflater inflater;

    private boolean has_cost;       //Рисовать цену
    private boolean has_remove;     //Рисовать крестик удаления
    private boolean has_image;      //Рисовать картинку

    private Context context;

    OnListInteractListener listener;

    public NamedListAdapter(Context context, List<? extends NamedModel> items, boolean has_image, boolean has_cost, boolean has_remove)
    {
        this.items = items;
        this.has_cost=has_cost;
        this.has_image = has_image;
        this.has_remove=has_remove;
        this.context = context;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Устанавливает слушатель
    public  void setOnListInteractListenr(OnListInteractListener listener)
    {
        this.listener = listener;
    }

    @Override
    public NameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.named_list_item,parent, false);
        NameViewHolder holder = new NameViewHolder(convertView);

        holder.imageViewRemove.setOnClickListener(this);

        //Обработчики
        convertView.setOnClickListener(this);
        holder.imageViewRemove.setOnClickListener(this);

        return holder;

    }

    @Override
    public void onBindViewHolder(NameViewHolder holder, int position)
    {
        holder.imageViewRemove.setTag(position);
        holder.view.setTag(position);
        holder.nameTextView.setText(items.get(position).Name());

        if(has_cost)
        {
            holder.costTextView.setVisibility(View.VISIBLE);
            holder.costTextView.setText(String.valueOf(items.get(position).Cost));
        }
        else
            holder.costTextView.setVisibility(View.GONE);

        if(has_remove)
            holder.imageViewRemove.setVisibility(View.VISIBLE);
        else
            holder.imageViewRemove.setVisibility(View.GONE);

        if(has_image) {
            Glide.with(context)
                    .load(items.get(position).Image)
                    .centerCrop()
                    .placeholder(context.getResources().getDrawable(R.drawable.ic_menu_camera))
                    .into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }




    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        int tag = (int)v.getTag();
        NamedModel model = items.get(tag);

        if(id==R.id.list_remove)
        {
            //Удалить
            if(listener!=null)
                listener.OnRemoveClick(model);
        }
        else if(id==-1)
        {
            //На саму запись
            if(listener!=null)
                listener.OnItemClick(model);
        }

    }


    /**
     * Интерфейс для взаиодействия
     */
    public interface OnListInteractListener
    {
        /**
         * Нажата строка
         * @param model
         */
        void OnItemClick(NamedModel model);

        /**
         * Нажата кнопка удалить
         * @param model
         */
        void OnRemoveClick(NamedModel model);
    }
}
