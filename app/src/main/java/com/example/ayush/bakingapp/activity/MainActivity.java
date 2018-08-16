package com.example.ayush.bakingapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.adapter.HomeAdapter;
import com.example.ayush.bakingapp.appConstants.AppConstants;
import com.example.ayush.bakingapp.callbacks.GridCallback;
import com.example.ayush.bakingapp.fragments.HomeDialogFragment;
import com.example.ayush.bakingapp.retrofitCalls.RetrofitService;
import com.example.ayush.bakingapp.utils.NetworkState;
import com.example.ayush.bakingapp.utils.Recipe;
import com.example.ayush.bakingapp.utils.SimpleIdlingResource;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RetrofitService.ResultCallback,GridCallback, HomeDialogFragment.CategoryDialogListener {

    @BindView(R.id.main_grid_view)
    GridView gridView;
    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    ProgressBar progressBar;
    private ArrayList<Recipe> recipe = new ArrayList<>();
    @Nullable
    private SimpleIdlingResource simpleIdlingResource;


@VisibleForTesting
@NonNull
public IdlingResource getIdlingResource(){
if(simpleIdlingResource == null){
    simpleIdlingResource = new SimpleIdlingResource();
}
return simpleIdlingResource;
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.main_progressBar);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        int span = 2;
        if (getResources().getConfiguration().orientation == 2) {
            span = 4;
        }
        gridView.setNumColumns(span);
        getIdlingResource();
        getSupportActionBar().setTitle(getString(R.string.app_name));
        NetworkState networkState = new NetworkState(this);
        if(!networkState.checkInternet()) {
            showNoInternet();

        }else{
            RetrofitService retrofitService = new RetrofitService(this);
            retrofitService.getData(MainActivity.this,simpleIdlingResource);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    public void notifySuccess(ArrayList<Recipe> response) {
        gridView.setAdapter(new HomeAdapter(MainActivity.this, response));
        recipe = response;
    }
    @Override
    public void openRecipe(int value) {
        HomeDialogFragment homeDialogFragment = new HomeDialogFragment();
        homeDialogFragment.setRecepeName(recipe.get(value).getName());
        homeDialogFragment.setRecipeId(value);
        homeDialogFragment.show(getSupportFragmentManager(), AppConstants.HOMEDETAILFRAGMENT_TAG);
    }


    @Override
    public void onCategorySelected(String str, Integer value) {
        Intent intent;
        switch (str) {
            case AppConstants.INGREDIENTS:
                intent = new Intent(this, DetailActivity.class);
                intent.putExtra(AppConstants.CATEGORY, str);
                intent.putExtra(AppConstants.VALUE, value);
                intent.putParcelableArrayListExtra(AppConstants.RECIPELIST, recipe);
                this.startActivity(intent);
                break;
            case AppConstants.STEPS:
                intent = new Intent(this, DetailActivity.class);
                intent.putExtra(AppConstants.CATEGORY, str);
                intent.putExtra(AppConstants.VALUE, value);
                intent.putParcelableArrayListExtra(AppConstants.RECIPELIST, recipe);
                this.startActivity(intent);
                break;
        }

    }

    public void showNoInternet() {
//    progressBar.setVisibility(GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
