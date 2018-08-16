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
import com.example.ayush.bakingapp.fragments.StepDetailFragment;
import com.example.ayush.bakingapp.utils.Recipe;

public class PlayActivity extends AppCompatActivity implements StepDetailFragment.OnButtonClick {


    private Integer stepId;
    private StepDetailFragment stepDetailFragment;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Intent intent = getIntent();
        stepId = intent.getIntExtra(AppConstants.STEP_ID, 0);
        recipe = intent.getParcelableExtra(AppConstants.RECIPE);
        NetworkState networkState = new NetworkState(this);
        if(!networkState.checkInternet()){
            showNoInternet();
        }
        else {
            if (savedInstanceState == null) {
                initFragment();
            }
        }



    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            stepDetailFragment = (StepDetailFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, AppConstants.STEP_DETAIL_FRAGMENT_TAG);
            stepId = savedInstanceState.getInt(AppConstants.STEP_ID);


        } else {
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
        getSupportFragmentManager().putFragment(outState, AppConstants.PLAYFRAGMENT_TAG, stepDetailFragment);
        outState.putInt(AppConstants.STEP_ID,stepId);
    }

    @Override
    public void onButtonClick(Integer val, Integer id) {
        switch (val) {
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
    public void showNoInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
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
