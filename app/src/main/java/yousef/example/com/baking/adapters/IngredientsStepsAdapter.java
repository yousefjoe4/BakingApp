package yousef.example.com.baking.adapters;

import android.content.Context;
import yousef.example.com.baking.R;
import yousef.example.com.baking.objects.Steps;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsStepsAdapter extends RecyclerView.Adapter<IngredientsStepsAdapter.StepsHolder> {
    Context context;
    ArrayList<Steps> stepsList;
    OnItemClick onItemClick;
    public static final String INGREDIENTS_TEXT = "Ingredients";

    public interface OnItemClick{
        void onClick(int position);
    }
    public IngredientsStepsAdapter(Context context, ArrayList<Steps> stepsList,OnItemClick onItemClick) {
        this.context = context;
        this.stepsList = stepsList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public StepsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_step_view, parent, false);
        view.getLayoutParams().height = 150;
        view.setLayoutParams(view.getLayoutParams());
        return new StepsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsHolder holder, int position) {
        if (position == 0) {
            holder.namesTextView.setText(INGREDIENTS_TEXT);
        } else {
            holder.namesTextView.setText(stepsList.get(position -1).getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        return stepsList.size() + 1;
    }

    class StepsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ingredient_step_view)
        TextView namesTextView;
        StepsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onClick(getLayoutPosition());
        }
    }
}
