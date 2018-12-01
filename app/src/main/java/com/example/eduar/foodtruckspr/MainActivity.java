package com.example.eduar.foodtruckspr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FavoritesFragment favoritesFragment;
    private SearchFragment searchFragment;
    private MyTruckFragment myTruckFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_favorites:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, favoritesFragment).commit();
                    return true;
                case R.id.navigation_search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();
                    return true;
                case R.id.navigation_my_truck:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myTruckFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoritesFragment = FavoritesFragment.newInstance();
        searchFragment = SearchFragment.newInstance();
        myTruckFragment = MyTruckFragment.newInstance();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_search);
    }

}
