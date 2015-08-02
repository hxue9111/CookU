package com.cooku.data;

import android.net.Uri;
import android.os.AsyncTask;

import com.cooku.models.RecipeItem;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Huang Xue on 7/30/2015.
 */
public class RecipeSearcher {
    private final OkHttpClient client = new OkHttpClient();
    private final List<RecipeItem> recipes;
    private final String BASE_URL;
    private final String PAGE_PARAM = "page";
    private int page = 1;
    private RecipeSearcherCallback callback;

    //Create searcher with array of ingredients, list to add recipes to, and callback upon finish loading
    public RecipeSearcher(String[] ingredients, List<RecipeItem> recipes, RecipeSearcherCallback callback){
        this.recipes = recipes;
        this.BASE_URL ="http://www.cookcucina.com/v1/recipes/search?ingredients=" +
                        android.text.TextUtils.join(",",ingredients);
        this.callback = callback;
        //http://www.cookcucina.com/v1/recipes/search?ingredients=�..."
    }

    public void requestRecipes(){
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                //.appendQueryParameter(PAGE_PARAM, page)
                .build();
        page++;
        //Building the request
        Request request = new Request.Builder()
                .url(builtUri.toString())
                .build();
        //Asynchronous OkHttp Call
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Request request, IOException ioe) {
                ioe.printStackTrace();
            }

            @Override public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String stringResponse = response.body().string();

                //Send response string to the parser to add to recipe list
                new ResponseParser(recipes).execute(stringResponse);
            }
        });
    }
    //Asynchronous JSON parser, automatically adds recipe items to recipe list
    private class ResponseParser extends AsyncTask<String, Void, Void>{
        List<RecipeItem> recipes;
        public ResponseParser(List<RecipeItem> recipes){
            this.recipes = recipes;
        }
        @Override
        protected Void doInBackground(String ... json) {
            try {
                JSONObject jsonObject = new JSONObject(json[0]);
                JSONArray recipesArray = jsonObject.getJSONArray("recipes");
                for (int i=0; i < recipesArray.length(); i++)
                {
                    JSONObject recipeObject = recipesArray.getJSONObject(i);
                    // Pulling items from the array
                    RecipeItem recipe = new RecipeItem(
                            recipeObject.getString("title"),
                            recipeObject.getString("image_url"),
                            recipeObject.getString("source_url"),
                            recipeObject.getString("recipe_id")
                    );
                    recipes.add(recipe);
                }
            }catch(JSONException jse){
                jse.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v){
            callback.onFinishedLoading();
        }
    }

    //Callback interface
    public interface RecipeSearcherCallback{
        public void onFinishedLoading();
    }
}
