package com.cooku.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.InstrumentationTestCase;

/**
 * Created by sarahford on 7/28/15.
 */
public class TestUriMatcher extends InstrumentationTestCase{

    private static final Uri TEST_INGREDIENT = RecipeContract.IngredientEntry.CONTENT_URI;
    private static final Uri TEST_RECIPE = RecipeContract.RecipeEntry.CONTENT_URI;
    private static final Uri TEST_INGREDIENT_ALL = RecipeContract.IngredientEntry.getAllIngredientsURI();
    /*
        Students: This function tests that your UriMatcher returns the correct integer value
        for each of the Uri types that our ContentProvider can handle.  Uncomment this when you are
        ready to test your UriMatcher.
     */
    public void testUriMatcher() {
        UriMatcher testMatcher = RecipeProvider.buildUriMatcher();

        assertEquals("Error: The INGREDIENT URI was matched incorrectly.",
                testMatcher.match(TEST_INGREDIENT), RecipeProvider.INGREDIENT);
        assertEquals("Error on all ingredient match.", testMatcher.match(TEST_INGREDIENT_ALL), RecipeProvider.INGREDIENT_ALL);
        assertEquals("Error: The WEATHER WITH LOCATION URI was matched incorrectly.",
                testMatcher.match(TEST_RECIPE), RecipeProvider.RECIPE);
    }
}
