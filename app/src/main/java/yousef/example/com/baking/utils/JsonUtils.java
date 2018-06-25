package yousef.example.com.baking.utils;

import android.content.Context;
import android.util.Log;

import yousef.example.com.baking.objects.Ingredient;
import yousef.example.com.baking.objects.Recipe;
import yousef.example.com.baking.objects.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by yousef on 16/5/2018.
 */

public class JsonUtils {
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_INGRED = "ingredients";
    private static final String KEY_STEPS = "steps";
    private static final String KEY_SERVINGS = "servings";
    private static final String KEY_IMAGE = "image";

    private static final String KEY_INGRED_QUANTITY = "quantity";
    private static final String KEY_INGRED_MEASURE = "measure";
    private static final String KEY_INGRED_INGREDIENT = "ingredient";

    private static final String KEY_STEPS_ID = "id";
    private static final String KEY_STEPS_SHORT_DESCRIPION = "shortDescription";
    private static final String KEY_STEPS_DESCRIPION = "description";
    private static final String KEY_STEPS_VIDEO_URL = "videoURL";
    private static final String KEY_STEPS_THUMBNAIL_URL = "thumbnailURL";

    private static final String DATA_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    public static ArrayList<Recipe> getDataArrayList() {
        return parseJsonData(getDataFromLink());
    }


    private static String getDataFromLink() {
        try {
            URL ur1 = new URL(DATA_URL);

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) ur1.openConnection();

            InputStream inputStream = httpsURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {

                return scanner.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static ArrayList<Recipe> parseJsonData(String jsonData) {

        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject recipeObject = jsonArray.optJSONObject(i);

                int id = recipeObject.getInt(KEY_ID);
                String name = recipeObject.optString(KEY_NAME);
                String imageUrl = recipeObject.optString(KEY_IMAGE);

                JSONArray ingredientsArray = recipeObject.optJSONArray(KEY_INGRED);
                ArrayList<Ingredient> ingredientList = new ArrayList<>();

                if (ingredientsArray != null || ingredientsArray.length() != 0) {
                    for (int j = 0; j < ingredientsArray.length(); j++) {
                        JSONObject IngredientObject = ingredientsArray.getJSONObject(j);
                        int quantity = IngredientObject.optInt(KEY_INGRED_QUANTITY);
                        String measure = IngredientObject.optString(KEY_INGRED_MEASURE);
                        String ingredient = IngredientObject.optString(KEY_INGRED_INGREDIENT);
                        ingredientList.add(new Ingredient(quantity, measure, ingredient));
                    }
                }


                JSONArray stepsArray = recipeObject.optJSONArray(KEY_STEPS);
                ArrayList<Steps> stepsList = new ArrayList<>();
                if (stepsArray != null || stepsArray.length() != 0) {
                    stepsList = new ArrayList<>();
                    for (int j = 0; j < stepsArray.length(); j++) {
                        JSONObject stepObject = stepsArray.getJSONObject(j);
                        int stepId = stepObject.optInt(KEY_STEPS_ID);
                        String shortDescription = stepObject.optString(KEY_STEPS_SHORT_DESCRIPION);
                        String description = stepObject.optString(KEY_STEPS_DESCRIPION);
                        String videoURL = stepObject.optString(KEY_STEPS_VIDEO_URL);
                        String thumbnailURL = stepObject.optString(KEY_STEPS_THUMBNAIL_URL);

                        stepsList.add(new Steps(stepId, shortDescription, description, videoURL, thumbnailURL));
                    }
                }
                recipes.add(new Recipe(id, name, imageUrl, ingredientList, stepsList));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public static ArrayList<Ingredient> getIngredientsById(int id) {
        String data = getDataFromLink();
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject recipeObject = jsonArray.optJSONObject(i);
                int objectId = recipeObject.getInt(KEY_ID);

                if (objectId == id) {
                    JSONArray ingredientsArray = recipeObject.optJSONArray(KEY_INGRED);
                    if (ingredientsArray != null || ingredientsArray.length() != 0) {
                        for (int j = 0; j < ingredientsArray.length(); j++) {
                            JSONObject IngredientObject = ingredientsArray.getJSONObject(j);
                            int quantity = IngredientObject.optInt(KEY_INGRED_QUANTITY);
                            String measure = IngredientObject.optString(KEY_INGRED_MEASURE);
                            String ingredient = IngredientObject.optString(KEY_INGRED_INGREDIENT);
                            ingredientList.add(new Ingredient(quantity, measure, ingredient));
                        }
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredientList;
    }

}