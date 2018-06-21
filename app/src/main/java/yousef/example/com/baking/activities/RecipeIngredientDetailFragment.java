package yousef.example.com.baking.activities;


import yousef.example.com.baking.adapters.IngredientAdapter;
import yousef.example.com.baking.objects.Ingredient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yousef.example.com.baking.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientDetailFragment extends Fragment {
    @BindView(R.id.fragment_ingredients_recycler_view)
    RecyclerView recyclerView;

    public static final String INGREDIENT_BUNDLE = "ingredient_bundle";

    public RecipeIngredientDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_recipe_ingredient_detail, container, false);

        if(getArguments().containsKey(INGREDIENT_BUNDLE)){
           ArrayList<Ingredient> ingredients =  getArguments().getParcelableArrayList(INGREDIENT_BUNDLE);

            ButterKnife.bind(this, view);

            IngredientAdapter ingredientAdapter = new IngredientAdapter(getActivity(), ingredients);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(ingredientAdapter);
            return view;
        }
        return null;
    }


}
