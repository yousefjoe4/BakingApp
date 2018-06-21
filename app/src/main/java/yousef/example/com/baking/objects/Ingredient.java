package yousef.example.com.baking.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yousef on 17/5/2018.
 */

public class Ingredient implements Parcelable {
    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient(int quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
    public String getMeasure() {
        return measure;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public Ingredient(Parcel in){
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(quantity);
    dest.writeString(measure);
    dest.writeString(ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[0];
        }
    };
}
