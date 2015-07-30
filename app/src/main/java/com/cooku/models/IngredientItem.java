package com.cooku.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Huang Xue on 7/25/2015.
 */
public class IngredientItem{
    private String name;
    private boolean checked;
    public IngredientItem(String name, boolean checked){
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
