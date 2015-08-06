package com.cooku.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cooku.models.RecipeItem;
import com.squareup.picasso.*;

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
            imageView = new SquaredImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        String image_url = ((RecipeItem) getItem(position)).getImageUrl();

        Picasso.with(context)
                .load(image_url)
                .fit().centerCrop()
                .tag(context)
                .into(imageView);

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

    /** An image view which always remains square with respect to its width. */
    public final class SquaredImageView extends ImageView {
        public SquaredImageView(Context context) {
            super(context);
        }

        public SquaredImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        }
    }
}
