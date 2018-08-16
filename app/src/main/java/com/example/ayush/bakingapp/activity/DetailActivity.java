package com.example.ayush.bakingapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.ayush.bakingapp.utils.NetworkState;
import com.example.ayush.bakingapp.appConstants.AppConstants;
import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.callbacks.StepsCallback;
import com.example.ayush.bakingapp.fragments.IngredientsFragment;
import com.example.ayush.bakingapp.fragments.StepDetailFragment;
import com.example.ayush.bakingapp.fragments.StepsFragment;
import com.example.ayush.bakingapp.utils.Recipe;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements
        StepsCallback, StepDetailFragment.OnButtonClick {

    private Integer recipeNum;
    private String category;
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private Recipe recipeSingle;
    private Boolean mTwoPane = false;
    private Integer stepId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        recipeNum = intent.getIntExtra(AppConstants.VALUE, 0);
        category = intent.getStringExtra(AppConstants.CATEGORY);
        recipeList = intent.getParcelableArrayListExtra(AppConstants.RECIPELIST);
        recipeSingle = recipeList.get(recipeNum);
        if (category.equals(AppConstants.STEPS)) {
            setContentView(R.layout.activity_steps);
        } else {
            setContentView(R.layout.activity_ingredients);
        }
        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
        NetworkState networkState = new NetworkState(this);
        if(!networkState.checkInternet()){
            showNoInternet();
        }
        else {
            initFragment();
        }
    }

    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        switch (category) {
            case AppConstants.STEPS:
                StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setRecipe(recipeSingle);
                manager.beginTransaction().replace(R.id.detail_recipe_frame, stepsFragment).commit();
                break;

            case AppConstants.INGREDIENTS:
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setRecipe(recipeSingle);
                ingredientsFragment.setValue(recipeNum);
                manager.beginTransaction().replace(R.id.detail_recipe_frame, ingredientsFragment).commit();
                break;
        }
    }

    @Override
    public void stepsInteraction(int id, Recipe recipe) {
        stepId = id;
        FragmentManager manager = getSupportFragmentManager();
        if (mTwoPane) {
            StepDetailFragment stepDetailFragment;
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setRecipe(recipeSingle);
            stepDetailFragment.setStepId(id);
            manager.beginTransaction().replace(R.id.item_detail_container, stepDetailFragment).commit();
        } else {
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra(AppConstants.STEP_ID, id);
            intent.putExtra(AppConstants.RECIPE, recipeSingle);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onButtonClick(Integer val, Integer id) {
        switch (val) {
            case 0:
                id--;
                initFragmentButton(id);
                break;
            case 1:
                id++;
                initFragmentButton(id);
                break;
        }
    }

    public void initFragmentButton(Integer id) {

        FragmentManager manager = getSupportFragmentManager();
        StepDetailFragment stepDetailFragment;
        stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setRecipe(recipeSingle);
        stepDetailFragment.setStepId(id);
        manager.beginTransaction().replace(R.id.item_detail_container, stepDetailFragment).commit();
    }
    public void showNoInternet() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.create();
        builder.show();
    }
}
