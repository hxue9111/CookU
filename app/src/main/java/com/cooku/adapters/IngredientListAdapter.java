package com.cooku.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.cooku.R;
import com.cooku.data.RecipeContract.IngredientEntry;

/**
 * Created by Huang Xue on 7/25/2015.
 */
public class IngredientListAdapter extends CursorAdapter{


    public IngredientListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }



    /*
        This is ported from FetchWeatherTask --- but now we go straight from the cursor to the
        string.
     */
    private String[] convertCursorRowToUXFormat(Cursor cursor) {
        // get row indices for our cursor
        int idx_name = cursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT_NAME);
        int idx_selected = cursor.getColumnIndex(IngredientEntry.COLUMN_SELECTED);
        String [] ingredientInfo = new String [2];
        ingredientInfo[0] = cursor.getString(idx_name);
        ingredientInfo[1] = Integer.toString(cursor.getInt(idx_selected));
        return ingredientInfo;
    }

    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_list_row_item, parent, false);

        return view;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Lookup view for data population
            TextView ingName = (TextView) view.findViewById(R.id.ingredient_name);
            CheckBox toggle= (CheckBox) view.findViewById(R.id.ingredient_checkbox);
//            // Populate the data into the template view using the data object
            ingName.setText(cursor.getString(cursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT_NAME)));
            int isChecked = cursor.getInt(cursor.getColumnIndex(IngredientEntry.COLUMN_SELECTED));
            boolean setValue = isChecked == 1;
            toggle.setChecked(setValue);
    }
//    private final Context context;
//    private final ArrayList<IngredientItem> itemsList;
//    public IngredientListAdapter(Context context, ArrayList<IngredientItem> itemsList){
//        super(context,0,itemsList);
//
//        this.context = context;
//        this.itemsList = itemsList;
//    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        IngredientItem ingredient = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_list_row_item, parent, false);
//            // Lookup view for data population
//            TextView ingName = (TextView) convertView.findViewById(R.id.ingredient_name);
//            CheckBox toggle= (CheckBox) convertView.findViewById(R.id.ingredient_checkbox);
//            // Populate the data into the template view using the data object
//            ingName.setText(ingredient.getName());
//            toggle.setChecked(ingredient.isChecked());
//            // Return the completed view to render on screen
//        }
//        return convertView;
//    }
}
