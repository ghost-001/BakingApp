package com.example.ayush.bakingapp.retrofitCalls;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.ayush.bakingapp.utils.SimpleIdlingResource;
import com.example.ayush.bakingapp.appConstants.AppConstants;
import com.example.ayush.bakingapp.utils.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitService {


   private  Context mContext;

    public RetrofitService(Context context) {
        mContext = context;
    }


    public interface ResultCallback {
         void notifySuccess(ArrayList<Recipe> response);
    }

    public void getData(final ResultCallback mResultCallback,@Nullable final SimpleIdlingResource idlingResource) {
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ArrayList<Recipe>> call = retrofitInterface.getRecipes();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                if (mResultCallback != null) {
                    mResultCallback.notifySuccess(response.body());
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                throw new RuntimeException(AppConstants.RETROFIT_ERROR);
            }
        });

    }
}
