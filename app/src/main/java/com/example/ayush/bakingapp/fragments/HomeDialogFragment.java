package com.example.ayush.bakingapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.ayush.bakingapp.R;

public class HomeDialogFragment extends DialogFragment {
    private Integer recipeId;
    private String recepeName;
    private CategoryDialogListener mListener;
    TextView ingredients;
    TextView steps;
    TextView name;

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public void setRecepeName(String recepeName) {
        this.recepeName = recepeName;
    }

    public interface CategoryDialogListener {
        public void onCategorySelected(String str, Integer value);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            recepeName = savedInstanceState.getString("name");
            recipeId = savedInstanceState.getInt("value");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_dialog, null);
        ingredients = v.findViewById(R.id.detail_ingre);
        steps = v.findViewById(R.id.detail_steps);
        name = v.findViewById(R.id.detail_name);
        name.setText(recepeName);
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategorySelected("Ingredients", recipeId);
            }
        });

        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategorySelected("Steps", recipeId);
            }
        });
        builder.setView(v);
        return builder.create();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("value", recipeId);
        outState.putString("name", recepeName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CategoryDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
