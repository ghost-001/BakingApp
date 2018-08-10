package com.example.ayush.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SaveIngredients {



    public static void saveRecipe(Context context,Recipe recipe){
        SharedPreferences mSharedPreference = context.getSharedPreferences("RecipeWidget",MODE_PRIVATE);
        Gson gson = new Gson();
        final SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putInt("Recipe_KEY",recipe.getId());
        String x = gson.toJson(recipe.getIngredients());
        editor.putString("Recipe",gson.toJson(recipe));
        editor.putString("Recipe_INGREDIENTS",x);
        editor.putString("Recipe_NAME",recipe.getName());
        editor.apply();
    }

    public static Recipe getRecipe(Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences("RecipeWidget",Context.MODE_PRIVATE);
        String recipeName = sharedPreferences.getString("Recipe_NAME",null);
        String recipeJson = sharedPreferences.getString("Recipe_INGREDIENTS",null);
        String recipeJ = sharedPreferences.getString("Recipe",null);
        Recipe recipe = gson.fromJson(recipeJ,Recipe.class);
        Ingredient[] recipeIngredeints = gson.fromJson(recipeJson, Ingredient[].class);

        return recipe;
    }

}
