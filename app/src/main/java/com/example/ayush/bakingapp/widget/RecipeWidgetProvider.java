package com.example.ayush.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.activity.MainActivity;
import com.example.ayush.bakingapp.utils.Ingredient;
import com.example.ayush.bakingapp.utils.Recipe;
import com.example.ayush.bakingapp.utils.SaveIngredients;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Recipe recipe = SaveIngredients.getRecipe(context);
        if(recipe!=null) {
            Intent i = new Intent(context,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);


            String recipeName = recipe.getName();
            Intent intent = new Intent(context,IngredientsWidgetService.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
            views.setRemoteAdapter(R.id.widget_list_view,intent);
            views.setTextViewText(R.id.appwidget_text, recipeName);

            views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    public static void updateAllPlantWidgets(Context context,AppWidgetManager appWidgetManager,int[] widgetIds){
        for (int widgetId : widgetIds){
            updateAppWidget(context,appWidgetManager,widgetId);
        }
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

