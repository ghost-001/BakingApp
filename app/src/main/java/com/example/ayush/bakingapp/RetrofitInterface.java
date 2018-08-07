package com.example.ayush.bakingapp;

import com.example.ayush.bakingapp.utils.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
