package com.example.ayush.bakingapp.retrofitCalls;

import android.content.Context;
import android.util.Log;

import com.example.ayush.bakingapp.callbacks.ResultCallback;
import com.example.ayush.bakingapp.retrofitCalls.RetrofitClient;
import com.example.ayush.bakingapp.retrofitCalls.RetrofitInterface;
import com.example.ayush.bakingapp.utils.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitService {

    ResultCallback mResultCallback = null;
    Context mContext;

    public RetrofitService(ResultCallback resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }


    public void getData() {

        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ArrayList<Recipe>> call = retrofitInterface.getRecipes();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                List<Recipe> recipes = response.body();
                Log.i("RESULT1", "" + recipes.size());
                if(mResultCallback!=null){
                    mResultCallback.notifySuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

            }
        });

    }
}
