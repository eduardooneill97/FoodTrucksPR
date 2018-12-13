package com.example.eduar.foodtruckspr;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FavoritesFragment favoritesFragment;
    //private SearchTruckFragment searchTruckFragment;
    private SearchFragment searchFragment;
    private MyTruckFragment myTruckFragment;
    private boolean b;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_favorites:
//                    Fragment f = getSupportFragmentManager().findFragmentByTag("detail");
//                    if(f!=null){
//                        getSupportFragmentManager().beginTransaction().remove(f).commit();
//                    }
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, favoritesFragment, "favorites").commit();
                    return true;
                case R.id.navigation_search:
//                    Fragment g = getSupportFragmentManager().findFragmentByTag("detail");
//                    if(g!=null){
//                        getSupportFragmentManager().beginTransaction().remove(g).commit();
//                    }
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, searchFragment, "search").commit();
                    return true;
                case R.id.navigation_my_truck:
                    b = true;
//                    Fragment h = getSupportFragmentManager().findFragmentByTag("detail");
//                    if(h!=null){
//                        getSupportFragmentManager().beginTransaction().remove(h).commit();
//                    }
                    if(User.get().created == false) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("This section is where you can publish your own food business. Do you wish to continue?");
                        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User.get().created = true;
                                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, myTruckFragment, "mytruck").commit();

                            }
                        });
                        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                b = false;
                            }
                        });
                        builder.create().show();
                    } else{
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myTruckFragment, "mytruck").commit();

                    }
                    return b;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!User.get().signedIn) {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
            finish();
        }
        
        favoritesFragment = FavoritesFragment.newInstance();
        //searchTruckFragment = SearchTruckFragment.newInstance();
        searchFragment = SearchFragment.newInstance();
        myTruckFragment = MyTruckFragment.newInstance();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_search);
    }

}
