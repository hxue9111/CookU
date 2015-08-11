package com.cooku.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.cooku.data.RecipeContract.IngredientEntry;
/**
 * Created by sarahford on 7/27/15.
// */
public class RecipeProvider extends ContentProvider {
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private RecipeDbHelper mOpenHelper;

    public static final int INGREDIENT = 100;
    public static final int RECIPE = 101;
    public static final int INGREDIENT_ALL = 102;

    /**
     * queries needed, all recipes and all ingredients **
     */


    private static final SQLiteQueryBuilder sRecipeContractQueryBuilder;

    static {
        sRecipeContractQueryBuilder = new SQLiteQueryBuilder();
        sRecipeContractQueryBuilder.setTables(IngredientEntry.TABLE_NAME);
    }


    private Cursor getAllIngredientsCursor() {
        String [] projection = {IngredientEntry._ID, IngredientEntry.COLUMN_INGREDIENT_NAME, IngredientEntry.COLUMN_SELECTED};
        return sRecipeContractQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                null);
    }
    private Cursor getSelectedIngredientsCursor(){
        String [] projection = {IngredientEntry._ID, IngredientEntry.COLUMN_INGREDIENT_NAME, IngredientEntry.COLUMN_SELECTED};
        return sRecipeContractQueryBuilder.query(mOpenHelper.getReadableDatabase(), projection, IngredientEntry.COLUMN_SELECTED + "=?", new String[] {"1"}, null, null, null);
    }



    /*
        Students: Here is where you need to create the UriMatcher. This UriMatcher will
        match each URI to the WEATHER, WEATHER_WITH_LOCATION, WEATHER_WITH_LOCATION_AND_DATE,
        and LOCATION integer constants defined above.  You can test this by uncommenting the
        testUriMatcher test within TestUriMatcher.
     */
    public static UriMatcher buildUriMatcher() {

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RecipeContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, RecipeContract.PATH_INGREDIENT, INGREDIENT);
        matcher.addURI(authority, RecipeContract.PATH_RECIPE, RECIPE);

        matcher.addURI(authority, RecipeContract.PATH_INGREDIENT + "/*", INGREDIENT_ALL);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new RecipeDbHelper(getContext());
        return true;
    }

    /*
        Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
        test this by uncommenting testGetType in TestProvider.
     */
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case INGREDIENT:
                return IngredientEntry.CONTENT_TYPE;
            case RECIPE:
                return IngredientEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case INGREDIENT_ALL:
            {
                retCursor = getAllIngredientsCursor();
                break;
            }
            case INGREDIENT:
            {
                retCursor = getSelectedIngredientsCursor();
                break;
            }
//            case RECIPE: {
//                //TODO Add recipe content provider methods
//                retCursor = null;
//                break;
//            }
            // "weather"
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
        Student: Add the ability to insert Locations to the implementation of this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case INGREDIENT: {
                long _id = db.insert(IngredientEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = IngredientEntry.buildIngredientURI(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
//            case RECIPE: {
//                long _id = db.insert(RecipeEntry.TABLE_NAME, null, values);
//                if ( _id > 0 )
//                    returnUri = RecipeEntry.buildRecipeUri(_id);
//                else
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                break;
//            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case INGREDIENT:
                rowsDeleted = db.delete(
                        IngredientEntry.TABLE_NAME, selection, selectionArgs);
                break;
//            case RECIPE:
//                rowsDeleted = db.delete(
//                        RecipeEntry.TABLE_NAME, selection, selectionArgs);
//                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case INGREDIENT:
                rowsUpdated = db.update(IngredientEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
//            case RECIPE:
//                rowsUpdated = db.update(RecipeEntry.TABLE_NAME, values, selection,
//                        selectionArgs);
//                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INGREDIENT:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(IngredientEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(14)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }

}
//}

