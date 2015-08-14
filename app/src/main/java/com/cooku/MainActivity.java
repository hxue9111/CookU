package com.cooku;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cooku.models.RecipeItem;

public class MainActivity extends AppCompatActivity
    implements RecipeSearchFragment.OnFragmentInteractionListener,
                RecipeDetailsFragment.OnFragmentInteractionListener,
        RecipeListFragment.OnRecipeClickListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, RecipeSearchFragment.newInstance());
        transaction.commit();
//        if (findViewById(R.id.recipe_detail_container) != null) {
//            // The detail container view will be present only in the large-screen layouts
//            // (res/layout-sw600dp). If this view is present, then the activity should be
//            // in two-pane mode.
//            mTwoPane = true;
//            // In two-pane mode, show the detail view in this activity by
//            // adding or replacing the detail fragment using a
//            // fragment transaction.
//            if (savedInstanceState == null) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.recipe_detail_container, new RecipeDetailsFragment(null), "Random tag")
//                        .commit();
//            } else {
//                mTwoPane = false;
//            }
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            startActivity(new Intent(this, SettingsActivity.class));
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchTrigger(String[] ingredients){
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        //TODO: also load first search result webview
        if (isTablet)
        {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, RecipeListFragment.newInstance(ingredients));
            transaction.addToBackStack(null);
            transaction.commit();

        }
        else { //Phone view
            this.toggleLoadAnimation(View.VISIBLE);// Show loading animation
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, RecipeListFragment.newInstance(ingredients));
            transaction.addToBackStack(null);
            transaction.commit();
        }


    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    public void onFragmentInteraction(Uri uri){
    }

    @Override
    public void onRecipeClick(RecipeItem recipe) {


        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        boolean gridView = sharedPrefs.getBoolean(getString(R.string.pref_view_key), true);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        this.toggleLoadAnimation(View.VISIBLE);// Show loading animation

        if (!isTablet || gridView) { //Phone view, click to Details view
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.recipe_search_view, RecipeDetailsFragment.newInstance(recipe));
            transaction.addToBackStack(null);
            transaction.commit();
        }
        // TODO: Tablet view, update the webview, do not click to details view
        else
        {

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.recipe_details, RecipeDetailsFragment.newInstance(recipe));
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    public void toggleLoadAnimation(int visible){ //View.Gone or View.Visisble
        this.findViewById(R.id.loading_animation).setVisibility(visible);
    }
}
