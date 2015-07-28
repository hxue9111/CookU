package com.cooku.models;

/**
 * Created by Huang Xue on 7/27/2015.
 */
public class RecipeItem {
    private String name, image_url, source_url, recipe_id;
    public RecipeItem(String name, String image_url,String source_url, String recipe_id) {
        this.name = name;
        this.image_url = image_url;
        this.source_url = source_url;
        this.recipe_id = recipe_id;
    }

    public String getName(){
        return name;
    }
    public String getImageUrl(){
        return image_url;
    }

    public String getSourceUrl(){
        return source_url;
    }

    public String getRecipeId(){
        return recipe_id;
    }
}
