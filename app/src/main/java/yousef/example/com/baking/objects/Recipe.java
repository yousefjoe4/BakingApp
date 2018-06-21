package yousef.example.com.baking.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by yousef on 16/5/2018.
 */

public class Recipe implements Parcelable {
    private String recipeName;
    private int id;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Steps> steps;
    private String imageUrl;


    public Recipe(int id, String recipeName, String imageUrl, ArrayList<Ingredient> ingredients, ArrayList<Steps> steps) {
        this.id = id;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.steps = steps;
        this.imageUrl = imageUrl;

    }

    public int getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        recipeName = in.readString();
        imageUrl = in.readString();
        ingredients = new ArrayList<>();
        in.readList(ingredients, Ingredient.class.getClassLoader());
        steps = new ArrayList<>();
        in.readList(steps, Steps.class.getClassLoader());

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(recipeName);
        dest.writeString(imageUrl);
        dest.writeList(ingredients);
        dest.writeList(steps);

    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}