package com.example.eduar.foodtruckspr;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.UUID;

public class User {

    private UUID id;
    private static User user;
    private ArrayList<FoodTruck> favorites;
    private FoodTruck myTruck;
    public boolean created;
    public boolean signedIn;

    public static User get(){
        if(user == null){
            user = new User();
        }
        return user;
    }

    public User(){
        this.id = UUID.randomUUID();
        this.created = false;
        this.signedIn = false;
        myTruck = new FoodTruck("", new LatLng(18.4444, -67.5555), this);
        OpenHour op = new OpenHour();
        initializeOpenHours(op,1,3,4,2,6,2,0,1);
        myTruck.getOpenHours().add(op);
        favorites = new ArrayList<FoodTruck>();
        favorites.add(new FoodTruck("TikiTacos", new LatLng(18.12345, -67.23156), this));
        favorites.add(new FoodTruck("Calmaos", new LatLng(18.12345, -67.23421), this));
        favorites.add(new FoodTruck("La Esquina", new LatLng(18.12345, -67.21456), this));
        favorites.add(new FoodTruck("Parada 51", new LatLng(18.12345, -67.23056), this));
        favorites.add(new FoodTruck("Test", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("Tests", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("Test", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("Test", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("Test", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("Test", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("Test", new LatLng(18.12345, -67.23456), this));
        favorites.add(new FoodTruck("Test", new LatLng(18.12345, -67.23456), this));

        favorites.get(0).setPhone("9392475830");
        favorites.get(0).getOpenHours().add(op);
        FoodItem a = new FoodItem(); a.setName("Alcapurria"); a.setPrice("2.99");
        FoodItem b = new FoodItem(); b.setName("Empanadas"); b.setPrice("3.25");
        FoodItem c = new FoodItem(); c.setName("Sandwiches"); c.setPrice("2.99");
        FoodItem d = new FoodItem(); d.setName("Hamburguesas"); d.setPrice("5.00");
        favorites.get(0).getMenu().add(a);
        favorites.get(0).getMenu().add(b);
        favorites.get(0).getMenu().add(c);
        favorites.get(0).getMenu().add(d);
        favorites.get(0).setCategory("Mexican");
        favorites.get(1).setCategory("Mexican");
        favorites.get(2).setCategory("Mexican");
        favorites.get(3).setCategory("Mexican");
        favorites.get(4).setCategory("Mexican");

        for(FoodTruck f: favorites){
            FoodTruckDatabase.get().addFoodTruck(f);
        }
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
