package com.example.smartpantry.logic;

import com.example.smartpantry.logic.UnitConverter.Amount;
import com.example.smartpantry.model.PantryItem;
import com.example.smartpantry.model.Recipe;
import com.example.smartpantry.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The strict-matching rule (assignment section 2.3).
 *
 * A recipe is suggested ONLY if every ingredient it needs is in the pantry in at
 * least the required quantity. Steps:
 *   1. Total up the pantry per (ingredient, dimension) in base units.
 *   2. For each recipe ingredient, look up that total and compare.
 *   3. Count how many ingredients are missing/insufficient.
 *      0 missing -> strict match; exactly 1 missing -> "Almost There" (stretch goal).
 *
 * This class has no Android dependencies, so it can be unit tested on the JVM.
 */
public final class RecipeMatcher {

    private static final double EPSILON = 1e-9;   // avoids floating-point rounding surprises

    private RecipeMatcher() { }

    /** Recipes the user can cook right now: nothing missing at all. */
    public static List<Recipe> findStrictMatches(List<Recipe> recipes, List<PantryItem> pantry) {
        return findByMissingCount(recipes, pantry, 0);
    }

    /** Stretch goal: recipes missing exactly one ingredient. Show in a SEPARATE list. */
    public static List<Recipe> findAlmostThere(List<Recipe> recipes, List<PantryItem> pantry) {
        return findByMissingCount(recipes, pantry, 1);
    }

    private static List<Recipe> findByMissingCount(List<Recipe> recipes, List<PantryItem> pantry, int wanted) {
        Map<String, Double> totals = buildPantryTotals(pantry);
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            // a recipe with no ingredients is data noise, never suggest it
            if (!recipe.getIngredients().isEmpty() && countMissing(recipe, totals) == wanted) {
                result.add(recipe);
            }
        }
        return result;
    }

    /**
     * Sums the pantry into a map keyed by "normalisedName|DIMENSION".
     * Two pantry entries for the same ingredient (e.g. 300 g + 1 kg flour) are added together.
     * Items with an unknown unit or a non-positive quantity are ignored.
     */
    static Map<String, Double> buildPantryTotals(List<PantryItem> pantry) {
        Map<String, Double> totals = new HashMap<>();
        for (PantryItem item : pantry) {
            String name = IngredientNormalizer.normalize(item.getName());
            Amount amount = UnitConverter.toBase(item.getQuantity(), item.getUnit());
            if (name.isEmpty() || amount == null || amount.baseValue <= 0) {
                continue;
            }
            String key = key(name, amount.dimension);
            Double current = totals.get(key);
            totals.put(key, (current == null ? 0 : current) + amount.baseValue);
        }
        return totals;
    }

    /** How many of the recipe's ingredients are absent or insufficient in the pantry. */
    static int countMissing(Recipe recipe, Map<String, Double> pantryTotals) {
        int missing = 0;
        for (RecipeIngredient needed : recipe.getIngredients()) {
            String name = IngredientNormalizer.normalize(needed.getName());
            Amount required = UnitConverter.toBase(needed.getQuantity(), needed.getUnit());
            if (name.isEmpty() || required == null) {
                missing++;      // cannot verify -> treat as missing (strict)
                continue;
            }
            Double have = pantryTotals.get(key(name, required.dimension));
            if (have == null || have + EPSILON < required.baseValue) {
                missing++;
            }
        }
        return missing;
    }

    private static String key(String normalisedName, UnitConverter.Dimension dimension) {
        return normalisedName + "|" + dimension.name();
    }
}