package yousef.example.com.baking.adapters;

import android.content.Context;
import yousef.example.com.baking.R;
import yousef.example.com.baking.objects.Ingredient;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yousef on 20/5/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {
    Context context;
    ArrayList<Ingredient> ingredients;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_view, parent, false);

        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        String ingredient = ingredients.get(position).getIngredient();
        holder.ingredient.setText(ingredient);

        int quantity = ingredients.get(position).getQuantity();
        String measure = ingredients.get(position).getMeasure();
        String measureQuantity = quantity + " " + measure;

        holder.measure.setText(measureQuantity);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient)
        TextView ingredient;
        @BindView(R.id.tv_measure)
        TextView measure;

        public IngredientHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

}
