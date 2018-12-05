package com.example.eduar.foodtruckspr;

import android.media.Image;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class FoodTruck {

    private UUID id;
    private String name;
    private ArrayList<OpenHour> openHours;
    private ArrayList<FoodItem> menu;
    private LatLng location;
    private String image;
    private String phone;
    public User owner;

    public FoodTruck(String name, LatLng location, User owner){
        this.name = name;
        this.id = UUID.randomUUID();
        this.location = location;
        this.owner = owner;
        this.openHours = new ArrayList<OpenHour>();
        this.phone = "None";
        this.image = null;
        this.menu = new ArrayList<FoodItem>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMenuItem(FoodItem item){
        menu.add(item);
    }

    public void removeMenuItem(FoodItem item){
        menu.remove(item);
    }

    public ArrayList<OpenHour> getOpenHours(){return openHours;}

    public ArrayList<FoodItem> getMenu() {
        return menu;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage() {
        this.image = "IMG_"+id.toString()+".jpg";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object ft){
        try{
            FoodTruck that = (FoodTruck) ft;
            if(this.id.equals(that.id)) return true;
            else return false;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public String toString(){
        return this.name;
    }
}
