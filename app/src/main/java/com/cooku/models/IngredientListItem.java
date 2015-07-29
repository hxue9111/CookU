package com.cooku.models;

/**
 * Created by Huang Xue on 7/25/2015.
 */
public class IngredientListItem {
    private String name;
    private boolean checked;
    public IngredientListItem(String name, boolean checked){
        this.name = name;
        this.checked = checked;
    }
    public String getName(){
        return name;
    }
    public boolean isChecked(){
        return checked;
    }
}
