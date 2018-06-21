package yousef.example.com.baking.activities;

import android.content.Intent;
import yousef.example.com.baking.adapters.IngredientsStepsAdapter;
import yousef.example.com.baking.objects.Ingredient;
import yousef.example.com.baking.objects.Recipe;
import yousef.example.com.baking.objects.Steps;
import yousef.example.com.baking.R;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements IngredientsStepsAdapter.OnItemClick {


    public static final String RECIPE_EXTRA = "recipe_extra";
    ArrayList<Ingredient> ingredients;
    ArrayList<Steps> steps;
    boolean isTablet;
    RecyclerView recyclerView;
    private static final String BUNDLE_RECYCLER_POSITION = "bundle_recycler_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (!(getIntent().hasExtra(RECIPE_EXTRA))) {
            return;
        }

        Recipe recipe = getIntent().getParcelableExtra(RECIPE_EXTRA);
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();

        isTablet = getResources().getBoolean(R.bool.isTablet);

        if (isTablet) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeListFragment recipeListFragment = new RecipeListFragment();

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RecipeListFragment.LIST_BUNDLE, steps);
            recipeListFragment.setArguments(bundle);

            RecipeIngredientDetailFragment ingredientFragment = new RecipeIngredientDetailFragment();

            Bundle ingredientBundle = new Bundle();
            bundle.putParcelableArrayList(RecipeIngredientDetailFragment.INGREDIENT_BUNDLE, ingredients);
            ingredientFragment.setArguments(ingredientBundle);

            fragmentManager.beginTransaction().add(R.id.list_fragment, recipeListFragment).commit();

            fragmentManager.beginTransaction().add(R.id.detail_fragment, ingredientFragment).commit();


        } else {

            recyclerView = findViewById(R.id.steps_recycler_view);
            if(getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            IngredientsStepsAdapter adapter = new IngredientsStepsAdapter(this, steps, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            recyclerView.setAdapter(adapter);
            if (savedInstanceState != null) {
                Parcelable savedRecyclerPosition = savedInstanceState.getParcelable(BUNDLE_RECYCLER_POSITION);
                recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerPosition);

            }
        }
    }

    @Override
    public void onClick(int position) {
        if (isTablet) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (position == 0) {
                RecipeIngredientDetailFragment ingredientFragment = new RecipeIngredientDetailFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(RecipeIngredientDetailFragment.INGREDIENT_BUNDLE, ingredients);
                ingredientFragment.setArguments(bundle);

                fragmentManager.beginTransaction().replace(R.id.detail_fragment, ingredientFragment).commit();

            } else {
                RecipeStepDetailFragment stepsFragment = new RecipeStepDetailFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelable(RecipeStepDetailFragment.STEP_BUNDLE, steps.get(position - 1));
                stepsFragment.setArguments(bundle);

                fragmentManager.beginTransaction().replace(R.id.detail_fragment, stepsFragment).commit();

            }
        } else {
            if (position == 0) {
                Intent intent = new Intent(this, IngredientActivity.class);
                intent.putExtra(IngredientActivity.INGREDIENT_EXTRA, ingredients);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, StepActivity.class);
                intent.putExtra(StepActivity.STEP_EXTRA, steps.get(position - 1));
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_POSITION, recyclerView.getLayoutManager().onSaveInstanceState());
    }
}
