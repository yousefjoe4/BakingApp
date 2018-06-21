package yousef.example.com.baking;

import yousef.example.com.baking.activities.MainActivity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by yousef on 29/5/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityClickItemTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    IdlingRegistry idlingRegistry;
    IdlingResource mIdlingResource;
    @Before
    public void registerIdlingResource() {

        mIdlingResource = mainActivityActivityTestRule.getActivity().getIdlingResource();
        idlingRegistry = IdlingRegistry.getInstance();
        idlingRegistry.register(mIdlingResource);
    }

    @Test
    public void checkData() {
        Espresso.onView(withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void checkIngredients() {
        Espresso.onView(withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(withId(R.id.steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    @After
    public void unregisterIdlingResource() {
        if(idlingRegistry != null){
            idlingRegistry.unregister(mIdlingResource);
        }
    }
}
