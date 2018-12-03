package com.example.eduar.foodtruckspr;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class FoodTruckDatabase {

    private static FoodTruckDatabase ftd;

    private ArrayList<FoodTruck> foodTrucks;
    private ArrayList<FoodItem> foods;
    private HashMap<FoodItem, ArrayList<FoodTruck>> foodAvailability;

    public static FoodTruckDatabase get(){
        if(ftd == null){
            ftd = new FoodTruckDatabase();
            //TODO Add a lot of placeholder food trucks;
        }

        return ftd;
    }

    public FoodTruckDatabase(){
        this.foodTrucks = new ArrayList<FoodTruck>();
        this.foods = new ArrayList<>();
        this.foodAvailability = new HashMap<>();

        FoodTruck ft = new FoodTruck("TikiTacos", new LatLng(18.5555,-67.4433), new User());
        FoodItem a = new FoodItem(); a.setName("Alcapurria");
        FoodItem b = new FoodItem(); b.setName("Empanadas");
        FoodItem c = new FoodItem(); c.setName("Sandwiches");
        FoodItem d = new FoodItem(); d.setName("Hamburguesas");
        ft.getMenu().add(a);
        ft.getMenu().add(b);
        ft.getMenu().add(c);
        ft.getMenu().add(d);

        this.addFoodTruck(ft);
    }

    public void addFoodTruck(FoodTruck ft){
        foodTrucks.add(ft);
        ArrayList<FoodItem> list = ft.getMenu();
        for(FoodItem f: list){
            if(foodAvailability.containsKey(f)){
                if(!foodAvailability.get(f).contains(ft)) {
                    foodAvailability.get(f).add(ft);
                }
            } else{
                ArrayList<FoodTruck> l = new ArrayList<FoodTruck>();
                l.add(ft);
                foodAvailability.put(f, l);
                foods.add(f);
            }
        }
    }

    public void removeFoodTruck(FoodTruck ft){
        foodTrucks.remove(ft);
        ArrayList<FoodItem> items = ft.getMenu();
        for(FoodItem f: items){
            if(foodAvailability.containsKey(f)){
                foodAvailability.get(f).remove(ft);
            }
            if(foodAvailability.get(f).size() == 0) {
                foodAvailability.remove(f);
                foods.remove(f);
            }
        }
    }

    public ArrayList<FoodItem> getAvailableFoods(){
        return foods;
    }

}
