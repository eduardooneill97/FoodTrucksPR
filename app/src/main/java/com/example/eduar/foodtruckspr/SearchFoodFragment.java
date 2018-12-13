package com.example.eduar.foodtruckspr;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFoodFragment extends Fragment {

    private TextView placeholder;
    private RecyclerView rv;
    private FoodRVAdapter adapter;
    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<FoodTruck> searchTrucks;

    public SearchFoodFragment() {
        // Required empty public constructor
    }

    public static SearchFoodFragment newInstance(){
        return new SearchFoodFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        adapter = new FoodRVAdapter();
        searchTrucks = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_food, container, false);

        setHasOptionsMenu(true);
        rv = v.findViewById(R.id.food_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
//        placeholder = v.findViewById(R.id.search_placeholder);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

//        inflater.inflate(R.menu.search_fragment_menu, menu);
//
//        searchView = (SearchView) menu.findItem(R.id.search_view_item).getActionView();
//        searchView.setQueryHint(getResources().getString(R.string.title_search));
//
//        searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        searchAutoComplete.setDropDownBackgroundResource(android.R.color.background_light);
//        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
//                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
//                searchAutoComplete.setText("" + queryString);
//                searchTrucks = new ArrayList<>();
//                for(FoodItem i: FoodTruckDatabase.get().getFoodAvailability().keySet()){
//                    if(queryString.equalsIgnoreCase(i.toString())){
//                        for(int j = 0;j<FoodTruckDatabase.get().getFoodAvailability().get(i).size(); j++)
//                        searchTrucks.add(FoodTruckDatabase.get().getFoodAvailability().get(i).get(j));
//                    }
//                }
//                placeholder.setVisibility(View.GONE);
//                adapter.notifyDataSetChanged();
//            }
//        });
        inflater.inflate(R.menu.empty_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);

//        MenuItem item = menu.findItem(R.id.search_view_item);
//        Drawable icon = item.getIcon();
//        icon.setColorFilter(getResources().getColor(R.color.colorSearchBar), PorterDuff.Mode.SRC_IN);
//        item.setIcon(icon);
//
//        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
//                FoodTruckDatabase.get().getFoodNames());
//        searchAutoComplete.setAdapter(arrayAdapter);
    }

    public class FoodRVAdapter extends RecyclerView.Adapter<FoodViewHolder>{

        @NonNull
        @Override
        public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.food_item, viewGroup, false);
            return new FoodViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i) {
            foodViewHolder.bind(i);
        }

        @Override
        public int getItemCount() {
//            return searchTrucks.size();
            return getResources().getStringArray(R.array.categories).length;
        }
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private FoodTruck ft;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, TruckListFragment.newInstance(name.getText().toString()))
                            .addToBackStack("categories").commit();
                }
            });
        }

        public void bind(int i){
            name.setText(getResources().getStringArray(R.array.categories)[i]);
            //ft = searchTrucks.get(i);
        }
    }

}
