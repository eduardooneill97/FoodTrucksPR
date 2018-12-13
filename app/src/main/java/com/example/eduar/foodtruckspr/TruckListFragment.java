package com.example.eduar.foodtruckspr;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TruckListFragment extends Fragment {

    private static String LIST_CATEGORY = "listcategory";
    private ArrayList<String> truckNames;
    private TruckRVAdapter adapter;


    public TruckListFragment() {
        // Required empty public constructor
    }

    public static TruckListFragment newInstance(String category){
        Bundle args = new Bundle();
        args.putString(LIST_CATEGORY, category);
        TruckListFragment f = new TruckListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        truckNames = FoodTruckDatabase.get().getTrucksByCategory(getArguments().getString(LIST_CATEGORY));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_truck_list, container, false);

        RecyclerView rv = v.findViewById(R.id.truck_list_recycler_view);
        adapter = new TruckRVAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView placeholder = v.findViewById(R.id.nothing_placeholder);
        if(truckNames.size() != 0)
            placeholder.setVisibility(View.GONE);

        TextView categoryName = v.findViewById(R.id.category_name);
        categoryName.setText(getArguments().getString(LIST_CATEGORY));

        if(truckNames.size() == 0)
            rv.setVisibility(View.GONE);

        return v;
    }

    private class TruckRVAdapter extends RecyclerView.Adapter<TruckViewHolder>{

        @NonNull
        @Override
        public TruckViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.food_item, viewGroup, false);
            return new TruckViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull TruckViewHolder truckViewHolder, int i) {
            truckViewHolder.bind(i);
        }

        @Override
        public int getItemCount() {
            return truckNames.size();
        }
    }

    private class TruckViewHolder extends RecyclerView.ViewHolder{

        private TextView name;

        public TruckViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FoodTruck ft = FoodTruckDatabase.get().getTruckByName(name.getText().toString());
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, TruckDetailFragment.newInstance(ft, false))
                            .addToBackStack("trucklist").commit();
                }
            });
        }

        public void bind(int i){
            name.setText(truckNames.get(i));
            //ft = searchTrucks.get(i);
        }
    }

}
