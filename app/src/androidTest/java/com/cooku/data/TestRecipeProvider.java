package com.cooku.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.InstrumentationTestCase;

import com.cooku.data.RecipeContract.IngredientEntry;

import java.util.ArrayList;

/**
 * Created by sarahford on 7/28/15.
 */
public class TestRecipeProvider extends InstrumentationTestCase {
    public static final String LOG_TAG = RecipeProvider.class.getSimpleName();
    public static Context context;

    /*
       This helper function deletes all records from both database tables using the ContentProvider.
       It also queries the ContentProvider to make sure that the database has been successfully
       deleted, so it cannot be used until the Query and Delete functions have been written
       in the ContentProvider.
       Students: Replace the calls to deleteAllRecordsFromDB with this one after you have written
       the delete functionality in the ContentProvider.
     */
    public void deleteAllRecordsFromProvider() {
        context.getContentResolver().delete(
                IngredientEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = context.getContentResolver().query(
                IngredientEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Ingredient table during delete", 0, cursor.getCount());
        cursor.close();
//        context.getContentResolver().delete(
//                RecipeEntry.CONTENT_URI,
//                null,
//                null
//        );
//        cursor = context.getContentResolver().query(
//                RecipeEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Error: Records not deleted from Location table during delete", 0, cursor.getCount());
//        cursor.close();
    }

    /*
        Student: Refactor this function to use the deleteAllRecordsFromProvider functionality once
        you have implemented delete functionality there.
     */
    public void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    // Since we want each test to start with a clean slate, run deleteAllRecords
    // in setUp (called by the test runner before each test).
    @Override
    protected void setUp() throws Exception {
        context = getInstrumentation().getContext().createPackageContext("com.cooku", Context.CONTEXT_IGNORE_SECURITY);
        super.setUp();
        deleteAllRecords();
    }

    /*
        This test checks to make sure that the content provider is registered correctly.
        Students: Uncomment this test to make sure you've correctly registered the WeatherProvider.
     */
    public void testProviderRegistry() {
        PackageManager pm = context.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(context.getPackageName(),
                RecipeProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: RecipeProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + RecipeContract.CONTENT_AUTHORITY,
                    providerInfo.authority, RecipeContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: RecipeProvider not registered at " + context.getPackageName(),
                    false);
        }
    }

    /*
            This test doesn't touch the database.  It verifies that the ContentProvider returns
            the correct type for each type of URI that it can handle.
            Students: Uncomment this test to verify that your implementation of GetType is
            functioning correctly.
         */
    public void testGetType() {
        // content://com.example.android.sunshine.app/weather/
        String type = context.getContentResolver().getType(IngredientEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error: the IngredientEntry CONTENT_URI should return IngredientEntry.CONTENT_TYPE",
                IngredientEntry.CONTENT_TYPE, type);
        String ingredientName = "eggs";

//        String testURL = "https://google.com"; // December 21st, 2014
        // content://com.example.android.sunshine.app/weather/94074/20140612
//        type = context.getContentResolver().getType(
//                RecipeEntry.buildRecipeUri(testURL));
//        // vnd.android.cursor.item/com.example.android.sunshine.app/weather/1419120000
//        assertEquals("Error: the RecipeEntry CONTENT_URI with location and date should return WeatherEntry.CONTENT_ITEM_TYPE",
//                RecipeEntry.CONTENT_TYPE, type);

        //TODO: figure out the difference between content type and content item type and if we need both
    }


    /*
        This test uses the database directly to insert and then uses the ContentProvider to
        read out the data.  Uncomment this test to see if the basic weather query functionality
        given in the ContentProvider is working correctly.
     */
    public void testBasicIngredientQuery() {
        // insert our test records into the database
        RecipeDbHelper dbHelper = new RecipeDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues ingredientValues = TestUtilities.createIngredientValues();
        long ingredientValueRow = TestUtilities.insertIngredientValues(context);

        assertTrue("Unable to Insert ingredientEntry into the Database", ingredientValueRow != -1);

        db.close();
        //TODO what does this do????
        // Test the basic content provider query
        Cursor ingredientCursor = context.getContentResolver().query(
                IngredientEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // Make sure we get the correct cursor out of the database
        TestUtilities.validateCursor("testIngredientQuery", ingredientCursor, ingredientValues);
    }

    /*
        This test uses the database directly to insert and then uses the ContentProvider to
        read out the data.  Uncomment this test to see if your location queries are
        performing correctly.
     */
//    public void testBasicRecipeQueries() {
//        // insert our test records into the database
//        RecipeDbHelper dbHelper = new RecipeDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues testValues = TestUtilities.createRecipeValues();
//        long recipeRowId = TestUtilities.insertRecipeValues(context);
//
//        // Test the basic content provider query
//        Cursor recipeCursor = context.getContentResolver().query(
//                RecipeEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//
//        // Make sure we get the correct cursor out of the database
//        TestUtilities.validateCursor("test recipe query", recipeCursor, testValues);
//
//
//    }


    // Make sure we can still delete after adding stuff
    //
    // Student: Uncomment this test after you have completed writing the insert functionality
    // in your provider.  It relies on insertions with testInsertReadProvider, so insert and
    // query functionality must also be complete before this test can be used.
    public void testInsertReadProvider() {
        ContentValues testValues = TestUtilities.createIngredientValues();

        // Register a content observer for our insert.  This time, directly with the content resolver
        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        context.getContentResolver().registerContentObserver(IngredientEntry.CONTENT_URI, true, tco);
        Uri ingredientUri = context.getContentResolver().insert(IngredientEntry.CONTENT_URI, testValues);

        // Did our content observer get called?  Students:  If this fails, your insert location
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        context.getContentResolver().unregisterContentObserver(tco);

        long ingredientRowId = ContentUris.parseId(ingredientUri);

        // Verify we got a row back.
        assertTrue(ingredientRowId != -1);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.
        Cursor cursor = context.getContentResolver().query(
                IngredientEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestUtilities.validateCursor("testInsertReadProvider. Error validating IngredientEntry.",
                cursor, testValues);

        // Fantastic.  Now that we have a ingredient, add some recipes!
//        ContentValues recipeValues = TestUtilities.createRecipeValues();
//        // The TestContentObserver is a one-shot class
//        tco = TestUtilities.getTestContentObserver();
//
//        context.getContentResolver().registerContentObserver(RecipeEntry.CONTENT_URI, true, tco);
//
//        Uri recipeInsertUri = context.getContentResolver()
//                .insert(RecipeEntry.CONTENT_URI, recipeValues);
//        assertTrue(recipeInsertUri != null);
//
//        // Did our content observer get called?  Students:  If this fails, your insert weather
//        // in your ContentProvider isn't calling
//        // getContext().getContentResolver().notifyChange(uri, null);
//        tco.waitForNotificationOrFail();
//        context.getContentResolver().unregisterContentObserver(tco);
//
//        // A cursor is your primary interface to the query results.
//        Cursor recipeCursor = context.getContentResolver().query(
//                RecipeEntry.CONTENT_URI,  // Table to Query
//                null, // leaving "columns" null just returns all the columns.
//                null, // cols for "where" clause
//                null, // values for "where" clause
//                null // columns to group by
//        );
//
//        TestUtilities.validateCursor("testInsertReadProvider. Error validating RecipeEntry insert.",
//                recipeCursor, recipeValues);
    }

    // Make sure we can still delete after adding/updating stuff
    //
    // Student: Uncomment this test after you have completed writing the delete functionality
    // in your provider.  It relies on insertions with testInsertReadProvider, so insert and
    // query functionality must also be complete before this test can be used.
    public void testDeleteRecords() {
        testInsertReadProvider();

        // Register a content observer for our ingredient delete.
        TestUtilities.TestContentObserver ingredientObserver = TestUtilities.getTestContentObserver();
        context.getContentResolver().registerContentObserver(IngredientEntry.CONTENT_URI, true, ingredientObserver);

        // Register a content observer for our recipe delete.
//        TestUtilities.TestContentObserver recipeObserver = TestUtilities.getTestContentObserver();
//        context.getContentResolver().registerContentObserver(RecipeEntry.CONTENT_URI, true, recipeObserver);

        deleteAllRecordsFromProvider();

        // Students: If either of these fail, you most-likely are not calling the
        // getContext().getContentResolver().notifyChange(uri, null); in the ContentProvider
        // delete.  (only if the insertReadProvider is succeeding)
        ingredientObserver.waitForNotificationOrFail();
//        recipeObserver.waitForNotificationOrFail();

        context.getContentResolver().unregisterContentObserver(ingredientObserver);
//        context.getContentResolver().unregisterContentObserver(recipeObserver);
    }


    static ContentValues[] createBulkInsertIngredientValues() {
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("eggs");
        ingredients.add("milk");
        ContentValues[] returnContentValues = new ContentValues[ingredients.size()];
        int count = 0;

        for (String i: ingredients) {
            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put(IngredientEntry.COLUMN_INGREDIENT_NAME, i);
            returnContentValues[count] = ingredientValues ;
            count += 1;
        }
        return returnContentValues;
    }

    // Student: Uncomment this test after you have completed writing the BulkInsert functionality
    // in your provider.  Note that this test will work with the built-in (default) provider
    // implementation, which just inserts records one-at-a-time, so really do implement the
    // BulkInsert ContentProvider function.
    public void testBulkInsert() {

        // Now we can bulkInsert some ingredient.  In fact, we only implement BulkInsert for ingreidnets
        // entries.  With ContentProviders, you really only have to implement the features you
        // use, after all.
        ContentValues[] bulkInsertContentValues = createBulkInsertIngredientValues();

        // Register a content observer for our bulk insert.
        TestUtilities.TestContentObserver ingredientObserver = TestUtilities.getTestContentObserver();
        context.getContentResolver().registerContentObserver(IngredientEntry.CONTENT_URI, true, ingredientObserver);

        int insertCount = context.getContentResolver().bulkInsert(IngredientEntry.CONTENT_URI, bulkInsertContentValues);

        // Students:  If this fails, it means that you most-likely are not calling the
        // getContext().getContentResolver().notifyChange(uri, null); in your BulkInsert
        // ContentProvider method.
        ingredientObserver.waitForNotificationOrFail();
        context.getContentResolver().unregisterContentObserver(ingredientObserver);

        assertEquals(insertCount, 2);

        // A cursor is your primary interface to the query results.
        Cursor cursor = context.getContentResolver().query(
                IngredientEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order == by DATE ASCENDING
        );

        // we should have as many records in the database as we've inserted
        assertEquals(cursor.getCount(), 2);

        // and let's make sure they match the ones we created
        cursor.moveToFirst();
        for ( int i = 0; i < 2; i++, cursor.moveToNext() ) {
            TestUtilities.validateCurrentRecord("testBulkInsert.  Error validating WeatherEntry " + i,
                    cursor, bulkInsertContentValues[i]);
        }
        cursor.close();
    }
}
