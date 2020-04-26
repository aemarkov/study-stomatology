package com.example.stomatologyclient.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.stomatologyclient.R;
import com.example.stomatologyclient.models.NamedModel;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Элемент списка дерева для придания большего графона
 */
public class TreeHolder extends TreeNode.BaseNodeViewHolder<NamedModel>
{
    public TreeHolder(Context context) {
        super(context);
    }

    NamedModel model;

    @Override
    public View createNodeView(TreeNode node, NamedModel value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.graphon_tree_item, null, false);

        TextView tvValue = (TextView) view.findViewById(R.id.tree_node_value);
        tvValue.setText(value.Name());
        tvValue.setTextSize(get_size(tvValue.getTextSize(), node.getLevel()));

        if(node.getLevel()==1)
        {
            tvValue.setTextColor(context.getResources().getColor(R.color.black));
        }

        return view;
    }

    //Уменьшение размера шрифта вложенных элементов
    float get_size(float start_size, int level)
    {
        float small = 0.8f;
        return start_size*(float)Math.pow(small, level-1);
    }

    /*float get_margin(float start_margin, int level)
    {
        return start_margin + level*start_margin;
    }*/
}
