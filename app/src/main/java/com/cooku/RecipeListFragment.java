package com.cooku;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.cooku.adapters.RecipeResultsGridAdapter;
import com.cooku.adapters.RecipeResultsListAdapter;
import com.cooku.data.RecipeSearcher;
import com.cooku.models.RecipeItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link android.widget.AdapterView.OnItemClickListener} interface
 * to handle interaction events.
 * Use the {@link RecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeListFragment extends Fragment
        implements RecipeSearcher.RecipeSearcherCallback,
        AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_INGREDIENTS = "ingredients";
    private RecipeSearcher searcher;
    private List<RecipeItem> recipes;
    private OnRecipeClickListener mListener;
    private BaseAdapter viewAdapter;
    private View view;
    private boolean loading;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeListFragment newInstance(String[] ingredients) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM_INGREDIENTS, ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipes = Collections.synchronizedList(new ArrayList<RecipeItem>());
            searcher = new RecipeSearcher(getArguments().getStringArray(ARG_PARAM_INGREDIENTS),recipes,this);
            loading = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String viewType = sharedPrefs.getString(
                getString(R.string.pref_view_key),
                getString(R.string.pref_view_pin));
        if(viewType.equals(getString(R.string.pref_view_list))){
            view =  inflater.inflate(R.layout.fragment_recipe_list, container, false);
            viewAdapter = new RecipeResultsListAdapter(getActivity(), recipes);
            ListView listView = (ListView) view.findViewById(R.id.search_results_list_view);
            listView.setAdapter(viewAdapter);
            listView.setOnItemClickListener(this);
            listView.setOnScrollListener(this);
        }else {
            view = inflater.inflate(R.layout.fragment_recipe_list_grid,container,false);
            viewAdapter = new RecipeResultsGridAdapter(getActivity(), recipes);
            GridView gv = (GridView) view.findViewById(R.id.search_results_grid_view);
            gv.setAdapter(viewAdapter);
            gv.setOnItemClickListener(this);
            gv.setOnScrollListener(this);
        }
        return view;
    }
    @Override
    public void onFinishedLoading(){
        MainActivity mainActivity = ((MainActivity)getActivity());
        if(mainActivity != null) {
            mainActivity.toggleLoadAnimation(View.GONE);
            viewAdapter.notifyDataSetChanged();
            loading = false;
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRecipeClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        ((MainActivity)getActivity()).toggleLoadAnimation(View.GONE);
    }
    @Override
    public void onStart() {
        super.onResume();
        // Set title
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setTitle(R.string.recipe_search_title);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        view.setVisibility(View.VISIBLE);// Show loading animation

//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.recipe_search_view, RecipeDetailsFragment.newInstance("www.ilovefood.com"));
//        transaction.addToBackStack(null);
//        transaction.commit();

        /* Call the fragment transaction in onRecipeClick() in MainActivity */
        RecipeItem item = (RecipeItem) adapterView.getAdapter().getItem(position);
        mListener.onRecipeClick(item);
    }
    @Override
    public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount){
        int BUFFER = 3; //Load more recipes when BUFFER items left

        if(firstVisibleItem + visibleItemCount >= totalItemCount - BUFFER){
            if(!loading) { //Only do requests when there is not already a request loading
                loading = true;
                searcher.requestRecipes();
            }
        }
    }
    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollstate){

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
    public interface OnRecipeClickListener {
        public void onRecipeClick(RecipeItem recipe);
    }


}
