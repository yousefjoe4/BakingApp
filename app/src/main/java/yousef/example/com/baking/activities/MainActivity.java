package yousef.example.com.baking.activities;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;

import yousef.example.com.baking.idlingResource.SimpleIdlingResource;
import yousef.example.com.baking.utils.JsonUtils;
import yousef.example.com.baking.R;
import yousef.example.com.baking.objects.Recipe;
import yousef.example.com.baking.adapters.RecipesListAdapter;

import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>, RecipesListAdapter.onItemClick {
    ArrayList<Recipe> recipeArrayList;
    @BindView(R.id.recipe_recycler_view)
    RecyclerView recyclerView;
    RecipesListAdapter recipesListAdapter;
    SimpleIdlingResource mIdlingResource;
    private static final String BUNDLE_RECYCLER_POSITION = "bundle_recycler_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recipesListAdapter = new RecipesListAdapter(this, null, this);
        recyclerView.setAdapter(recipesListAdapter);
        recyclerView.setHasFixedSize(true);


        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if (isTablet) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        if (savedInstanceState != null) {
            Parcelable savedRecyclerPosition = savedInstanceState.getParcelable(BUNDLE_RECYCLER_POSITION);
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerPosition);
        }

        getIdlingResource();
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.RECIPE_EXTRA, recipeArrayList.get(position));
        startActivity(intent);
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {

            @Override
            public ArrayList<Recipe> loadInBackground() {
                if(mIdlingResource != null){
                    mIdlingResource.setIdlingState(false);
                }
                return JsonUtils.getDataArrayList();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        recipeArrayList = data;
        recipesListAdapter.swapData(data);
        mIdlingResource.setIdlingState(true);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_POSITION, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    public IdlingResource getIdlingResource(){
        if(mIdlingResource == null){
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onResume() {
        super.onResume();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                getLoaderManager().initLoader(0, null, this).forceLoad();
            }
        }
    }
}

