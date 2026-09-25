package com.example.smartpantry.model;

/**
 * One ingredient line inside a recipe, e.g. "200 g pasta".
 * Maps to a row in the recipe_ingredients table.
 */
public class RecipeIngredient {
    private final String name;
    private final double quantity;
    private final String unit;

    public RecipeIngredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() { return name; }
    public double getQuantity() { return quantity; }
    public String getUnit() { return unit; }
}