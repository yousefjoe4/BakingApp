package yousef.example.com.baking.widget;

import android.app.Activity;
import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import yousef.example.com.baking.utils.JsonUtils;
import yousef.example.com.baking.R;
import yousef.example.com.baking.adapters.ListViewService;
import yousef.example.com.baking.adapters.RecipesListAdapter;
import yousef.example.com.baking.objects.Recipe;

import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * The configuration screen for the {@link BakingAppWidget BakingAppWidget} AppWidget.
 */
public class BakingAppWidgetConfigureActivity extends Activity implements RecipesListAdapter.onItemClick, LoaderManager.LoaderCallbacks<ArrayList<Recipe>>{
    ArrayList<Recipe> recipeArrayList;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    RecipesListAdapter recipesListAdapter;
    RecyclerView recyclerView;
    public BakingAppWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.baking_app_widget_configure);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }


        getData();
        recyclerView = findViewById(R.id.widgetActivity_recycler_view);
        recipesListAdapter = new RecipesListAdapter(this, recipeArrayList, this);
        recyclerView.setAdapter(recipesListAdapter);
        recyclerView.setHasFixedSize(true);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if (isTablet) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    @Override
    public void onClick(int position) {

        int ingredientsID = recipeArrayList.get(position).getId();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.baking_app_widget);

        Intent intent = new Intent(this, ListViewService.class);
        intent.putExtra(ListViewService.WIDGET_INGREDIENTS_EXTRA, ingredientsID);
        intent.putExtra("appWidgetId", mAppWidgetId);

        remoteViews.setRemoteAdapter(R.id.appwidget_listView, intent);

        appWidgetManager.updateAppWidget(mAppWidgetId, remoteViews);
        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {

            @Override
            public ArrayList<Recipe> loadInBackground() {

                return JsonUtils.getDataArrayList();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        recipeArrayList = data;
        recipesListAdapter.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

    }

    public void getData(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                getLoaderManager().initLoader(0, null, this).forceLoad();
            }
        }
    }

}

