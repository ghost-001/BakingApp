package com.example.ayush.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.constants.Constants;
import com.example.ayush.bakingapp.fragments.StepDetailFragment;
import com.example.ayush.bakingapp.utils.Recipe;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements StepDetailFragment.OnButtonClick{

    private Integer recipeId;
    private Integer stepId;
    private StepDetailFragment stepDetailFragment;
    //private ArrayList<Recipe> recipe = new ArrayList<>();
   // private Recipe recipeSingle;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Intent intent = getIntent();
        //recipeId = intent.getIntExtra("recipeId", 0);
        stepId = intent.getIntExtra("stepId", 0);
        recipe = intent.getParcelableExtra("recipe");
       // recipeSingle = recipe.get(recipeId);
        // stepDetailFragment = new StepDetailFragment();
        if(savedInstanceState == null) {
            Log.i("PLAYP", "FRAGMENT CREATED");
            initFragment();
        }



}

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            Log.i("PLAYP","FRAGMENT RESTORED");
            stepDetailFragment = (StepDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState,"myPlayFragment");
        }else {
            // stepDetailFragment = new StepDetailFragment();
            Log.i("PLAYP","FRAGMENT CREATED");
            initFragment();
        }

    }

    public void initFragment() {

        FragmentManager manager = getSupportFragmentManager();
        stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setRecipe(recipe);
        stepDetailFragment.setStepId(stepId);
        manager.beginTransaction().replace(R.id.play_activity_frame, stepDetailFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState,"myPlayFragment",stepDetailFragment);
        Log.i("PLAYP","FRAGMENT SAVED");
    }

    @Override
    public void onButtonClick(Integer val) {
        switch (val){
            case 0:
                stepId--;
                initFragment();
                break;
            case 1:
                stepId++;
                initFragment();
                break;
        }
    }
}
