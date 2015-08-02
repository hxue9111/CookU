package com.cooku.tasks;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.cooku.data.RecipeContract;

/**
 * Created by sarahford on 8/2/15.
 */
public class AddIngredientTask extends AsyncTask<String, Void, Void> {
    private final String LOG_TAG = AddIngredientTask.class.getSimpleName();

    private final Context mContext;

    public AddIngredientTask(Context context) {
        mContext = context;
    }

    private boolean DEBUG = true;


    long addIngredient(String ingredientName) {



            ContentValues ingredientValues = new ContentValues();

            // Then add the data, along with the corresponding name of the data type,
            // so the content provider knows what kind of value is being inserted.
            ingredientValues.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_NAME, ingredientName);

            Uri insertedUri = mContext.getContentResolver().insert(
                    RecipeContract.IngredientEntry.CONTENT_URI,
                    ingredientValues
            );

            // The resulting URI contains the ID for the row.  Extract the locationId from the Uri.
            long ingredientId = ContentUris.parseId(insertedUri);

        // Wait, that worked?  Yes!
        return ingredientId;
    }


    @Override
    protected Void doInBackground(String... params) {
        addIngredient(params[0]); //not sure if this is right but wil use debugger to check
        return null;
    }


}
