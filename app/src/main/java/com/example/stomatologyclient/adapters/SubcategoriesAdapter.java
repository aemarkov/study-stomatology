package com.example.stomatologyclient.adapters;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.bumptech.glide.Glide;
import com.example.stomatologyclient.R;
import com.example.stomatologyclient.api.Models;

import java.util.List;

/**
 * ViewHolder для SubcategoriesAdapter
 */
class SectionViewHolder extends RecyclerView.ViewHolder
{
    public TextView sectionName;
    public ImageView imageView;
    public  TextView costTextView;
    public  ImageView imageViewRemove;      //Удалить
    public ImageView imageViewEdit;         //Редактировать

    public View editor_panel;               //Область редактироания

    public SectionViewHolder(View itemView) {
        super(itemView);

        sectionName = (TextView)itemView.findViewById(R.id.section_name);
        imageView = (ImageView) itemView.findViewById(R.id.list_image);
        costTextView = (TextView)itemView.findViewById(R.id.list_cost);
        imageViewRemove = (ImageView)itemView.findViewById(R.id.list_remove);
        imageViewEdit = (ImageView)itemView.findViewById(R.id.list_edit);
        editor_panel = itemView.findViewById(R.id.editor_panel);
    }
}

/**
 * Адаптер для подкатегорий
 * Разбивает список процедур на секции по подкатегориям
 */
public class SubcategoriesAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder>
{
    private Context context;
    private List<Models.Subcategory> items;
    private LayoutInflater inflater;
    private boolean has_edit;       //Рисовать область редактирования

    private OnListInteractListener headers_listener;
    private OnListInteractListener items_listener;

    private View.OnClickListener headers_click_listener;
    private View.OnClickListener items_click_listener;

    public SubcategoriesAdapter(Context context, final List<Models.Subcategory> items, boolean has_edit)
    {
        this.context =context;
        this.items = items;
        this.has_edit=has_edit;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Обработчик нажатий на заголовок
        headers_click_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                int tag = (int)v.getTag();
                Models.Subcategory subcategory = items.get(tag);

                if(id==R.id.list_edit)
                {
                    if(headers_listener!=null)
                        headers_listener.OnEditClick(subcategory.Id);
                }
                else if(id==R.id.list_remove)
                {
                    if(headers_listener!=null)
                        headers_listener.OnRemoveClick(subcategory.Id);
                }
            }
        };

        //Обработчик нажатий на элементы
        items_click_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<Integer,Integer> ids = (Pair<Integer,Integer>)v.getTag();
                Models.Procedure procedure = items.get(ids.first).Procedures.get(ids.second);
                int id = v.getId();

                if(id == R.id.list_edit)
                {
                    if(items_listener!=null)
                        items_listener.OnEditClick(procedure.Id);
                }
                else if(id==R.id.list_remove)
                {
                    if(items_listener!=null)
                        items_listener.OnRemoveClick(procedure.Id);
                }
                else if(id==-1)
                {
                    if(items_listener!=null)
                        items_listener.OnItemClick(procedure.Id);
                }
            }
        };
    }

    /**
     * Установка обработчика кнопок редактирования списка
     * @param listener
     */
    public void setOnHeaerEditListener(OnListInteractListener listener)
    {
        this.headers_listener = listener;
    }

    /**
     * Установка обработчика элементов
     * @param listener
     */
    public void setOnItemsInterractListener(OnListInteractListener listener)
    {
        this.items_listener = listener;
    }

    @Override
    public int getSectionCount() {
        return items.size();
    }

    @Override
    public int getItemCount(int section) {
        List<Models.Procedure> procedures = items.get(section).Procedures;
        return procedures==null ? 0 : procedures.size();
    }

    //Заголовок раздела
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        String section_name = items.get(section).Name;
        SectionViewHolder vh = (SectionViewHolder)holder;
        vh.sectionName.setText(section_name);

        vh.imageViewRemove.setTag(section);
        vh.imageViewEdit.setTag(section);

        if(has_edit)
            vh.editor_panel.setVisibility(View.VISIBLE);
        else
            vh.editor_panel.setVisibility(View.GONE);
    }

    //Элемент (процедура)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, int relativePosition, int absolutePosition) {
        Models.Procedure procedure = items.get(section).Procedures.get(relativePosition);
        NameViewHolder vh = (NameViewHolder)holder;

        vh.nameTextView.setText(procedure.Name);
        vh.costTextView.setText(procedure.Cost+"р");

        Pair<Integer, Integer> ids = new Pair<>(section, relativePosition);
        vh.view.setTag(ids);
        vh.imageViewRemove.setTag(ids);
        vh.imageViewEdit.setTag(ids);

        Glide.with(context)
                .load(procedure.Image)
                .centerCrop()
                .placeholder(context.getResources().getDrawable(R.drawable.ic_camera))
                .into(vh.imageView);

        if(has_edit)
            vh.editor_panel.setVisibility(View.VISIBLE);
        else
            vh.editor_panel.setVisibility(View.GONE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;

        if(viewType == VIEW_TYPE_HEADER) {
            layout = R.layout.subcategories_list_header;
            View v = inflater.inflate(layout, parent, false);
            SectionViewHolder vh =  new SectionViewHolder(v);

            vh.imageViewEdit.setOnClickListener(headers_click_listener);
            vh.imageViewRemove.setOnClickListener(headers_click_listener);

            return vh;
        }
        else
        {
            layout = R.layout.named_list_item;
            View v = inflater.inflate(layout, parent, false);
            NameViewHolder vh =   new NameViewHolder(v);

            vh.imageViewEdit.setOnClickListener(items_click_listener);
            vh.imageViewRemove.setOnClickListener(items_click_listener);
            vh.view.setOnClickListener(items_click_listener);

            return  vh;
        }


    }

}
