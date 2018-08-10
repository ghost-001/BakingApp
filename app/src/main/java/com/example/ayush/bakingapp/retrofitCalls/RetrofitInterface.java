package com.example.ayush.bakingapp.retrofitCalls;

import com.example.ayush.bakingapp.utils.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
