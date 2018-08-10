package com.example.ayush.bakingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.adapter.IngredientsAdapter;
import com.example.ayush.bakingapp.constants.Constants;
import com.example.ayush.bakingapp.utils.Ingredient;
import com.example.ayush.bakingapp.utils.Recipe;
import com.example.ayush.bakingapp.utils.SaveIngredients;
import com.example.ayush.bakingapp.widget.IngredientsWidgetService;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class IngredientsFragment extends Fragment {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ingre_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.ingre_text_title)
    TextView title_tv;
    @BindView(R.id.ingre_button)
    Button mAdd;


    private Recipe recipe;
    private Integer value;
    private IngredientsAdapter adapter;
    private SharedPreferences mSharedPreference;
    private Gson gson = new Gson();
    public IngredientsFragment() {

    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        adapter = new IngredientsAdapter(getContext(), recipe);

        final View root = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, root);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipe.getName());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        title_tv.setText(Constants.INGREDIENTS);



        //SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("Recipes",MODE_PRIVATE);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveIngredients.saveRecipe(getContext(),recipe);
                Toast.makeText(getContext(),"Added to Widget",Toast.LENGTH_SHORT).show();
                IngredientsWidgetService.updateTheWidgets(getContext(),recipe);
            }
        });
      /*  final Activity activity = getActivity();
        final View content = activity.findViewById(android.R.id.content).getRootView();
        if (content.getWidth() > 0) {
            Bitmap image = BitmapFactory.decodeResource(getResources(), constants.images[value]);
            root.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));
        } else {
            content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Bitmap image = BitmapFactory.decodeResource(getResources(), constants.images[value]);
                    // Bitmap image = BlurBuilder.blur(content,getContext());
                    root.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), image));
                }
            });
        }*/
        //root.getBackground().setAlpha(250);
        return root;
    }






    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }


}
