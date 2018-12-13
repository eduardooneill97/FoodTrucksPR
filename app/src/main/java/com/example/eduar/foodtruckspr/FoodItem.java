package com.example.eduar.foodtruckspr;

import android.media.Image;

import java.util.UUID;

public class FoodItem {

    private UUID id;
    private String name;
    private String price;

    public FoodItem(){
        id = UUID.randomUUID();
        name = "";
        price = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object fi){
        try{
            FoodItem that = (FoodItem) fi;
            if(this.id.equals(that.id)) return true;
            else return false;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public String toString(){
        return name.toLowerCase();
    }
}
