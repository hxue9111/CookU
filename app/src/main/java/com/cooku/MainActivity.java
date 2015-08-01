package com.cooku;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cooku.models.IngredientItem;

import java.util.List;

public class MainActivity extends ActionBarActivity
    implements RecipeSearchFragment.OnFragmentInteractionListener,
                RecipeDetailsFragment.OnFragmentInteractionListener,
                RecipeListFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        String viewType = sharedPrefs.getString(
                getString(R.string.pref_view_key),
                getString(R.string.pref_view_pin));

//        Log.w("viewType", viewType);


//        Fragment newFragment = new RecipeListFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.recipe_search_fragment, newFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchTrigger(String[] ingredients){
        findViewById(R.id.loading_animation).setVisibility(View.VISIBLE);// Show loading animation


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.recipe_search_view, RecipeListFragment.newInstance(ingredients));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void onFragmentInteraction(Uri uri){
    }
}
