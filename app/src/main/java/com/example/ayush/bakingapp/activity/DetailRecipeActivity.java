package com.example.ayush.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.callbacks.StepsCallback;
import com.example.ayush.bakingapp.fragments.IngredientsFragment;
import com.example.ayush.bakingapp.fragments.StepsFragment;
import com.example.ayush.bakingapp.utils.Recipe;

import java.util.ArrayList;

public class DetailRecipeActivity extends AppCompatActivity implements
        StepsCallback {

    private Integer recipeNum;
    private String category;
    private ArrayList<Recipe> recipeDetails = new ArrayList<>();
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private Recipe recipeSingle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();

        recipeNum = intent.getIntExtra("value", 0);
        category = intent.getStringExtra("category");
        recipeList = intent.getParcelableArrayListExtra("RecipeList");
        recipeSingle = recipeList.get(recipeNum);
        initFragment();
    }

    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        switch (category) {
            case "Steps":
                StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setRecipe(recipeSingle);
                manager.beginTransaction().replace(R.id.detail_recipe_frame, stepsFragment).commit();
                break;
            case "Ingredients":
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setRecipe(recipeSingle);
                ingredientsFragment.setValue(recipeNum);
                manager.beginTransaction().replace(R.id.detail_recipe_frame, ingredientsFragment).commit();
                break;

        }
    }

    @Override
    public void stepsInteraction(int id, Recipe recipe) {

        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("stepId", id);
        intent.putExtra("recipe",recipeSingle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
