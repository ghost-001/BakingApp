package com.example.ayush.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.retrofitCalls.RetrofitService;
import com.example.ayush.bakingapp.adapter.HomeAdapter;
import com.example.ayush.bakingapp.callbacks.ResultCallback;
import com.example.ayush.bakingapp.callbacks.grid_Callback;
import com.example.ayush.bakingapp.constants.Constants;
import com.example.ayush.bakingapp.fragments.HomeDialogFragment;
import com.example.ayush.bakingapp.utils.Recipe;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements grid_Callback, HomeDialogFragment.CategoryDialogListener {

    @BindView(R.id.main_grid_view)
    GridView gridView;
    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    private ArrayList<Recipe> recipe = new ArrayList<>();
    private Gson gson = new Gson();
    private ResultCallback mResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        int span = 2;

        if (getResources().getConfiguration().orientation == 2) {
            span = 4;
        }

        gridView.setNumColumns(span);
        //msharedPreference = getPreferences(MODE_PRIVATE);

       mResult = new ResultCallback() {
           @Override
           public void notifySuccess(ArrayList<Recipe> response) {
               gridView.setAdapter(new HomeAdapter(MainActivity.this, response));
               recipe = response;
           }
       };

        RetrofitService retrofitService = new RetrofitService(mResult,this);
        retrofitService.getData();
        //getData();


    }

    @Override
    public void openRecipe(int value) {
        HomeDialogFragment homeDialogFragment = new HomeDialogFragment();
        homeDialogFragment.setRecepeName(recipe.get(value).getName());
        homeDialogFragment.setRecipeId(value);
        homeDialogFragment.show(getSupportFragmentManager(), "HomeDialogFragment");
    }


    @Override
    public void onCategorySelected(String str, Integer value) {
        Toast.makeText(this, str + value, Toast.LENGTH_SHORT).show();
        Intent intent;
        switch (str){
            case Constants.INGREDIENTS:
                intent = new Intent(this, IngredientsActivity.class);
                intent.putExtra(Constants.CATEGORY, str);
                intent.putExtra(Constants.VALUE, value);
                intent.putParcelableArrayListExtra(Constants.RECIPELIST,recipe);
                this.startActivity(intent);
                break;
            case Constants.STEPS:
                intent = new Intent(this, StepActivity.class);
                intent.putExtra(Constants.CATEGORY, str);
                intent.putExtra(Constants.VALUE, value);
                intent.putParcelableArrayListExtra(Constants.RECIPELIST,recipe);
                this.startActivity(intent);
                break;
            }

    }

    /*@Override
    public void notifySuccess(List<Recipe> response) {
        gridView.setAdapter(new HomeAdapter(MainActivity.this, recipe));
    } */

    /*public void getData() {

        final SharedPreferences.Editor prefsEditor = msharedPreference.edit();
        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<List<Recipe>> call = retrofitInterface.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                String saveOffline = gson.toJson(recipes);
                prefsEditor.putString("MyRecipes",saveOffline);
                prefsEditor.commit();
                Log.i("RESULT1", "" + recipes.size());
                recipe.addAll(recipes);
                gridView.setAdapter(new HomeAdapter(MainActivity.this, recipe));
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    } */

}
