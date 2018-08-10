package com.example.ayush.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayush.bakingapp.AppConstants.AppConstants;
import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.adapter.StepsAdapter;
import com.example.ayush.bakingapp.utils.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.step_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.step_text_title)
    TextView title_tv;
    private Recipe recipe;
    private StepsAdapter adapter;

    public StepsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new StepsAdapter(getContext(), recipe);


        View root = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, root);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipe.getName());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        title_tv.setText(AppConstants.STEPS);

        return root;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }


}
