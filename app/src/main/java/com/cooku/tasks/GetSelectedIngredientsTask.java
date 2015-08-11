package com.cooku.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.cooku.data.RecipeContract;

import java.util.ArrayList;

/**
 * Created by sarahford on 8/11/15.
 */
public class GetSelectedIngredientsTask extends AsyncTask<Object, Void, String[]> {
    private final Context mContext;
    private final Listener mListener;
    public GetSelectedIngredientsTask(Context context, Listener listener) {
        mListener = listener;
        mContext = context;
    }

    private boolean DEBUG = true;


    String[] getSelected() {
        Cursor results = mContext.getContentResolver().query(RecipeContract.IngredientEntry.CONTENT_URI, new String[]{RecipeContract.IngredientEntry.COLUMN_INGREDIENT_NAME}, RecipeContract.IngredientEntry.COLUMN_SELECTED, new String [] {"1"}, null);
        int count = 0;
        ArrayList<String> selectedIngredeients = new ArrayList<String>();
        while(results.moveToNext()) {
            selectedIngredeients.add(results.getString(count));
            count += 1;
        }
        String[] selectedIngredient = selectedIngredeients.toArray(new String[selectedIngredeients.size()]);
        return selectedIngredient;

    }


    @Override
    protected String[] doInBackground(Object...params) {
        return getSelected();
    }

    @Override
    protected void onPostExecute(String[] result){
        mListener.onIngredientsRecieved(result);
    }

    public static interface Listener{
        public void onIngredientsRecieved(String[] ingredients);
    }

}
