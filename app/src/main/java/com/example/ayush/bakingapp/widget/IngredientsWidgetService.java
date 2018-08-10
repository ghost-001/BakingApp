package com.example.ayush.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.utils.Recipe;
import com.example.ayush.bakingapp.utils.SaveIngredients;

public class IngredientsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsViewsFactory(this.getApplicationContext());
    }

    public static void updateTheWidgets(Context context,Recipe recipe){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,RecipeWidgetProvider.class));
       RecipeWidgetProvider.updateAllPlantWidgets(context,appWidgetManager,appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
    }
}
