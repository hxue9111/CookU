package com.cooku.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.cooku.data.RecipeContract.IngredientEntry;
import com.cooku.data.RecipeContract.RecipeEntry;
/**
 * Created by sarahford on 7/21/15.
 */
public class RecipeDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "recipe.db";

    public RecipeDbHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_INGREDIENT_TABLE = "CREATE TABLE " + IngredientEntry.TABLE_NAME + " (" +
                IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IngredientEntry.COLUMN_INGREDIENT_NAME + " TEXT NOT NULL " +
                " );";
        final String SQL_CREATE_RECIPE_TABLE = "CREATE TABLE " + RecipeEntry.TABLE_NAME + " (" +
                RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecipeEntry.COLUMN_RECIPE_URL + " TEXT NOT NULL " +
               " );";

        sqLiteDatabase.execSQL(SQL_CREATE_INGREDIENT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IngredientEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

