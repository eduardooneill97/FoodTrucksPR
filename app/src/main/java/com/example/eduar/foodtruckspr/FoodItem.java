package com.example.eduar.foodtruckspr;

import android.media.Image;

import java.util.UUID;

public class FoodItem {

    private UUID id;
    private String name;
    private Image image;
    private String price;

    @Override
    public boolean equals(Object fi){
        try{
            FoodItem that = (FoodItem) fi;
            if(this.name.equals(that.name)) return true;
            else return false;
        } catch (Exception e){
            return false;
        }
    }
}
