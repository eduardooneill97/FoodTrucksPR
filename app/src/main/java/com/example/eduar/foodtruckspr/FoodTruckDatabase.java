package com.example.eduar.foodtruckspr;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class FoodTruckDatabase {

    private static FoodTruckDatabase ftd;

    private ArrayList<FoodTruck> foodTrucks;
    private ArrayList<String> foodNames;
    private ArrayList<String> truckNames;
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
        this.foodNames = new ArrayList<>();
        this.truckNames = new ArrayList<>();

        FoodTruck ft = new FoodTruck("TikiTacos", new LatLng(18.5555,-67.4433), new User());
        FoodItem a = new FoodItem(); a.setName("Alcapurria"); a.setPrice("2.99");
        FoodItem b = new FoodItem(); b.setName("Empanadas"); b.setPrice("3.25");
        FoodItem c = new FoodItem(); c.setName("Sandwiches"); c.setPrice("2.99");
        FoodItem d = new FoodItem(); d.setName("Hamburguesas"); d.setPrice("5.00");
        ft.getMenu().add(a);
        ft.getMenu().add(b);
        ft.getMenu().add(c);
        ft.getMenu().add(d);

        this.addFoodTruck(ft);

        this.addFoodTruck(new FoodTruck("TikiTacos", new LatLng(18.12345, -67.23456), User.get()));
        this.addFoodTruck(new FoodTruck("Calmaos", new LatLng(18.12345, -67.23816), User.get()));
        this.addFoodTruck(new FoodTruck("La Esquina", new LatLng(18.12145, -67.23256), User.get()));
        this.addFoodTruck(new FoodTruck("Parada 51", new LatLng(18.12945, -67.23456), User.get()));
        this.addFoodTruck(new FoodTruck("Test", new LatLng(18.12045, -67.23454), User.get()));
        this.addFoodTruck(new FoodTruck("Tests", new LatLng(18.12345, -67.13456), User.get()));
        this.addFoodTruck(new FoodTruck("Test", new LatLng(18.22345, -67.23466), User.get()));
    }

    public void addFoodTruck(FoodTruck ft){
        foodTrucks.add(ft);
        ArrayList<FoodItem> list = ft.getMenu();
        for(FoodItem f: list){
            if(foodAvailability.containsKey(f)){
                if(!foodAvailability.get(f).contains(ft)) {
                    foodAvailability.get(f).add(ft);
                    truckNames.add(ft.toString());
                }
            } else{
                ArrayList<FoodTruck> l = new ArrayList<FoodTruck>();
                l.add(ft);
                foodAvailability.put(f, l);
                foods.add(f);
                foodNames.add(f.toString());
                truckNames.add(ft.toString());
            }
        }
    }

    public void removeFoodTruck(FoodTruck ft){
        foodTrucks.remove(ft);
        ArrayList<FoodItem> items = ft.getMenu();
        for(FoodItem f: items){
            if(foodAvailability.containsKey(f)){
                foodAvailability.get(f).remove(ft);
                truckNames.remove(ft.toString());
            }
            if(foodAvailability.get(f) != null && foodAvailability.get(f).size() == 0) {
                foodAvailability.remove(f);
                foods.remove(f);
                foodNames.remove(f.toString());
                truckNames.remove(ft.toString());
            }
        }
    }

    public ArrayList<FoodItem> getAvailableFoods(){
        return foods;
    }

    public ArrayList<String> getFoodNames() {
        return foodNames;
    }

    public HashMap<FoodItem, ArrayList<FoodTruck>> getFoodAvailability() {
        return foodAvailability;
    }

    public ArrayList<String> getTruckNames() {
        return truckNames;
    }

    public ArrayList<FoodTruck> getFoodTrucks() {
        return foodTrucks;
    }
}
