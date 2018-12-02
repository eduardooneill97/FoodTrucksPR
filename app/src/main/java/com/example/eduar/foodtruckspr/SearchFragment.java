package com.example.eduar.foodtruckspr;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private ViewPager pager;
    private TabLayout tabLayout;
    private SearchTruckFragment searchTruckFragment;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(){
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        searchTruckFragment = SearchTruckFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        pager = v.findViewById(R.id.fragment_view_pager);
        tabLayout = v.findViewById(R.id.fragment_tab_layout);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()){
            @Override
            public Fragment getItem(int i) {
                switch (i){
                    case 0:
                        return searchTruckFragment;
                    case 1:
                        return MyTruckFragment.newInstance();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));


        return v;
    }

}
