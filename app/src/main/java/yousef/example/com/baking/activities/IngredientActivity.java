package yousef.example.com.baking.activities;

import yousef.example.com.baking.R;
import yousef.example.com.baking.adapters.IngredientAdapter;
import yousef.example.com.baking.objects.Ingredient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity {
    @BindView(R.id.ingredients_recycler_view)
    RecyclerView recyclerView;

    public static final String INGREDIENT_EXTRA = "ingredient_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);

        setTitle("Ingredients");
        if (getIntent().hasExtra(INGREDIENT_EXTRA)) {
            ArrayList<Ingredient> ingredients = getIntent().getParcelableArrayListExtra(INGREDIENT_EXTRA);

            IngredientAdapter ingredientAdapter = new IngredientAdapter(this, ingredients);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(ingredientAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return onOptionsItemSelected(item);
    }
}
