package com.example.eduar.foodtruckspr;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TruckDetailFragment extends Fragment {

    private FoodTruck foodTruck;
    private TextView name;
    private TextView phone;
    private RecyclerView hoursRV;
    private RecyclerView menuRV;
    private HoursRVAdapter adapterH;
    private MenuRVAdapter adapterM;
    private boolean isFavorite;

    private void setFoodTruck(FoodTruck ft){foodTruck = ft;}
    private void setFavorite(boolean f){isFavorite = f;}

    public TruckDetailFragment() {
        // Required empty public constructor
    }

    public static TruckDetailFragment newInstance(FoodTruck ft, boolean isFav){
        TruckDetailFragment tdf = new TruckDetailFragment();
        tdf.setFoodTruck(ft);
        tdf.setFavorite(isFav);
        return tdf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        adapterH = new HoursRVAdapter();
        adapterM = new MenuRVAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_truck_detail, container, false);

        setHasOptionsMenu(true);
        name = v.findViewById(R.id.name);
        phone = v.findViewById(R.id.phone);
        hoursRV = v.findViewById(R.id.hours_recycler_view);
        menuRV = v.findViewById(R.id.menu_items_recycler_view);

        hoursRV.setLayoutManager(new LinearLayoutManager(getContext()));
        hoursRV.setAdapter(adapterH);

        menuRV.setLayoutManager(new LinearLayoutManager(getContext()));
        menuRV.setAdapter(adapterM);

        name.setText(foodTruck.getName());
        String p = "Phone: "+foodTruck.getPhone();
        phone.setText(p);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        if(isFavorite)
            inflater.inflate(R.menu.empty_menu, menu);
        else {
            inflater.inflate(R.menu.truck_detail_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add_to_favorites:
                Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                FoodTruckDatabase.get().addFoodTruck(foodTruck);
                return true;
            case R.id.remove_favorites:
                AlertDialog.Builder builde = new AlertDialog.Builder(getActivity());
                builde.setMessage("Are you sure you want to remove from favorites?");
                builde.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
                        FoodTruckDatabase.get().removeFoodTruck(foodTruck);
                    }
                });
                builde.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builde.create().show();
                return true;
        }
        return true;
    }

    private class HoursRVAdapter extends RecyclerView.Adapter<HoursViewHolder>{

        @NonNull
        @Override
        public HoursViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.open_hour_detail_item, viewGroup, false);
            return new HoursViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull HoursViewHolder hoursViewHolder, int i) {
            hoursViewHolder.bind(i);
        }

        @Override
        public int getItemCount() {
            return foodTruck.getOpenHours().size();
        }
    }

    private class HoursViewHolder extends RecyclerView.ViewHolder{

        private TextView hour;

        public HoursViewHolder(@NonNull View itemView) {
            super(itemView);

            hour = itemView.findViewById(R.id.hour);
        }

        public void bind(int i){
            hour.setText(formattedOH(foodTruck.getOpenHours().get(i)));
        }

        private String formattedOH(OpenHour oh){
            String fromDay = getResources().getStringArray(R.array.days)[oh.getFromDay()];
            String toDay = getResources().getStringArray(R.array.days)[oh.getToDay()];
            String fromHour = getResources().getStringArray(R.array.hours)[oh.getFromHour()];
            String toHour = getResources().getStringArray(R.array.hours)[oh.getToHour()];
            String fromMinute = getResources().getStringArray(R.array.minutes)[oh.getFromMinute()];
            String toMinute = getResources().getStringArray(R.array.minutes)[oh.getToMinute()];
            String fromAPM = getResources().getStringArray(R.array.apm)[oh.getFromAPM()];
            String toAPM = getResources().getStringArray(R.array.apm)[oh.getToAPM()];

            return fromDay+"-"+toDay+"\n"+"Opens: "+fromHour+":"+fromMinute+" "+fromAPM+"\n"+"Closes: "+toHour+":"+toMinute+" "+toAPM;
        }
    }

    private class MenuRVAdapter extends RecyclerView.Adapter<MenuViewHolder>{

        @NonNull
        @Override
        public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.menu_detail_item, viewGroup, false);
            return new MenuViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i) {
            menuViewHolder.bind(i);
        }

        @Override
        public int getItemCount() {
            return foodTruck.getMenu().size();
        }
    }

    private class MenuViewHolder extends RecyclerView.ViewHolder{

        private TextView description;
        private TextView price;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.menu_item_name);
            price = itemView.findViewById(R.id.menu_item_cost);
        }

        public void bind(int i){
            FoodItem f = foodTruck.getMenu().get(i);
            description.setText(f.getName());
            price.setText(f.getPrice());
        }
    }

}
