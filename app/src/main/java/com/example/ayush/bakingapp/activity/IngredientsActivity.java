package com.example.ayush.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.constants.Constants;
import com.example.ayush.bakingapp.fragments.IngredientsFragment;
import com.example.ayush.bakingapp.utils.Recipe;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {
    private Integer recipeNum;
    private String category;
    private ArrayList<Recipe> recipeDetails = new ArrayList<>();
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private Recipe recipeSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        Intent intent = getIntent();

        recipeNum = intent.getIntExtra(Constants.VALUE, 0);
        category = intent.getStringExtra(Constants.CATEGORY);
        recipeList = intent.getParcelableArrayListExtra(Constants.RECIPELIST);
        recipeSingle = recipeList.get(recipeNum);
        initFragment();
    }

    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setRecipe(recipeSingle);
        ingredientsFragment.setValue(recipeNum);
        manager.beginTransaction().replace(R.id.detail_recipe_frame, ingredientsFragment).commit();
    }

}
