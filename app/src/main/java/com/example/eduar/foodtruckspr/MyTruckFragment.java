package com.example.eduar.foodtruckspr;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import java.nio.BufferUnderflowException;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTruckFragment extends Fragment {

    private ScrollView scrollView;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private OpenHoursRVAdapter adapter;
    private MenuItemsRVAdapter adapter2;
    private ImageButton addHourButton;
    private ImageButton addMenuItemButton;
    private AppCompatSpinner category;
    private TextInputLayout name;
    private TextInputLayout phone;

    public MyTruckFragment() {
        // Required empty public constructor
    }

    public static MyTruckFragment newInstance(){
        Bundle args = new Bundle();

        MyTruckFragment fragment = new MyTruckFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        adapter = new OpenHoursRVAdapter();
        adapter2 = new MenuItemsRVAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_truck, container, false);

        setHasOptionsMenu(true);

        scrollView = v.findViewById(R.id.scrollView1);
        category = v.findViewById(R.id.spinner);
        User.get().getMyTruck().setCategory(category.getItemAtPosition(0).toString());
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                User.get().getMyTruck().setCategory(category.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        name = v.findViewById(R.id.name);
        name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name.setError(null);
                User.get().getMyTruck().setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phone = v.findViewById(R.id.phone);
        phone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone.setError(null);
                User.get().getMyTruck().setPhone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recyclerView = v.findViewById(R.id.hours_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        recyclerView2 = v.findViewById(R.id.menu_items_recycler_view);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setAdapter(adapter2);

        addHourButton = v.findViewById(R.id.add_hour_button);
        addHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.get().getMyTruck().getOpenHours().add(new OpenHour());
                adapter.notifyDataSetChanged();
            }
        });

        addMenuItemButton = v.findViewById(R.id.add_menu_button);
        addMenuItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.get().getMyTruck().getMenu().add(new FoodItem());
                adapter2.notifyDataSetChanged();
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.my_truck_menu, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.update:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure? You're Food Truck will be visible to the world.");
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(checkValidInput()){
                            Toast.makeText(getContext(), "Information Uploaded", Toast.LENGTH_SHORT).show();
                            FoodTruckDatabase.get().addFoodTruck(User.get().getMyTruck());
                        }
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                return true;
            case R.id.delete:
                AlertDialog.Builder builde = new AlertDialog.Builder(getActivity());
                builde.setMessage("Are you sure? You're Food Truck will no longer appear to anyone.");
                builde.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Deleted My Truck", Toast.LENGTH_SHORT).show();
                        FoodTruckDatabase.get().removeFoodTruck(User.get().getMyTruck());
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
        return false;
    }

    private class OpenHoursRVAdapter extends RecyclerView.Adapter<OHViewHolder>{

        @NonNull
        @Override
        public OHViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.open_hour_item, viewGroup, false);

            return new OHViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull OHViewHolder ohViewHolder, int i) {
            ohViewHolder.bind(i);
        }

        @Override
        public int getItemCount() {
            FoodTruck t = User.get().getMyTruck();
            if(t == null)
                return 0;
            else return t.getOpenHours().size();
        }
    }

    private class OHViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private AppCompatSpinner fromDay;
        private AppCompatSpinner toDay;
        private AppCompatSpinner fromHour;
        private AppCompatSpinner toHour;
        private AppCompatSpinner fromMinute;
        private AppCompatSpinner toMinute;
        private AppCompatSpinner fromAPM;
        private AppCompatSpinner toAPM;
        private ImageButton deleteButton;
        private OpenHour openHour;

        public OHViewHolder(@NonNull View itemView) {
            super(itemView);
            fromDay = itemView.findViewById(R.id.from_day_spinner);
            toDay = itemView.findViewById(R.id.to_day_spinner);
            fromHour = itemView.findViewById(R.id.from_hour_spinner);
            toHour = itemView.findViewById(R.id.to_hour_spinner);
            fromMinute = itemView.findViewById(R.id.from_minute_spinner);
            toMinute = itemView.findViewById(R.id.to_minute_spinner);
            fromAPM = itemView.findViewById(R.id.from_apm_spinner);
            toAPM = itemView.findViewById(R.id.to_apm_spinner);
            deleteButton = itemView.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(this);
        }

        public void bind(int i){
            OpenHour op = User.get().getMyTruck().getOpenHours().get(i);
            openHour = op;
            if(!op.isInitialized()){
                User.get().initializeOpenHours(op,0,0,0,0,0,0,0,0);
            }
            fromDay.setSelection(op.getFromDay()!=-1 ? op.getFromDay():0);
            toDay.setSelection(op.getToDay()!=-1 ? op.getToDay():0);
            fromHour.setSelection(op.getFromHour()!=-1 ? op.getFromHour():0);
            toHour.setSelection(op.getToHour()!=-1 ? op.getToHour():0);
            fromMinute.setSelection(op.getFromMinute()!=-1 ? op.getFromMinute():0);
            toMinute.setSelection(op.getToMinute()!=-1 ? op.getToMinute():0);
            fromAPM.setSelection(op.getFromAPM()!=-1 ? op.getFromAPM():0);
            toAPM.setSelection(op.getToAPM()!=-1 ? op.getToAPM():0);
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you want to delete this item?");
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    User.get().getMyTruck().getOpenHours().remove(openHour);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }
    }

    private class MenuItemsRVAdapter extends RecyclerView.Adapter<MIViewHolder>{

        @NonNull
        @Override
        public MIViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.menu_item, viewGroup, false);
            return new MIViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MIViewHolder miViewHolder, int i) {
            miViewHolder.bind(i);
        }

        @Override
        public int getItemCount() {
            return User.get().getMyTruck().getMenu().size();
        }
    }

    private class MIViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextInputLayout description;
        private TextInputLayout price;
        private ImageButton deleteButton;
        private FoodItem foodItem;

        public MIViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.menu_item_name);
            price = itemView.findViewById(R.id.menu_item_cost);
            deleteButton = itemView.findViewById(R.id.delete_button);
            deleteButton.setOnClickListener(this);
            description.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    foodItem.setName(s.toString());
                }
            });
            price.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    foodItem.setPrice(s.toString());
                }
            });
        }

        public void bind(int i){
            foodItem = User.get().getMyTruck().getMenu().get(i);
            description.getEditText().setText(foodItem.getName());
            price.getEditText().setText(foodItem.getPrice());
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you want to delete this item?");
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    User.get().getMyTruck().getMenu().remove(foodItem);
                    adapter2.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }
    }

    private boolean checkValidInput(){
        boolean b = true;
        if(User.get().getMyTruck().getName().equals("")){
            name.setError("Name is required.");
            b = false;
        }
        if(User.get().getMyTruck().getPhone().equals("None")){
            phone.setError("Phone is required.");
            b = false;
        }
        else if(!User.get().getMyTruck().getPhone().matches("[(][0-9]{3}[)][-]?[0-9]{3}[-]?[0-9]{4}") &&
                !User.get().getMyTruck().getPhone().matches("[0-9]{3}[-]?[0-9]{3}[-]?[0-9]{4}") &&
                !(User.get().getMyTruck().getPhone().matches("[0-9]+") && User.get().getMyTruck().getPhone().length() == 10)){
            phone.setError("Invalid phone number.");
            b = false;
        }
        return b;
    }
}
