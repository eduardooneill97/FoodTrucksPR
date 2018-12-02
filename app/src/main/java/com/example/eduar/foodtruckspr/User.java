package com.example.eduar.foodtruckspr;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class User {

    private static User user;
    private ArrayList<FoodTruck> favorites;

    public static User get(){
        if(user == null){
            user = new User();
        }
        return user;
    }

    public User(){
        favorites = new ArrayList<FoodTruck>();
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("BitchTacos", new LatLng(18.12345, -67.23456), this));
    }

    public ArrayList<FoodTruck> getFavorites() {
        return favorites;
    }
}
