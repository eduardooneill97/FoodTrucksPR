package com.example.eduar.foodtruckspr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class SearchFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mapView;
    private SearchView searchView;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        //setHasOptionsMenu(true);

        FrameLayout fl = v.findViewById(R.id.searchLayout);
        fl.setBackgroundColor(getResources().getColor(R.color.colorPrimaryOrange));

        searchView = v.findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);

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

//        searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
//        searchView.setIconified(false);
//        searchView.setIconifiedByDefault(false);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        this.map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng marker = new LatLng(18.2116, -67.1424);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 17));
        map.setMinZoomPreference(1.0f);
        map.setMaxZoomPreference(21.0f);

    }
}
