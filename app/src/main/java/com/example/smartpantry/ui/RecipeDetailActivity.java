package com.example.smartpantry.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantry.R;
import com.example.smartpantry.data.DatabaseHelper;
import com.example.smartpantry.model.Recipe;
import com.example.smartpantry.model.RecipeIngredient;

/** Shows the full ingredient list and method for a single recipe. */
public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        long recipeId = getIntent().getLongExtra(EXTRA_RECIPE_ID, -1);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Recipe recipe = databaseHelper.getRecipeById(recipeId);

        TextView titleView = findViewById(R.id.textRecipeTitle);
        TextView ingredientsView = findViewById(R.id.textIngredientsList);
        TextView stepsView = findViewById(R.id.textStepsList);

        if (recipe == null) {
            titleView.setText("Recipe not found");
            return;
        }

        setTitle(recipe.getName());
        titleView.setText(recipe.getName());
        ingredientsView.setText(formatIngredients(recipe));
        stepsView.setText(recipe.getSteps());
    }

    private String formatIngredients(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        for (RecipeIngredient ing : recipe.getIngredients()) {
            sb.append("\u2022 ")
                    .append(formatQuantity(ing.getQuantity()))
                    .append(" ")
                    .append(ing.getUnit())
                    .append(" ")
                    .append(ing.getName())
                    .append("\n");
        }
        // drop the trailing newline
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    }

    private String formatQuantity(double quantity) {
        if (quantity == Math.floor(quantity) && !Double.isInfinite(quantity)) {
            return String.valueOf((long) quantity);
        }
        return String.valueOf(quantity);
    }
}