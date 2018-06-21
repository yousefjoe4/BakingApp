package yousef.example.com.baking.adapters;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

import yousef.example.com.baking.R;
import yousef.example.com.baking.objects.Ingredient;
import yousef.example.com.baking.utils.JsonUtils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by yousef on 27/5/2018.
 */

public class ListViewService extends RemoteViewsService {
    public static final String WIDGET_INGREDIENTS_EXTRA = "widget_ingredients_extra";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int ingredientsID = intent.getIntExtra(WIDGET_INGREDIENTS_EXTRA, 0);
        int appWidgetId = intent.getIntExtra("appWidgetId", AppWidgetManager.INVALID_APPWIDGET_ID);

        return new ListRemoteViewsFactory(this.getApplicationContext(), ingredientsID, appWidgetId);
    }

    class ListRemoteViewsFactory implements RemoteViewsFactory {
        int ingredientsID;
        Context context;
        ArrayList<Ingredient> ingredients;
        int appWidgetId;

        ListRemoteViewsFactory(Context context, int ingredientsID, int appWidgetId) {
            this.ingredientsID = ingredientsID;
            this.context = context;
            this.appWidgetId = appWidgetId;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    ingredients = JsonUtils.getIngredientsById(ingredientsID);
                    Log.e("ListView", "In onDataSetChanged size = " + ingredients.size());

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId,R.id.appwidget_listView);
                }
            }.execute();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            Log.e("ListView", "In get count ");
            if (ingredients == null) {
                return 0;
            }
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_view);

            Ingredient ingredient = ingredients.get(position);
            String ingredientText = ingredient.getIngredient();
            String measure = ingredient.getMeasure();
            int quantity = ingredient.getQuantity();

            String quantityMeasure = quantity + " " + measure;

            remoteViews.setTextViewText(R.id.tv_ingredient, ingredientText);
            remoteViews.setTextViewText(R.id.tv_measure, quantityMeasure);
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
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
