package com.example.ayush.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.callbacks.StepsCallback;
import com.example.ayush.bakingapp.utils.Recipe;
import com.example.ayush.bakingapp.utils.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    Recipe recipe;
    List<Step> steps;
    private Context context;
    private StepsCallback stepsCallback;

    public StepsAdapter(Context context, Recipe recipe) {
        this.context = context;
        stepsCallback = (StepsCallback) context;
        this.recipe = recipe;
        this.steps = recipe.getSteps();
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_ingredients_single, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, final int position) {
        final Step step = steps.get(position);
        holder.ingre_tv.setText(position + 1 + ".) " + step.getShortDescription());
        holder.ingre_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepsCallback.stepsInteraction(step.getId(), recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients_tv)
        TextView ingre_tv;

        public StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
