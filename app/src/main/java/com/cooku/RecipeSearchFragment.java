package com.cooku;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cooku.adapters.IngredientListAdapter;
import com.cooku.data.RecipeContract;
import com.cooku.tasks.AddIngredientTask;
import com.cooku.tasks.GetSelectedIngredientsTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeSearchFragment extends Fragment
        implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor>,
        TextView.OnEditorActionListener, GetSelectedIngredientsTask.Listener{

    /*Carries instance of parent Activity, used to do callbacks to parent*/
    private OnFragmentInteractionListener mListener;

    private static final int INGREDIENT_LOADER = 0;
    IngredientListAdapter mIngredientListAdapter;
    /*View for this fragment*/
    private View view;
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
        view = inflater.inflate(R.layout.fragment_recipe_search, container, false);

        mIngredientListAdapter  = new IngredientListAdapter(getActivity(),null, 0);
        /*Attach adapter to the listView*/
        ListView listView = (ListView) view.findViewById(R.id.ingredient_list_view);
        listView.setAdapter(mIngredientListAdapter);
        /*Attach onClickListener to the add ingredient button*/
        ImageButton addIngredient = (ImageButton) view.findViewById(R.id.ingredient_add);
        addIngredient.setOnClickListener(this);
        /*Attach onEditorAction to the EditText field*/
        TextView ingredientField = (EditText) view.findViewById(R.id.enter_ingredient_field);
        ingredientField.setOnEditorActionListener(this);
        /*Attach onClickListener to the search button*/
        Button searchButton = (Button) view.findViewById(R.id.search_recipes_button);
        searchButton.setOnClickListener(this);


        return view;
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if ((actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_GO) && v.getText().length() > 0) {
            /*Adds ingredient to DB*/
            addIngredient();
            /*Closes keyboard after enter*/
            closeKeyboard(v);

            handled = true;
        }
        return handled;
    }
    /*Adds ingredient to DB*/
    private void addIngredient(){
        TextView ingredientField = (EditText) view.findViewById(R.id.enter_ingredient_field);
        String ingredient = ingredientField.getText().toString();
        AsyncTask task = new AddIngredientTask(getActivity());
        task.execute(ingredient);
        ingredientField.setText("");

        Toast.makeText(getActivity(),ingredient, Toast.LENGTH_SHORT).show();
    }
    /*The following method sets up the buttons within this fragment*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ingredient_add:
                addIngredient();
                break;
            case R.id.search_recipes_button:
                closeKeyboard(v);
                AsyncTask selectedIngredientTask = new GetSelectedIngredientsTask(getActivity(),this);
                selectedIngredientTask.execute();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(INGREDIENT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setTitle(R.string.app_name);
        ab.setDisplayHomeAsUpEnabled(false);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri ingredientUri = RecipeContract.IngredientEntry.getAllIngredientsURI();
        return new CursorLoader(getActivity(),ingredientUri,null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mIngredientListAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mIngredientListAdapter.swapCursor(null);
    }
    public void closeKeyboard(View v){
        /*Closes keyboard*/
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onIngredientsRecieved(String[] ingredients) {
        mListener.onSearchTrigger(ingredients);

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
        public void onSearchTrigger(String[] ingredients);
    }

}
