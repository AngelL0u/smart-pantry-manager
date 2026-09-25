package com.example.smartpantry.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.smartpantry.model.PantryItem;
import com.example.smartpantry.model.Recipe;
import com.example.smartpantry.model.RecipeIngredient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Goes in app/src/test/java/com/example/smartpantry/logic/ (plain JVM test). */
public class RecipeMatcherTest {

    private PantryItem pantry(String name, double qty, String unit) {
        return new PantryItem(0, name, qty, unit, null);
    }

    private Recipe recipe(String name, RecipeIngredient... ings) {
        return new Recipe(1, name, "steps", Arrays.asList(ings));
    }

    private Recipe tomatoPasta() {
        return recipe("Tomato Pasta",
                new RecipeIngredient("Tomato", 2, "pcs"),
                new RecipeIngredient("Pasta", 200, "g"),
                new RecipeIngredient("Olive oil", 1, "tbsp"),
                new RecipeIngredient("Garlic", 2, "cloves"),
                new RecipeIngredient("Salt", 1, "tsp"));
    }

    @Test
    public void allIngredientsPresent_recipeIsSuggested() {
        List<PantryItem> p = Arrays.asList(
                pantry("Tomatoes", 4, "pcs"), pantry("Pasta", 500, "g"),
                pantry("olive oil", 100, "ml"), pantry("Garlic", 5, "cloves"),
                pantry("Salt", 50, "g" ) // wrong dimension on purpose, see next test
        );
        // salt is in grams but the recipe wants tsp (volume) -> cannot verify -> not suggested
        assertTrue(RecipeMatcher.findStrictMatches(Arrays.asList(tomatoPasta()), p).isEmpty());

        p = new ArrayList<>(p.subList(0, 4));
        p.add(pantry("Salt", 100, "ml"));
        assertEquals(1, RecipeMatcher.findStrictMatches(Arrays.asList(tomatoPasta()), p).size());
    }

    @Test
    public void fourOfFiveIngredients_isExcluded() {
        List<PantryItem> p = Arrays.asList(
                pantry("Tomatoes", 4, "pcs"), pantry("Pasta", 500, "g"),
                pantry("Olive oil", 100, "ml"), pantry("Garlic", 5, "cloves"));
        assertTrue(RecipeMatcher.findStrictMatches(Arrays.asList(tomatoPasta()), p).isEmpty());
        // ...but it does qualify for the optional "Almost There" list
        assertEquals(1, RecipeMatcher.findAlmostThere(Arrays.asList(tomatoPasta()), p).size());
    }

    @Test
    public void singularAndPluralNamesMatch() {
        Recipe r = recipe("Tomato Toast", new RecipeIngredient("tomato", 1, "pcs"));
        List<PantryItem> p = Arrays.asList(pantry("  TOMATOES ", 3, "pcs"));
        assertEquals(1, RecipeMatcher.findStrictMatches(Arrays.asList(r), p).size());
    }

    @Test
    public void unitDifferencesAreConverted() {
        Recipe r = recipe("Bread", new RecipeIngredient("Flour", 500, "g"));
        assertEquals(1, RecipeMatcher.findStrictMatches(Arrays.asList(r),
                Arrays.asList(pantry("flour", 1, "kg"))).size());
    }

    @Test
    public void insufficientQuantityIsExcluded() {
        Recipe r = recipe("Bread", new RecipeIngredient("Flour", 500, "g"));
        assertTrue(RecipeMatcher.findStrictMatches(Arrays.asList(r),
                Arrays.asList(pantry("flour", 0.3, "kg"))).isEmpty());
    }

    @Test
    public void duplicatePantryEntriesAreSummed() {
        Recipe r = recipe("Bread", new RecipeIngredient("Flour", 500, "g"));
        List<PantryItem> p = Arrays.asList(pantry("Flour", 300, "g"), pantry("flour", 300, "g"));
        assertEquals(1, RecipeMatcher.findStrictMatches(Arrays.asList(r), p).size());
    }

    @Test
    public void emptyPantryReturnsNoMatches() {
        assertTrue(RecipeMatcher.findStrictMatches(Arrays.asList(tomatoPasta()),
                new ArrayList<PantryItem>()).isEmpty());
    }
}