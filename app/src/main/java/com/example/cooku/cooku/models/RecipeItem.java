package com.example.cooku.cooku.models;

/**
 * Created by Huang Xue on 7/27/2015.
 */
public class RecipeItem {
    private String name, URL, id;
    public RecipeItem(String name, String URL, String id) {
        this.name = name;
        this.URL = URL;
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public String getURL(){
        return URL;
    }

    public String getId(){
        return id;
    }
}
