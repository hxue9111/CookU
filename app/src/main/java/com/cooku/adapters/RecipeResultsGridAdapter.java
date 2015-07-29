package com.cooku.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cooku.models.RecipeItem;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Huang Xue on 7/27/2015.
 */
public class RecipeResultsGridAdapter extends BaseAdapter{
    private Context context;
    private List<RecipeItem> recipes;
    public RecipeResultsGridAdapter(Context c, List<RecipeItem> recipes){
        this.context = c;
        this.recipes = recipes;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }


        return imageView;
    }
    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
