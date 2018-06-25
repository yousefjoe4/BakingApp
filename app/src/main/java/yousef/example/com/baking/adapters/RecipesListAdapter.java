package yousef.example.com.baking.adapters;

        import android.content.Context;
        import yousef.example.com.baking.R;
        import yousef.example.com.baking.objects.Recipe;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;

        import butterknife.BindView;
        import butterknife.ButterKnife;

/**
 * Created by yousef on 16/5/2018.
 */

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipeViewHolder> {
    Context context;
    ArrayList<Recipe> recipesList;
    onItemClick onItemClick;

    public interface onItemClick {
        void onClick(int position);
    }

    public RecipesListAdapter(Context context, ArrayList<Recipe> recipesList, onItemClick onItemClick) {
        this.context = context;
        this.recipesList = recipesList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.cardViewTextView.setText(recipesList.get(position).getRecipeName());
        String imageUrl = recipesList.get(position).getImageUrl();
        if(imageUrl != null && !(imageUrl.equals(""))){
            Picasso.get().
                    load(imageUrl).
                    placeholder(R.drawable.ic_image_black_24dp).
                    error(R.drawable.ic_image_black_24dp).
                    into(holder.cardViewImageView);
        }
    }

    @Override
    public int getItemCount() {
        Log.e("RecipeAdapter", "in getItemCount");
        if(recipesList == null){
            return 0;
        }
        return recipesList.size();
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card_view_text)
        TextView cardViewTextView;
        @BindView(R.id.card_view_image)
        ImageView cardViewImageView;
        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onClick(getLayoutPosition());

        }
    }

    public void swapData(ArrayList<Recipe> recipesList){
        this.recipesList = recipesList;
        notifyDataSetChanged();
    }
}
