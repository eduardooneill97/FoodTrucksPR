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
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFoodFragment extends Fragment {

    private RecyclerView rv;
    private FoodRVAdapter adapter;
    private SearchView searchView;

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

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_fragment_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.search_view_item).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.title_search));

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.search_view_item);
        Drawable icon = item.getIcon();
        icon.setColorFilter(getResources().getColor(R.color.colorSearchBar), PorterDuff.Mode.SRC_IN);

        item.setIcon(icon);
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
            return FoodTruckDatabase.get().getAvailableFoods().size();
        }
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{

        private TextView name;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name);
        }

        public void bind(int i){
            name.setText(FoodTruckDatabase.get().getAvailableFoods().get(i).getName());
        }
    }

}
