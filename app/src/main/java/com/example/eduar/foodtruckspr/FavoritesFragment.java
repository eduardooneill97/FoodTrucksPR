package com.example.eduar.foodtruckspr;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    private RecyclerView recyclerView;
    private FoodTrucksRVAdapter adapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance(){
        Bundle args = new Bundle();

        FavoritesFragment fragment = new FavoritesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        adapter = new FoodTrucksRVAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_favorites, container, false);

        setHasOptionsMenu(true);
        recyclerView = v.findViewById(R.id.favorites_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.empty_menu, menu);

    }

    public class FoodTrucksRVAdapter extends RecyclerView.Adapter<FTViewHolder>{


        @NonNull
        @Override
        public FTViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.foodtruck_list_item, viewGroup, false);
            return new FTViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull FTViewHolder ftViewHolder, int i) {
            ftViewHolder.bind(i);
        }

        @Override
        public int getItemCount() {
            return User.get().getFavorites().size();
        }
    }

    public class FTViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView image;
        private TextView name;
        private FoodTruck foodTruck;

        public FTViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.truck_name);
            image = itemView.findViewById(R.id.truck_image);
            itemView.setOnClickListener(this);
        }

        public void bind(int i){
            foodTruck = User.get().getFavorites().get(i);
            name.setText(foodTruck.getName());
            if(foodTruck.getImage() != null)
                image.setImageBitmap(getScaledBitmap(foodTruck.getImage()));
        }

        @Override
        public void onClick(final View v){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, TruckDetailFragment.newInstance(foodTruck, false)).commit();
        }

        private Bitmap getScaledBitmap(String path){
            Point size = new Point();
            getActivity().getWindowManager().getDefaultDisplay().getSize(size);

            return getScaledBitmap2(path, size.x, size.y);
        }

        private Bitmap getScaledBitmap2(String path, int destW, int destH){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            float srcW = options.outWidth;
            float srcH = options.outHeight;

            int inSampleSize = 1;
            if(srcH > destH || srcW>destW){
                float heightScale = srcH/destH;
                float widthScale = srcW/destW;

                inSampleSize = Math.round(heightScale>widthScale ? heightScale:widthScale);
            }

            options = new BitmapFactory.Options();
            options.inSampleSize = inSampleSize;

            return BitmapFactory.decodeFile(path, options);
        }
    }

}
