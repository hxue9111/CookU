package com.cooku;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailsFragment.OnItemClickListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailsFragment extends Fragment
        implements AdapterView.OnItemClickListener{

    private static final String URL = "www.ilovefood.com";
    private String url;
    private OnItemClickListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url Parameter 1.
     * @return A new instance of fragment RecipeDetailsFragment.
     */
    public static RecipeDetailsFragment newInstance(String url) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* Set the reciple details page to fixed site */
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        WebView myWebView = (WebView) rootView.findViewById(R.id.recipe_details);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.loadUrl(URL);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        view.setVisibility(View.VISIBLE);// Show loading animation

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.recipe_search_view, RecipeDetailsFragment.newInstance("www.ilovefood.com"));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String url) {
        if (mListener != null) {
            mListener.onItemClick();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnItemClickListener) activity;
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

    public interface OnItemClickListener {
        public void onItemClick();
    }

}
