package com.cooku;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

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
 * {@link RecipeListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeListFragment extends Fragment implements RecipeSearcher.RecipeSearcherCallback {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_INGREDIENTS = "ingredients";
    private RecipeSearcher searcher;
    private List<RecipeItem> recipes;
    private OnFragmentInteractionListener mListener;
    private BaseAdapter viewAdapter;
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
            searcher.requestRecipes();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
//        /*  Hard code test data */
//        ArrayList<RecipeItem> list = new ArrayList<RecipeItem>();
//        list.add(new RecipeItem("Mac n Cheese", "", "", ""));
//        list.add(new RecipeItem("Creme Brule", "", "", ""));
//        list.add(new RecipeItem("Berry Lemonade", "", "", ""));
//        list.add(new RecipeItem("Pad Thai", "", "", ""));
//        list.add(new RecipeItem("Mac n Cheese", "", "", ""));
//        list.add(new RecipeItem("Creme Brule", "", "", ""));
//        list.add(new RecipeItem("Berry Lemonade", "", "", ""));
//        list.add(new RecipeItem("Pad Thai", "", "", ""));
//        /* Hard coded recipe items with only images*/
//        List<RecipeItem> fakedata = new ArrayList<RecipeItem>();
//        fakedata.add(new RecipeItem("","http://i.imgur.com/ZLixWFT.jpg","",""));
//        fakedata.add(new RecipeItem("", "http://i.imgur.com/ZSgMm0u.jpg", "", ""));
//        fakedata.add(new RecipeItem("", "http://i.imgur.com/TWaUGEi.jpg", "", ""));
//        fakedata.add(new RecipeItem("", "http://i.imgur.com/JRWFumf.jpg", "", ""));

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

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Create new fragment and transaction
                    Fragment newFragment = new RecipeDetailsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.recipe_search_fragment, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }else {
            view = inflater.inflate(R.layout.fragment_recipe_list_grid,container,false);
            viewAdapter = new RecipeResultsGridAdapter(getActivity(), recipes);
            GridView gv = (GridView) view.findViewById(R.id.search_results_grid_view);
            gv.setAdapter(viewAdapter);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Toast.makeText(getActivity(), "Will this print grid?", Toast.LENGTH_SHORT).show();
                    Fragment newFragment = new RecipeDetailsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.recipe_search_fragment, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }




        return view;
    }
    @Override
    public void onFinishedLoading(){
        getActivity().findViewById(R.id.loading_animation).setVisibility(View.GONE);
        viewAdapter.notifyDataSetChanged();
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public void onStart() {
        super.onResume();
        // Set title
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setTitle(R.string.recipe_search_title);
        ab.setDisplayHomeAsUpEnabled(true);
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
