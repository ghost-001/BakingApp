package com.example.ayush.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ayush.bakingapp.activity.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class IdlingResourceMainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;
    private IdlingRegistry idlingRegistry = IdlingRegistry.getInstance();

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        idlingRegistry.register(mIdlingResource);
        //Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void IdlingResourceTest() {
        onData(anything()).inAdapterView(withId(R.id.main_grid_view)).atPosition(0).perform(click());

        onView(withId(R.id.detail_ingre)).perform(click());
        onView(withId(R.id.ingre_text_title)).check(matches(withText("Ingredients")));

        pressBack();
        onView(withId(R.id.detail_steps)).perform(click());
        onView(withId(R.id.step_text_title)).check(matches(withText("Steps")));

        onView(ViewMatchers.withId(R.id.step_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.step_short_des)).check(matches(withText("Recipe Introduction")));
        onView(withId(R.id.step_next_button)).perform(click());
        onView(withId(R.id.step_short_des)).check(matches(withText("Starting prep")));
        onView(withId(R.id.step_prev_button)).perform(click());
        onView(withId(R.id.step_short_des)).check(matches(withText("Recipe Introduction")));

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            idlingRegistry.unregister(mIdlingResource);
        }
    }
}
