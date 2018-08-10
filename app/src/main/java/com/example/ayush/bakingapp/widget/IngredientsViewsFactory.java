package com.example.ayush.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.AppConstants.AppConstants;
import com.example.ayush.bakingapp.utils.Recipe;
import com.example.ayush.bakingapp.utils.SaveIngredients;

public class IngredientsViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe recipe;

    public IngredientsViewsFactory(Context context){
        this.mContext = context;
    }
    @Override
    public void onCreate() {
        
    }

    @Override
    public void onDataSetChanged() {
        recipe = SaveIngredients.getRecipe(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe.getIngredientsSize();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_single_ingedient);
        StringBuilder s = new StringBuilder().append(i+1).append(AppConstants.BRACKET).append(" ")
                .append(recipe.getIngredients().get(i).getIngredient());
        remoteViews.setTextViewText(R.id.widget_ingredient_tv,s);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
