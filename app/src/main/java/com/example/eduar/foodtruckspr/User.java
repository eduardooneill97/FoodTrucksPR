package com.example.eduar.foodtruckspr;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.UUID;

public class User {

    private UUID id;
    private static User user;
    private ArrayList<FoodTruck> favorites;
    private FoodTruck myTruck;

    public static User get(){
        if(user == null){
            user = new User();
        }
        return user;
    }

    public User(){
        this.id = UUID.randomUUID();
        myTruck = new FoodTruck("BitchTacos", new LatLng(18.4444, -67.5555), this);
        OpenHour op = new OpenHour();
        initializeOpenHours(op,1,3,4,2,6,2,0,1);
        myTruck.getOpenHours().add(op);
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

    public FoodTruck getMyTruck() {
        return myTruck;
    }

    public void setMyTruck(FoodTruck myTruck) {
        this.myTruck = myTruck;
    }

    public void initializeOpenHours(OpenHour op, int fd, int td, int fh, int th, int fm, int tm, int fap, int tap){
        op.setFromHour(fh);
        op.setFromDay(fd);
        op.setFromMinute(fm);
        op.setFromAPM(fap);
        op.setToDay(td);
        op.setToHour(th);
        op.setToMinute(tm);
        op.setToAPM(tap);
        op.setInitialized(true);
    }
}
