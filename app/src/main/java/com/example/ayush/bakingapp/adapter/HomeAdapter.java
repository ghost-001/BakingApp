package com.example.ayush.bakingapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.callbacks.grid_Callback;
import com.example.ayush.bakingapp.constants.Constants;
import com.example.ayush.bakingapp.utils.Recipe;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends BaseAdapter {
    private static LayoutInflater inflater;
    Context context;
    List<String> name;
    ArrayList<Recipe> recipe = new ArrayList<>();
    Constants constants;
    private grid_Callback gridCallback;

    public HomeAdapter(Context context, ArrayList<Recipe> r) {
        this.context = context;
        this.recipe = r;
        gridCallback = (grid_Callback) context;
        inflater = LayoutInflater.from(context);
        Log.i("Adapter",""+r.size());
    }

    @Override
    public int getCount() {
        return recipe.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            Recipe r = recipe.get(i);
            view = inflater.inflate(R.layout.main_grid, viewGroup, false);
            ImageView image = view.findViewById(R.id.home_grid_image);
            TextView recipeName = view.findViewById(R.id.home_grid_name);
            TextView stepNum = view.findViewById(R.id.home_grid_step);
            TextView ingreNum = view.findViewById(R.id.home_grid_ingre);
            TextView servingNum = view.findViewById(R.id.home_grid_serving);

            image.setImageResource(constants.images[i]);
            recipeName.setText(r.getName());
            stepNum.setText(String.valueOf(r.getStepsSize()));
            ingreNum.setText(String.valueOf(r.getIngredientsSize()));
            servingNum.setText(String.valueOf(r.getServings()));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gridCallback.openRecipe(i);
                }
            });
        }
        return view;
    }
}
