package com.example.ayush.bakingapp.callbacks;

import com.example.ayush.bakingapp.utils.Recipe;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface ResultCallback {
    public void notifySuccess(ArrayList<Recipe> response);
}
