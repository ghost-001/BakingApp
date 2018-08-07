package com.example.ayush.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.callbacks.StepsCallback;
import com.example.ayush.bakingapp.fragments.IngredientsFragment;
import com.example.ayush.bakingapp.fragments.StepDetailFragment;
import com.example.ayush.bakingapp.fragments.StepsFragment;
import com.example.ayush.bakingapp.utils.Recipe;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity implements
        StepsCallback,StepDetailFragment.OnButtonClick {

    private Integer recipeNum;
    private String category;
    private ArrayList<Recipe> recipeDetails = new ArrayList<>();
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private Recipe recipeSingle;
    private Boolean mTwoPane = false;
    private Integer stepId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        Intent intent = getIntent();

        recipeNum = intent.getIntExtra("value", 0);
        category = intent.getStringExtra("category");
        recipeList = intent.getParcelableArrayListExtra("RecipeList");
        recipeSingle = recipeList.get(recipeNum);
        if(findViewById(R.id.item_detail_container) != null){
            mTwoPane = true;
        }
        initFragment();
    }

    public void initFragment() {
        FragmentManager manager = getSupportFragmentManager();


            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setRecipe(recipeSingle);
            manager.beginTransaction().replace(R.id.detail_recipe_frame, stepsFragment).commit();
    }

    @Override
    public void stepsInteraction(int id, Recipe recipe) {
        stepId = id;
        FragmentManager manager = getSupportFragmentManager();
        if(mTwoPane){
            StepDetailFragment stepDetailFragment;
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setRecipe(recipeSingle);
            stepDetailFragment.setStepId(id);
            manager.beginTransaction().replace(R.id.item_detail_container, stepDetailFragment).commit();
        }
        else {
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("stepId", id);
            intent.putExtra("recipe", recipeSingle);
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
    public void onButtonClick(Integer val,Integer id) {
        switch (val){
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
}
