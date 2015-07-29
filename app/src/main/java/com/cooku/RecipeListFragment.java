package com.cooku;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.cooku.adapters.RecipeResultsGridAdapter;
import com.cooku.adapters.RecipeResultsListAdapter;
import com.cooku.models.RecipeItem;
import com.cooku.models.RecipeResultsListItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeListFragment newInstance(String param1, String param2) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        return fragment;
    }

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //if (preferences.listview == listview ) LIST VIEW
        /*  Hard code test data */
        View view =  inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ArrayList<RecipeResultsListItem> list = new ArrayList<RecipeResultsListItem>();
        list.add(new RecipeResultsListItem("Mac n Cheese"));
        list.add(new RecipeResultsListItem("Creme Brule"));
        list.add(new RecipeResultsListItem("Berry Lemonade"));
        list.add(new RecipeResultsListItem("Pad Thai"));
        list.add(new RecipeResultsListItem("Mac n Cheese"));
        list.add(new RecipeResultsListItem("Creme Brule"));
        list.add(new RecipeResultsListItem("Berry Lemonade"));
        list.add(new RecipeResultsListItem("Pad Thai"));

        RecipeResultsListAdapter listSearchAdapter = new RecipeResultsListAdapter(getActivity(), list);
        ListView listView = (ListView) view.findViewById(R.id.search_results_list_view);
        listView.setAdapter(listSearchAdapter);
        //else
        //GRID VIEW
        /* Hard coded recipe items with only images*/
        view = inflater.inflate(R.layout.fragment_recipe_list_grid,container,false);
        List<RecipeItem> fakedata = new ArrayList<RecipeItem>();
        fakedata.add(new RecipeItem("","http://i.imgur.com/ZLixWFT.jpg","",""));
        fakedata.add(new RecipeItem("", "http://i.imgur.com/ZSgMm0u.jpg", "", ""));
        fakedata.add(new RecipeItem("", "http://i.imgur.com/TWaUGEi.jpg", "", ""));
        fakedata.add(new RecipeItem("", "http://i.imgur.com/JRWFumf.jpg", "", ""));
        RecipeResultsGridAdapter adapter = new RecipeResultsGridAdapter(getActivity(), fakedata);
        GridView gv = (GridView) view.findViewById(R.id.search_results_grid_view);
        gv.setAdapter(adapter);

        return view;
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
