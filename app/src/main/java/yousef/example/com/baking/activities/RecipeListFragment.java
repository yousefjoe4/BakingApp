package yousef.example.com.baking.activities;


import android.content.Context;
import yousef.example.com.baking.adapters.IngredientsStepsAdapter;
import yousef.example.com.baking.objects.Steps;
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
public class RecipeListFragment extends Fragment {
    @BindView(R.id.fragment_recipe_recycler_view)
    RecyclerView recyclerView;

    public static final String LIST_BUNDLE = "steps_bundle";
    ArrayList<Steps> stepsArrayList;
    IngredientsStepsAdapter.OnItemClick  onItemClick;



    public RecipeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        Bundle bundle = getArguments();
        if ( bundle != null && bundle.containsKey(LIST_BUNDLE)) {
            stepsArrayList = bundle.getParcelableArrayList(LIST_BUNDLE);
        }
        ButterKnife.bind(this, view);

        IngredientsStepsAdapter adapter = new IngredientsStepsAdapter(getActivity(), stepsArrayList, onItemClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onItemClick = (IngredientsStepsAdapter.OnItemClick) context;
    }
}
