package com.example.cooku.cooku.adapters;

import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.cooku.cooku.models.IngredientListItem;
import com.example.cooku.cooku.R;

/**
 * Created by Huang Xue on 7/25/2015.
 */
public class IngredientListAdapter extends ArrayAdapter<IngredientListItem>{

    public final Context context;
    public final ArrayList<IngredientListItem> itemsList;
    public IngredientListAdapter(Context context, ArrayList<IngredientListItem> itemsList){
        super(context,0,itemsList);

        this.context = context;
        this.itemsList = itemsList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        IngredientListItem ingredient = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_list_row_item, parent, false);
            // Lookup view for data population
            TextView ingName = (TextView) convertView.findViewById(R.id.ingredient_name);
            CheckBox toggle= (CheckBox) convertView.findViewById(R.id.ingredient_checkbox);
            // Populate the data into the template view using the data object
            ingName.setText(ingredient.getName());
            toggle.setChecked(ingredient.isChecked());
            // Return the completed view to render on screen
        }
        return convertView;
    }
}
