package com.cooku.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
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
    public void bindView(View view, final Context context, final Cursor cursor) {
        final String ingredientName = cursor.getString(cursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT_NAME));
        boolean ingredientChecked = cursor.getInt(cursor.getColumnIndex(IngredientEntry.COLUMN_SELECTED)) == 1;

        TextView ingName = (TextView) view.findViewById(R.id.ingredient_name);
        CheckBox toggle= (CheckBox) view.findViewById(R.id.ingredient_checkbox);
        ingName.setText(ingredientName);
        toggle.setChecked(ingredientChecked);

        final ImageButton delete = (ImageButton)view.findViewById(R.id.ingredient_delete);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                context.getContentResolver().delete(IngredientEntry.CONTENT_URI,IngredientEntry.COLUMN_INGREDIENT_NAME + "=?",new String[]{ingredientName});
            }
        });
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                ContentValues values = new ContentValues();
                String selection = IngredientEntry.COLUMN_INGREDIENT_NAME + " = ?";
                String[] selectionArgs = {ingredientName};
                values.put(IngredientEntry.COLUMN_SELECTED, true);
                context.getContentResolver().update(IngredientEntry.CONTENT_URI,values,selection,selectionArgs);
            }
        });
    }

}
