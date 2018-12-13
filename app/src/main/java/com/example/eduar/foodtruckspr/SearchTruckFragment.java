package com.example.eduar.foodtruckspr;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class SearchTruckFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mapView;
    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<FoodTruck> trucks;

    public SearchTruckFragment() {
        // Required empty public constructor
    }

    public static SearchTruckFragment newInstance() {

        Bundle args = new Bundle();

        SearchTruckFragment fragment = new SearchTruckFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_truck, container, false);

        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) view.findViewById(R.id.mapContainer);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_fragment_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.search_view_item).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.title_search));

        searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.background_light);
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);

                for(FoodTruck ft: FoodTruckDatabase.get().getFoodTrucks()){
                    if(ft.getName().equalsIgnoreCase(queryString)){
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ft.getLocation(), 17));
                    }
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(FoodTruckDatabase.get().getTruckByName(s) == null){
                    Toast.makeText(getContext(), R.string.not_found, Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.search_view_item);
        Drawable icon = item.getIcon();
        icon.setColorFilter(getResources().getColor(R.color.colorSearchBar), PorterDuff.Mode.SRC_IN);
        item.setIcon(icon);

        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                FoodTruckDatabase.get().getTruckNames());
        searchAutoComplete.setAdapter(arrayAdapter);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng marker = new LatLng(18.2116, -67.1424);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 17));
        map.setMinZoomPreference(1.0f);
        map.setMaxZoomPreference(21.0f);
        map.addMarker(new MarkerOptions()
            .position(marker)
            .title("TikiTacos"));
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                FoodTruck ft = searchFoodTruck(marker);
                if(ft!=null){
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, TruckDetailFragment.newInstance(ft, false)).addToBackStack("detail").commit();
                }
            }
        });

        for(FoodTruck ft: FoodTruckDatabase.get().getFoodTrucks()){
            map.addMarker(new MarkerOptions()
                    .position(ft.getLocation())
                    .title(ft.getName()));
        }
    }

    private FoodTruck searchFoodTruck(Marker m){
        for(FoodTruck f: FoodTruckDatabase.get().getFoodTrucks()){
            if(f.getName().equals(m.getTitle()))
                return f;
        }
        return null;
    }

}
