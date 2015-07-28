package com.cooku.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.test.InstrumentationTestCase;

import com.cooku.data.RecipeContract.IngredientEntry;
import com.cooku.data.RecipeContract.RecipeEntry;

import java.io.File;
import java.util.HashSet;

/**
 * Created by sarahford on 7/23/15.
 */


public class TestDb extends InstrumentationTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();
    Context context;

    // Since we want each test to start with a clean slate

    void deleteTheDatabase() {
        context.deleteDatabase(RecipeDbHelper.DATABASE_NAME);
    }

    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */
    public void setUp() {
        context = getInstrumentation().getContext();
        deleteTheDatabase();

    }

    /*
        Students: Uncomment this test once you've written the code to create the Location
        table.  Note that you will have to have chosen the same column names that I did in
        my solution for this test to compile, so if you haven't yet done that, this is
        a good time to change your column names to match mine.
        Note that this only tests that the Location table has the correct columns, since we
        give you the code for the weather table.  This test does not look at the
     */
    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(RecipeEntry.TABLE_NAME);
        tableNameHashSet.add(IngredientEntry.TABLE_NAME);

        context.deleteDatabase(RecipeDbHelper.DATABASE_NAME);
//        String dbName = new RecipeDbHelper(context).getDatabaseName();


        SQLiteDatabase db = new RecipeDbHelper(context).getWritableDatabase();
        boolean here = db.isOpen();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + IngredientEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> ingredientColumnHashSet = new HashSet<String>();
        ingredientColumnHashSet.add(IngredientEntry._ID);
        ingredientColumnHashSet.add(IngredientEntry.COLUMN_INGREDIENT_NAME);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            ingredientColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                ingredientColumnHashSet.isEmpty());
        db.close();
    }

    /*
        Students:  Here is where you will build code to test that we can insert and query the
        location database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can uncomment out the "createNorthPoleLocationValues" function.  You can
        also make use of the ValidateCurrentRecord function from within TestUtilities.
    */
    public void testIngredientTable() {
        insertIngredient();
    }

    /*
        Students:  Here is where you will build code to test that we can insert and query the
        database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can use the "createWeatherValues" function.  You can
        also make use of the validateCurrentRecord function from within TestUtilities.
     */
    public void testRecipeTable() {
        //is this neccessary??????
//        long locationRowId = insertLocation();
//
//        // Make sure we have a valid row ID.
//        assertFalse("Error: Location Not Inserted Correctly", locationRowId == -1L);

        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        RecipeDbHelper dbHelper = new RecipeDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step (Weather): Create weather values
        ContentValues recipeValues = TestUtilities.createRecipeValues();

        // Third Step (Weather): Insert ContentValues into database and get a row ID back
        long recipeRowId = db.insert(RecipeEntry.TABLE_NAME, null, recipeValues);
        assertTrue(recipeRowId != -1);

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor recipeCursor = db.query(RecipeEntry.TABLE_NAME,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );

        // Move the cursor to the first valid database row and check to see if we have any rows
        assertTrue("Error: No Records returned from recipe query", recipeCursor.moveToFirst());

        // Fifth Step: Validate the location Query
        TestUtilities.validateCurrentRecord("testInsertReadDb recipeEntry failed to validate",
                recipeCursor, recipeValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse("Error: More than one record returned from weather query",
                recipeCursor.moveToNext());

        // Sixth Step: Close cursor and database
        recipeCursor.close();
        dbHelper.close();
    }


    public long insertIngredient() {
        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        RecipeDbHelper dbHelper = new RecipeDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step: Create ContentValues of what you want to insert
        // (you can use the createNorthPoleLocationValues if you wish)
        ContentValues testValues = TestUtilities.createIngredientValues();

        // Third Step: Insert ContentValues into database and get a row ID back
        long ingredientRowId;
        ingredientRowId = db.insert(IngredientEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue(ingredientRowId != -1);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor cursor = db.query(IngredientEntry.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // Move the cursor to a valid database row and check to see if we got any records back
        // from the query
        assertTrue("Error: No Records returned from ingredient query", cursor.moveToFirst());

        // Fifth Step: Validate data in resulting Cursor with the original ContentValues
        // (you can use the validateCurrentRecord function in TestUtilities to validate the
        // query if you like)
        TestUtilities.validateCurrentRecord("Error: Ingredient Query Validation Failed",
                cursor, testValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse("Error: More than one record returned from Ingredient query",
                cursor.moveToNext());

        // Sixth Step: Close Cursor and Database
        cursor.close();
        db.close();
        return ingredientRowId;
    }
}
