package com.cooku.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.cooku.data.RecipeContract;

/**
 * Created by sarahford on 8/2/15.
 */
public class DeleteIngredientTask extends AsyncTask<Object, Void, Void> {
    private final String LOG_TAG = DeleteIngredientTask.class.getSimpleName();

    private final Context mContext;

    public DeleteIngredientTask(Context context) {
        mContext = context;
    }

    private boolean DEBUG = true;


    void deleteIngredient(String ingredientName) {



        ContentValues ingredientValues = new ContentValues();

        // Then add the data, along with the corresponding name of the data type,
        // so the content provider knows what kind of value is being inserted.
        ingredientValues.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_NAME, ingredientName);
        String selectionClause = RecipeContract.IngredientEntry.COLUMN_INGREDIENT_NAME + " LIKE ?";
        String[] selectionArgs = {ingredientName};

        mContext.getContentResolver().delete(RecipeContract.IngredientEntry.CONTENT_URI,selectionClause, selectionArgs);
    }


    @Override
    protected Void doInBackground(Object... params) {
        deleteIngredient(params[0].toString()); //not sure if this is right but wil use debugger to check
        return null;
    }


}
