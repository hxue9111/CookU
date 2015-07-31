package com.cooku;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.cooku.adapters.IngredientListAdapter;
import com.cooku.models.IngredientItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeSearchFragment extends Fragment implements View.OnClickListener{

    /*Carries instance of parent Activity, used to do callbacks to parent*/
    private OnFragmentInteractionListener mListener;
    /*List of ingredients (FOR TESTING)*/
    private ArrayList<IngredientItem> ingredients = new ArrayList<IngredientItem>();
    private Context mContext;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeSearchFragment.
     */
    public static RecipeSearchFragment newInstance() {
        RecipeSearchFragment fragment = new RecipeSearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_search, container, false);

        /*Test Data*/
        ingredients.add(new IngredientItem("Brocolli",true));
        ingredients.add(new IngredientItem("Carrot",false));
        ingredients.add(new IngredientItem("Rice",true));
        ingredients.add(new IngredientItem("Chicken",true));
        ingredients.add(new IngredientItem("Beef",false));
        ingredients.add(new IngredientItem("Cheese",true));
        ingredients.add(new IngredientItem("Brocolli",true));
        ingredients.add(new IngredientItem("Carrot",false));
        ingredients.add(new IngredientItem("Rice",true));
        ingredients.add(new IngredientItem("Chicken",true));
        ingredients.add(new IngredientItem("Beef",false));
        ingredients.add(new IngredientItem("Cheese",true));
        ingredients.add(new IngredientItem("Brocolli",true));
        ingredients.add(new IngredientItem("Carrot",false));
        ingredients.add(new IngredientItem("Rice",true));
        ingredients.add(new IngredientItem("Chicken",true));
        ingredients.add(new IngredientItem("Beef",false));
        ingredients.add(new IngredientItem("Cheese",true));

        IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(getActivity(), null, 0);
        /*Attach adapter to the listView*/
        ListView listView = (ListView) view.findViewById(R.id.ingredient_list_view);
        listView.setAdapter(ingredientListAdapter);

        /*Attach onClickListener to the search button*/
        Button searchButton = (Button) view.findViewById(R.id.search_recipes_button);
        searchButton.setOnClickListener(this);

        return view;
    }

    /*The following method sets up the buttons within this fragment*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_recipes_button:
                mListener.onSearchTrigger(ingredients);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onSearchTrigger(List<IngredientItem> ingredients);
    }
    //TODO: fix this to create cursor loader
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // The CursorAdapter will take data from our cursor and populate the ListView.
//        mForecastAdapter = new ForecastAdapter(getActivity(), null, 0);
//
//        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//
//        // Get a reference to the ListView, and attach this adapter to it.
//        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
//        listView.setAdapter(mForecastAdapter);
//
//        return rootView;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        getLoaderManager().initLoader(FORECAST_LOADER, null, this);
//        super.onActivityCreated(savedInstanceState);
//    }
//
//    private void updateWeather() {
//        FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity());
//        String location = Utility.getPreferredLocation(getActivity());
//        weatherTask.execute(location);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        updateWeather();
//    }
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        String locationSetting = Utility.getPreferredLocation(getActivity());
//
//        // Sort order:  Ascending, by date.
//        String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
//        Uri weatherForLocationUri = WeatherContract.WeatherEntry.buildWeatherLocationWithStartDate(
//                locationSetting, System.currentTimeMillis());
//
//        return new CursorLoader(getActivity(),
//                weatherForLocationUri,
//                null,
//                null,
//                null,
//                sortOrder);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//        mForecastAdapter.swapCursor(cursor);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//        mForecastAdapter.swapCursor(null);
//    }
}
