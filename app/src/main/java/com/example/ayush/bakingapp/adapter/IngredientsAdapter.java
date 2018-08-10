package com.example.ayush.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayush.bakingapp.AppConstants.AppConstants;
import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.utils.Ingredient;
import com.example.ayush.bakingapp.utils.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    Recipe recipe;
    List<Ingredient> ingredient;
    private Context context;

    public IngredientsAdapter(Context context, Recipe recipe) {
        this.context = context;
        this.recipe = recipe;
        this.ingredient = recipe.getIngredients();
    }

    @NonNull
    @Override
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_ingredients_single, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.IngredientsViewHolder holder, int position) {
        Ingredient ingre = ingredient.get(position);
        StringBuilder s = new StringBuilder().append(position + 1).append(AppConstants.BRACKET).append(" ")
                .append(ingre.getQuantity()).append(" ")
                .append(ingre.getMeasure()).append(" ")
                .append(ingre.getIngredient());
        holder.ingre_tv.setText(s);

    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients_tv)
        TextView ingre_tv;

        private IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
