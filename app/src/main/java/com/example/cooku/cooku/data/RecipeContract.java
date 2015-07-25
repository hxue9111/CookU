package com.example.cooku.cooku.data;

import android.provider.BaseColumns;

/**
 * Created by sarahford on 7/21/15.
 */
public class RecipeContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
//    public static final String CONTENT_AUTHORITY= "com.example.cooku.cooku.data";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
//    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
//    public static final String PATH_INGREDIENT = "ingredient";
//    public static final String PATH_RECIPE = "recipe";

    /* Inner class that defines the table contents of the Ingredient table */
    public static final class IngredientEntry implements BaseColumns {

//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();
//
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;

        // Table name
        public static final String TABLE_NAME = "ingredient";

        public static final String COLUMN_INGREDIENT_NAME = "ingredient_name";

//        public static Uri buildIngredientURI (long id) {
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//
//        public static Uri builIngredientURI(String ingredientName) {
//            return CONTENT_URI.buildUpon().appendPath(ingredientName).build();
//        }
//
//        public static String getIngredientFromURI(Uri uri){
//            return uri.getPathSegments().get(1);
//        }



    }

    /* Inner class that defines the table contents of the Recipe table */
    public static final class RecipeEntry implements BaseColumns {

//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();
//
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPE;
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPE;

        public static final String TABLE_NAME = "recipe";

        public static final String COLUMN_RECIPE_URL = "recipe_url";



 //define the contentprovider methods here
//    public static Uri buildRecipeUri(long id) {
//         return ContentUris.withAppendedId(CONTENT_URI, id);
//    }
//        public static Uri buildRecipeURI (String url) {
//            return CONTENT_URI.buildUpon().appendPath(url).build();
//        }
//        public static String getUrlFromURI(Uri uri) {
//            return uri.getPathSegments().get(1);
//
//        }
//
//
//        public static String getLocationSettingFromUri(Uri uri) {
//            return uri.getPathSegments().get(1);
//        }
//
//        public static long getDateFromUri(Uri uri) {
//            return Long.parseLong(uri.getPathSegments().get(2));
//        }
//
//        public static long getStartDateFromUri(Uri uri) {
//            String dateString = uri.getQueryParameter(COLUMN_DATE);
//            if (null != dateString && dateString.length() > 0)
//                return Long.parseLong(dateString);
//            else
//                return 0;
//        }
    }
}
