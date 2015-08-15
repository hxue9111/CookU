package com.cooku.data;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.AsyncTask;

import com.cooku.models.RecipeItem;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Huang Xue on 7/30/2015.
 */
public class RecipeSearcher {
    private final OkHttpClient client = new OkHttpClient();
    private final List<RecipeItem> recipes;
    private final List<RecipeItem> buffer;
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
        this.buffer = new ArrayList<RecipeItem>();

        requestRecipes();
    }

    public void requestRecipes(){
        System.out.println("rec buffer: " + recipes.size());
        System.out.println("request buffer: " + buffer.size());

        if(buffer.size() > 0){
            recipes.addAll(buffer);
            callback.onFinishedLoading(buffer.size());
            buffer.clear();
        }

        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter(PAGE_PARAM, Integer.toString(page))
                .build();

        System.out.println(builtUri.toString());
        page++;
        //Building the request
        Request request = new Request.Builder()
                .url(builtUri.toString())
                .build();
        //Asynchronous OkHttp Call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException ioe) {
                ioe.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                String stringResponse = response.body().string();
                //Send response string to the parser to add to recipe list
                new ResponseParser(recipes,buffer).execute(stringResponse);
            }
        });
//        String staticTestData = "{\"count\":30,\"recipes\":[{\"publisher\":\"The Pioneer Woman\",\"f2f_url\":\"http://food2fork.com/view/47101\",\"title\":\"My Favorite Meatloaf\",\"source_url\":\"http://thepioneerwoman.com/cooking/2010/09/my-favorite-meatloaf/\",\"recipe_id\":\"47101\",\"image_url\":\"http://static.food2fork.com/5007085748_7400c77973_ob554.jpg\",\"social_rank\":99.99999999999916,\"publisher_url\":\"http://thepioneerwoman.com\"},{\"publisher\":\"The Pioneer Woman\",\"f2f_url\":\"http://food2fork.com/view/47119\",\"title\":\"Make-Ahead Muffin Melts\",\"source_url\":\"http://thepioneerwoman.com/cooking/2010/07/make-ahead-muffin-melts/\",\"recipe_id\":\"47119\",\"image_url\":\"http://static.food2fork.com/4806066889_64468ae431_bc027.jpg\",\"social_rank\":99.99999999999805,\"publisher_url\":\"http://thepioneerwoman.com\"},{\"publisher\":\"Closet Cooking\",\"f2f_url\":\"http://food2fork.com/view/35445\",\"title\":\"Okonomiyaki (Japanese Pancake)\",\"source_url\":\"http://www.closetcooking.com/2009/05/okonomiyaki-japanese-pancake.html\",\"recipe_id\":\"35445\",\"image_url\":\"http://static.food2fork.com/Okonomiyaki15000c5035ac.jpg\",\"social_rank\":99.99999999987664,\"publisher_url\":\"http://closetcooking.com\"},{\"publisher\":\"The Pioneer Woman\",\"f2f_url\":\"http://food2fork.com/view/2581e6\",\"title\":\"Cowboy Quiche\",\"source_url\":\"http://thepioneerwoman.com/cooking/2013/06/cowboy-quiche/\",\"recipe_id\":\"2581e6\",\"image_url\":\"http://static.food2fork.com/cowboyquichefb39.jpg\",\"social_rank\":99.99999999999959,\"publisher_url\":\"http://thepioneerwoman.com\"},{\"publisher\":\"The Pioneer Woman\",\"f2f_url\":\"http://food2fork.com/view/47106\",\"title\":\"Layered Salad\",\"source_url\":\"http://thepioneerwoman.com/cooking/2010/08/layered-salad/\",\"recipe_id\":\"47106\",\"image_url\":\"http://static.food2fork.com/4940301746_c16a4e7edf_o72c2.jpg\",\"social_rank\":99.99999999969766,\"publisher_url\":\"http://thepioneerwoman.com\"},{\"publisher\":\"The Pioneer Woman\",\"f2f_url\":\"http://food2fork.com/view/7d8e83\",\"title\":\"Breakfast Quesdillas\",\"source_url\":\"http://thepioneerwoman.com/cooking/2013/04/breakfast-quesadillas/\",\"recipe_id\":\"7d8e83\",\"image_url\":\"http://static.food2fork.com/breakfastquesadilladc70.jpg\",\"social_rank\":99.99999999712918,\"publisher_url\":\"http://thepioneerwoman.com\"},{\"publisher\":\"Closet Cooking\",\"f2f_url\":\"http://food2fork.com/view/35136\",\"title\":\"Beer Mac n Cheese Soup\",\"source_url\":\"http://www.closetcooking.com/2012/10/beer-mac-n-cheese-soup.html\",\"recipe_id\":\"35136\",\"image_url\":\"http://static.food2fork.com/Beer2BMac2Bn2BCheese2BSoup2B5002B3748fc1ba0f0.jpg\",\"social_rank\":99.99999999694427,\"publisher_url\":\"http://closetcooking.com\"},{\"publisher\":\"The Pioneer Woman\",\"f2f_url\":\"http://food2fork.com/view/47296\",\"title\":\"Pastor Ryan’s Pasta Carbonara\",\"source_url\":\"http://thepioneerwoman.com/cooking/2008/09/cooking-with-ryan-pasta-carbonara/\",\"recipe_id\":\"47296\",\"image_url\":\"http://static.food2fork.com/PastaCarbonara061c.jpg\",\"social_rank\":99.99999999693264,\"publisher_url\":\"http://thepioneerwoman.com\"},{\"publisher\":\"The Pioneer Woman\",\"f2f_url\":\"http://food2fork.com/view/46922\",\"title\":\"Pasta Carbonara\",\"source_url\":\"http://thepioneerwoman.com/cooking/2012/05/pasta-carbonara/\",\"recipe_id\":\"46922\",\"image_url\":\"http://static.food2fork.com/carbonara2f55.jpg\",\"social_rank\":99.99999999592087,\"publisher_url\":\"http://thepioneerwoman.com\"},{\"publisher\":\"Smitten Kitchen\",\"f2f_url\":\"http://food2fork.com/view/3618e0\",\"title\":\"spinach salad with warm bacon vinaigrette\",\"source_url\":\"http://smittenkitchen.com/blog/2012/11/spinach-salad-with-warm-bacon-vinaigrette/\",\"recipe_id\":\"3618e0\",\"image_url\":\"http://static.food2fork.com/8185356933_a35178eab24bbc.jpg\",\"social_rank\":99.99999999480895,\"publisher_url\":\"http://www.smittenkitchen.com\"},{\"publisher\":\"Closet Cooking\",\"f2f_url\":\"http://food2fork.com/view/35096\",\"title\":\"Avocado BLT with Fried Egg and Chipotle Mayo\",\"source_url\":\"http://www.closetcooking.com/2011/07/avocado-blt-with-fried-egg-and-chipotle.html\",\"recipe_id\":\"35096\",\"image_url\":\"http://static.food2fork.com/Avocado2BBLT2Bwith2BFried2BEgg2Band2BChipotle2BMayo2B5002B7704a8129f9b.jpg\",\"social_rank\":99.99999998700555,\"publisher_url\":\"http://closetcooking.com\"},{\"publisher\":\"The Pioneer Woman\",\"f2f_url\":\"http://food2fork.com/view/46870\",\"title\":\"Fancy Mac \\u0026 Cheese\",\"source_url\":\"http://thepioneerwoman.com/cooking/2013/02/fancy-mac-and-cheese/\",\"recipe_id\":\"46870\",\"image_url\":\"http://static.food2fork.com/fancymacf37f.jpg\",\"social_rank\":99.99999996260217,\"publisher_url\":\"http://thepioneerwoman.com\"},{\"publisher\":\"Closet Cooking\",\"f2f_url\":\"http://food2fork.com/view/35087\",\"title\":\"Asparagus and Double Smoked Bacon Popover\",\"source_url\":\"http://www.closetcooking.com/2012/05/asparagus-and-double-smoked-bacon.html\",\"recipe_id\":\"35087\",\"image_url\":\"http://static.food2fork.com/Asparagus2Band2BDouble2BSmoked2BBacon2BPopover2B5002B188858f07cf5.jpg\",\"social_rank\":99.99999994964467,\"publisher_url\":\"http://closetcooking.com\"},{\"publisher\":\"Closet Cooking\",\"f2f_url\":\"http://food2fork.com/view/35097\",\"title\":\"Avocado Breakfast Pizza with Fried Egg\",\"source_url\":\"http://www.closetcooking.com/2012/07/avocado-breakfast-pizza-with-fried-egg.html\",\"recipe_id\":\"35097\",\"image_url\":\"http://static.food2fork.com/Avocado2Band2BFried2BEgg2BBreakfast2BPizza2B5002B296294dcea8a.jpg\",\"social_rank\":99.99999990783806,\"publisher_url\":\"http://closetcooking.com\"},{\"publisher\":\"Closet Cooking\",\"f2f_url\":\"http://food2fork.com/view/35353\",\"title\":\"Guinness Braised Onion and Aged White Cheddar Quiche\",\"source_url\":\"http://www.closetcooking.com/2012/03/guinness-braised-onion-and-aged-white.html\",\"recipe_id\":\"35353\",\"image_url\":\"http://static.food2fork.com/Guinness2BBraised2BOnion2Band2BCheddar2BQuiche2B5002B4999f5d8250b.jpg\",\"social_rank\":99.9999998952943,\"publisher_url\":\"http://closetcooking.com\"},{\"publisher\":\"The Pioneer Woman\",\"f2f_url\":\"http://food2fork.com/view/47204\",\"title\":\"Bacon Onion Cheddar Biscuits\",\"source_url\":\"http://thepioneerwoman.com/cooking/2009/09/bacon-onion-cheddar-biscuits/\",\"recipe_id\":\"47204\",\"image_url\":\"http://static.food2fork.com/3894838010_b4ee0bbf377661.jpg\",\"social_rank\":99.99999955544457,\"publisher_url\":\"http://thepioneerwoman.com\"},{\"publisher\":\"Closet Cooking\",\"f2f_url\":\"http://food2fork.com/view/35116\",\"title\":\"Bacon Jam and Avocado Grilled Cheese Sandwich\",\"source_url\":\"http://www.closetcooking.com/2012/08/bacon-jam-and-avocado-grilled-cheese.html\",\"recipe_id\":\"35116\",\"image_url\":\"http://static.food2fork.com/Bacon2BJam2Band2BAvocado2BGrilled2BCheese2BSandwich2B5002B4611c559b66c.jpg\",\"social_rank\":99.99999939486977,\"publisher_url\":\"http://closetcooking.com\"},{\"publisher\":\"Closet Cooking\",\"f2f_url\":\"http://food2fork.com/view/35113\",\"title\":\"Bacon Jam Breakfast Sandwich with Fried Egg and Avocado\",\"source_url\":\"http://www.closetcooking.com/2012/12/bacon-jam-breakfast-sandwich-with-fried.html\",\"recipe_id\":\"35113\",\"image_url\":\"http://static.food2fork.com/Bacon2BJam2BBreakfast2BSandwich2Bwith2BFried2BEgg2Band2BAvocado2B5002B995922913bc2.jpg\",\"social_rank\":99.99999738945647,\"publisher_url\":\"http://closetcooking.com\"},{\"publisher\":\"Simply Recipes\",\"f2f_url\":\"http://food2fork.com/view/36965\",\"title\":\"Southern Cornbread\",\"source_url\":\"http://www.simplyrecipes.com/recipes/southern_cornbread/\",\"recipe_id\":\"36965\",\"image_url\":\"http://static.food2fork.com/southerncornbreadc300x200cb0df1c7.jpg\",\"social_rank\":99.99999569825427,\"publisher_url\":\"http://simplyrecipes.com\"},{\"publisher\":\"All Recipes\",\"f2f_url\":\"http://food2fork.com/view/26444\",\"title\":\"Red Skinned Potato Salad\",\"source_url\":\"http://allrecipes.com/Recipe/Red-Skinned-Potato-Salad/Detail.aspx\",\"recipe_id\":\"26444\",\"image_url\":\"http://static.food2fork.com/621452ffc3.jpg\",\"social_rank\":99.99999557918667,\"publisher_url\":\"http://allrecipes.com\"},{\"publisher\":\"BBC Good Food\",\"f2f_url\":\"http://food2fork.com/view/a43c5b\",\"title\":\"Dead good spaghetti carbonara\",\"source_url\":\"http://www.bbcgoodfood.com/recipes/4057/dead-good-spaghetti-carbonara\",\"recipe_id\":\"a43c5b\",\"image_url\":\"http://static.food2fork.com/4057_MEDIUMd3d0.jpg\",\"social_rank\":99.99999351015376,\"publisher_url\":\"http://www.bbcgoodfood.com\"},{\"publisher\":\"Jamie Oliver\",\"f2f_url\":\"http://food2fork.com/view/d7c241\",\"title\":\"Beautiful courgette carbonara\",\"source_url\":\"http://www.jamieoliver.com/recipes/pasta-recipes/beautiful-courgette-carbonara\",\"recipe_id\":\"d7c241\",\"image_url\":\"http://static.food2fork.com/67_1_1350894766_lrg2ca8.jpg\",\"social_rank\":99.9999879905975,\"publisher_url\":\"http://www.jamieoliver.com\"},{\"publisher\":\"All Recipes\",\"f2f_url\":\"http://food2fork.com/view/2226\",\"title\":\"Bacon Cheddar Deviled Eggs\",\"source_url\":\"http://allrecipes.com/Recipe/Bacon-Cheddar-Deviled-Eggs/Detail.aspx\",\"recipe_id\":\"2226\",\"image_url\":\"http://static.food2fork.com/9998886566.jpg\",\"social_rank\":99.99998209556264,\"publisher_url\":\"http://allrecipes.com\"},{\"publisher\":\"Two Peas and Their Pod\",\"f2f_url\":\"http://food2fork.com/view/54266\",\"title\":\"Loaded Baked Potato Frittata\",\"source_url\":\"http://www.twopeasandtheirpod.com/loaded-baked-potato-frittata/\",\"recipe_id\":\"54266\",\"image_url\":\"http://static.food2fork.com/LoadedBakedPotatoFrittata4727a.jpg\",\"social_rank\":99.99998181875522,\"publisher_url\":\"http://www.twopeasandtheirpod.com\"},{\"publisher\":\"Simply Recipes\",\"f2f_url\":\"http://food2fork.com/view/35846\",\"title\":\"Broccoli Cheese Casserole\",\"source_url\":\"http://www.simplyrecipes.com/recipes/broccoli_cheese_casserole/\",\"recipe_id\":\"35846\",\"image_url\":\"http://static.food2fork.com/broccolicheesecasserole300x200b7263562.jpg\",\"social_rank\":99.99997689411128,\"publisher_url\":\"http://simplyrecipes.com\"},{\"publisher\":\"All Recipes\",\"f2f_url\":\"http://food2fork.com/view/12666\",\"title\":\"Eggs Benedict\",\"source_url\":\"http://allrecipes.com/Recipe/Eggs-Benedict/Detail.aspx\",\"recipe_id\":\"12666\",\"image_url\":\"http://static.food2fork.com/520652a761.jpg\",\"social_rank\":99.99990816673203,\"publisher_url\":\"http://allrecipes.com\"},{\"publisher\":\"All Recipes\",\"f2f_url\":\"http://food2fork.com/view/11064\",\"title\":\"Dark Chocolate Bacon Cupcakes\",\"source_url\":\"http://allrecipes.com/Recipe/Dark-Chocolate-Bacon-Cupcakes/Detail.aspx\",\"recipe_id\":\"11064\",\"image_url\":\"http://static.food2fork.com/4569806186.jpg\",\"social_rank\":99.99990296622477,\"publisher_url\":\"http://allrecipes.com\"},{\"publisher\":\"Closet Cooking\",\"f2f_url\":\"http://food2fork.com/view/35117\",\"title\":\"Bacon Jam and Guacamole Quesadilla topped with a Fried Egg and Bacon Jam Vinaigrette Drizzle\",\"source_url\":\"http://www.closetcooking.com/2012/09/bacon-jam-and-guacamole-quesadilla.html\",\"recipe_id\":\"35117\",\"image_url\":\"http://static.food2fork.com/Bacon2BJam2Band2BGuacamole2BQuesadilla2B5002B4126ad3d60a1.jpg\",\"social_rank\":99.99987666835922,\"publisher_url\":\"http://closetcooking.com\"},{\"publisher\":\"The Pioneer Woman\",\"f2f_url\":\"http://food2fork.com/view/47226\",\"title\":\"Spinach Salad with Warm Bacon Dressing\",\"source_url\":\"http://thepioneerwoman.com/cooking/2009/06/the-best-spinach-salad-ever/\",\"recipe_id\":\"47226\",\"image_url\":\"http://static.food2fork.com/3611769621_e169fe9fbb8f1c.jpg\",\"social_rank\":99.99983421868475,\"publisher_url\":\"http://thepioneerwoman.com\"},{\"publisher\":\"Simply Recipes\",\"f2f_url\":\"http://food2fork.com/view/36967\",\"title\":\"Spaghetti alla Carbonara\",\"source_url\":\"http://www.simplyrecipes.com/recipes/spaghetti_alla_carbonara/\",\"recipe_id\":\"36967\",\"image_url\":\"http://static.food2fork.com/spaghettipastacarbonaraa300x2008bf99e00.jpg\",\"social_rank\":99.99980489611534,\"publisher_url\":\"http://simplyrecipes.com\"}]}";
//        new ResponseParser(recipes).execute(staticTestData);
    }
    //Asynchronous JSON parser, automatically adds recipe items to a buffer list
    private class ResponseParser extends AsyncTask<String, Void, Void>{
        List<RecipeItem> recipes, buffer;

        public ResponseParser(List<RecipeItem> recipes, List<RecipeItem> buffer){
            this.recipes = recipes;
            this.buffer = buffer;
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
                    buffer.add(recipe);
                }
            }catch(JSONException jse){
                jse.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            if(buffer.size() == 0) {
                callback.onFinishedLoading(0);
            }
            if(recipes.size() == 0 && buffer.size() != 0) {
                requestRecipes();
            }
        }
    }

    //Callback interface
    public interface RecipeSearcherCallback{
        public void onFinishedLoading(int loaded);
    }
}

