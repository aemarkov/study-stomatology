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

public class UniversalListAdapter extends  RecyclerView.Adapter<NameViewHolder> implements View.OnClickListener {
    private List<? extends NamedModel> items;
    private LayoutInflater inflater;

    private boolean has_cost;       //Рисовать цену
    private boolean has_edit_are;   //Рисовать область редактирования
    private boolean has_remove;     //Рисовать кнопку удаления
    private boolean has_edit;       //Рисовать кнопку удаления
    private boolean has_image;      //Рисовать картинку

    private Context context;

    OnListInteractListener listener;

    /**
     *
     * @param context контекст
     * @param items объекты для отображения
     * @param has_image рисовать картинку?
     * @param has_cost рисовать ценц?
     * @param has_edit рисовать кнопку удаления?
     * @param has_remove рисовать кнопку редактирования?
     */
    public UniversalListAdapter(Context context, List<? extends NamedModel> items, boolean has_image, boolean has_cost, boolean has_edit, boolean has_remove) {
        this.items = items;
        this.has_cost = has_cost;
        this.has_image = has_image;
        this.has_edit_are = has_edit || has_remove;
        this.has_edit = has_edit;
        this.has_remove = has_remove;
        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Создает адаптер
     * @param context контекст
     * @param items объекты для отображения
     * @param has_image рисовать картинку?
     * @param has_cost рисовать ценц?
     * @param has_edit_area рисовать удалить и редактипровать?
     */
    public UniversalListAdapter(Context context, List<? extends NamedModel> items, boolean has_image, boolean has_cost, boolean has_edit_area)
    {
        this(context, items, has_image, has_cost, has_edit_area, has_edit_area);
    }

    //Устанавливает слушатель
    public void setOnListInteractListenr(OnListInteractListener listener) {
        this.listener = listener;
    }

    /**
     * Удаляет запсь на основе индекса
     * @param id
     */
    public void remove(int id)
    {
        for(int i = 0; i<items.size(); i++)
            if(items.get(i).Id==id)
            {
                items.remove(i);
                notifyItemRangeRemoved(i,1);
                //notifyDataSetChanged();
                break;
            }
    }

    public void insertEnd()
    {
        notifyItemInserted(items.size());
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
    public void onBindViewHolder(NameViewHolder holder, int position)
    {
        NamedModel item = items.get(position);

        holder.imageViewRemove.setTag(item);
        holder.imageViewEdit.setTag(item);
        holder.view.setTag(item);
        holder.nameTextView.setText(item.Name());

        //Настройка видимости компонентов и отображение данных
        if (has_cost) {
            holder.costTextView.setVisibility(View.VISIBLE);
            holder.costTextView.setText(String.valueOf(item.Cost));
        } else
            holder.costTextView.setVisibility(View.GONE);

        if (has_edit_are)
        {
            holder.editor_panel.setVisibility(View.VISIBLE);

            if(has_remove)
                holder.imageViewRemove.setVisibility(View.VISIBLE);
            else
                holder.imageViewRemove.setVisibility(View.GONE);

            if(has_edit)
                holder.imageViewEdit.setVisibility(View.VISIBLE);
            else
                holder.imageViewEdit.setVisibility(View.GONE);
        }
        else
            holder.editor_panel.setVisibility(View.GONE);

        if (has_image)
        {
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(item.Image)
                    .centerCrop()
                    .placeholder(context.getResources().getDrawable(R.drawable.ic_camera))
                    .into(holder.imageView);
        }
        else
            holder.imageView.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        //int tag = (int) v.getTag();
        NamedModel model = (NamedModel)v.getTag();

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



