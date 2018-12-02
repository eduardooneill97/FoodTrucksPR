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
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class SearchTruckFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mapView;
    private SearchView searchView;

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

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.search_view_item);
        Drawable icon = item.getIcon();
        icon.setColorFilter(getResources().getColor(R.color.colorSearchBar), PorterDuff.Mode.SRC_IN);

        item.setIcon(icon);
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
        map.addMarker(new MarkerOptions()
            .position(marker)
            .title("BitchTacos")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
    }
}
