package com.cooku.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cooku.R;
import com.cooku.models.RecipeItem;

import java.util.ArrayList;

/**
 * Created by Julie on 7/28/2015.
 */



public class RecipeResultsListAdapter extends ArrayAdapter<RecipeItem>
{

    private final Context context;
    private final ArrayList<RecipeItem> itemsList;

    public RecipeResultsListAdapter (Context context, ArrayList<RecipeItem> itemsList)
    {
        super(context, 0, itemsList);
        this.context = context;
        this.itemsList = itemsList;
    }

    public View getView (int position, View convertView, ViewGroup parent)
    {
        RecipeItem recipeName = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_search_results_row_item, parent,false);
            TextView ingName = (TextView) convertView.findViewById(R.id.recipe_name);
            ingName.setText(recipeName.getName());
        }
        return convertView;
    }

}
