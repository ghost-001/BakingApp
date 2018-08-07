package com.example.ayush.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.RetrofitClient;
import com.example.ayush.bakingapp.RetrofitInterface;
import com.example.ayush.bakingapp.adapter.HomeAdapter;
import com.example.ayush.bakingapp.callbacks.grid_Callback;
import com.example.ayush.bakingapp.fragments.HomeDialogFragment;
import com.example.ayush.bakingapp.utils.Recipe;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements grid_Callback, HomeDialogFragment.CategoryDialogListener {

    @BindView(R.id.main_grid_view)
    GridView gridView;
    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    private ArrayList<Recipe> recipe = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Baking App");
        int span = 2;

        if (getResources().getConfiguration().orientation == 2) {
            span = 4;
        }

        gridView.setNumColumns(span);
        getData();


    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("RecipeData",recipe);
    } */

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
        Intent intent = new Intent(this, DetailRecipeActivity.class);
        intent.putExtra("category", str);
        intent.putExtra("value", value);
        intent.putParcelableArrayListExtra("RecipeList",recipe);
        this.startActivity(intent);
    }

    public void getData() {
        String TAG = "RETROFIT";

        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<List<Recipe>> call = retrofitInterface.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                Log.i("RESULT1", "" + recipes.size());
                recipe.addAll(recipes);
                gridView.setAdapter(new HomeAdapter(MainActivity.this, recipe));

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }

}
