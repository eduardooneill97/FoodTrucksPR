package com.example.eduar.foodtruckspr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTruckFragment extends Fragment {


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_truck, container, false);
    }

}
