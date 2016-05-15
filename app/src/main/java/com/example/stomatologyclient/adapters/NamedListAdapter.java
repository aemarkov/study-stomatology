package com.example.stomatologyclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.stomatologyclient.R;
import com.example.stomatologyclient.models.NamedModel;

import java.util.List;


/**
 * Адаптер для отображения всяких списков от сервера
 */

public class NamedListAdapter extends  RecyclerView.Adapter<NameViewHolder> implements View.OnClickListener {
    private List<? extends NamedModel> items;
    private LayoutInflater inflater;

    private boolean has_cost;       //Рисовать цену
    private boolean has_edit;       //Рисовать область редактирования
    private boolean has_image;      //Рисовать картинку

    private Context context;

    OnListInteractListener listener;

    public NamedListAdapter(Context context, List<? extends NamedModel> items, boolean has_image, boolean has_cost, boolean has_remove) {
        this.items = items;
        this.has_cost = has_cost;
        this.has_image = has_image;
        this.has_edit = has_remove;
        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Устанавливает слушатель
    public void setOnListInteractListenr(OnListInteractListener listener) {
        this.listener = listener;
    }

    @Override
    public NameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.named_list_item, parent, false);
        NameViewHolder holder = new NameViewHolder(convertView);

        //Обработчики
        convertView.setOnClickListener(this);
        holder.imageViewRemove.setOnClickListener(this);
        holder.imageViewEdit.setOnClickListener(this);

        return holder;

    }

    @Override
    public void onBindViewHolder(NameViewHolder holder, int position) {
        holder.imageViewRemove.setTag(position);
        holder.imageViewEdit.setTag(position);
        holder.view.setTag(position);
        holder.nameTextView.setText(items.get(position).Name());

        if (has_cost) {
            holder.costTextView.setVisibility(View.VISIBLE);
            holder.costTextView.setText(String.valueOf(items.get(position).Cost));
        } else
            holder.costTextView.setVisibility(View.GONE);

        if (has_edit)
            holder.editor_panel.setVisibility(View.VISIBLE);
        else
            holder.editor_panel.setVisibility(View.GONE);

        if (has_image) {
            Glide.with(context)
                    .load(items.get(position).Image)
                    .centerCrop()
                    .placeholder(context.getResources().getDrawable(R.drawable.ic_camera))
                    .into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        int tag = (int) v.getTag();
        NamedModel model = items.get(tag);

        if (id == R.id.list_remove) {
            //Удалить
            if (listener != null)
                listener.OnRemoveClick(model.Id);
        }
        if (id == R.id.list_edit) {
            //Редактировать
            if (listener != null)
                listener.OnEditClick(model.Id);
        } else if (id == -1) {
            //На саму запись
            if (listener != null)
                listener.OnItemClick(model.Id);
        }

    }
}



