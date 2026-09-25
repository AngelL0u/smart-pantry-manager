package com.example.smartpantry.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A recipe with its required ingredients and preparation steps.
 * Maps to the recipes table (ingredients live in recipe_ingredients).
 */
public class Recipe {
    private final long id;
    private final String name;
    private final String steps;                       // simple text, one step per line
    private final List<RecipeIngredient> ingredients;

    public Recipe(long id, String name, String steps, List<RecipeIngredient> ingredients) {
        this.id = id;
        this.name = name;
        this.steps = steps;
        this.ingredients = ingredients != null ? ingredients : new ArrayList<RecipeIngredient>();
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getSteps() { return steps; }
    public List<RecipeIngredient> getIngredients() { return ingredients; }
}