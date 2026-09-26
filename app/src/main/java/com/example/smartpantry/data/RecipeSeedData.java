package com.example.smartpantry.data;

import com.example.smartpantry.model.Recipe;
import com.example.smartpantry.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The 18 recipes loaded into the database the first time the app runs.
 * IDs are left as 0 here; DatabaseHelper assigns real IDs on insert.
 */
final class RecipeSeedData {

    private RecipeSeedData() { }

    static List<Recipe> all() {
        List<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe(0, "Tomato Pasta", joinSteps(
                "Boil pasta until al dente.",
                "Fry garlic in olive oil until fragrant.",
                "Add chopped tomato, salt and simmer 5 minutes.",
                "Toss pasta through the sauce and serve."),
                Arrays.asList(
                        new RecipeIngredient("pasta", 200, "g"),
                        new RecipeIngredient("tomato", 2, "pcs"),
                        new RecipeIngredient("garlic", 2, "cloves"),
                        new RecipeIngredient("olive oil", 1, "tbsp"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Scrambled Eggs on Toast", joinSteps(
                "Whisk eggs with a splash of milk and salt.",
                "Melt butter in a pan and scramble the eggs on low heat.",
                "Toast the bread and serve the eggs on top."),
                Arrays.asList(
                        new RecipeIngredient("egg", 3, "pcs"),
                        new RecipeIngredient("bread", 2, "slices"),
                        new RecipeIngredient("butter", 15, "g"),
                        new RecipeIngredient("milk", 30, "ml"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Garlic Fried Rice", joinSteps(
                "Fry minced garlic in oil until golden.",
                "Add cooked rice and stir-fry until heated through.",
                "Season with salt and serve."),
                Arrays.asList(
                        new RecipeIngredient("rice", 300, "g"),
                        new RecipeIngredient("garlic", 3, "cloves"),
                        new RecipeIngredient("olive oil", 2, "tbsp"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Cheese Omelette", joinSteps(
                "Whisk the eggs with salt.",
                "Pour into a hot buttered pan.",
                "Sprinkle cheese on one half, fold and serve."),
                Arrays.asList(
                        new RecipeIngredient("egg", 2, "pcs"),
                        new RecipeIngredient("cheese", 50, "g"),
                        new RecipeIngredient("butter", 10, "g"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Chicken Stir Fry", joinSteps(
                "Slice chicken and stir-fry in oil until cooked.",
                "Add chopped onion and garlic, cook until soft.",
                "Season with salt and serve hot."),
                Arrays.asList(
                        new RecipeIngredient("chicken breast", 300, "g"),
                        new RecipeIngredient("onion", 1, "pcs"),
                        new RecipeIngredient("garlic", 2, "cloves"),
                        new RecipeIngredient("olive oil", 1, "tbsp"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Tuna Sandwich", joinSteps(
                "Mix tuna with mayonnaise.",
                "Spread onto bread slices.",
                "Add lettuce and close the sandwich."),
                Arrays.asList(
                        new RecipeIngredient("tuna", 1, "can"),
                        new RecipeIngredient("bread", 2, "slices"),
                        new RecipeIngredient("mayonnaise", 30, "g"),
                        new RecipeIngredient("lettuce", 2, "leaves"))));

        recipes.add(new Recipe(0, "Vegetable Soup", joinSteps(
                "Chop all vegetables into small pieces.",
                "Simmer in water with salt for 20 minutes.",
                "Serve hot."),
                Arrays.asList(
                        new RecipeIngredient("carrot", 2, "pcs"),
                        new RecipeIngredient("potato", 2, "pcs"),
                        new RecipeIngredient("onion", 1, "pcs"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Banana Pancakes", joinSteps(
                "Mash the banana and mix with flour, egg and milk.",
                "Cook spoonfuls of batter in a buttered pan until golden.",
                "Serve warm."),
                Arrays.asList(
                        new RecipeIngredient("banana", 1, "pcs"),
                        new RecipeIngredient("flour", 100, "g"),
                        new RecipeIngredient("egg", 1, "pcs"),
                        new RecipeIngredient("milk", 100, "ml"),
                        new RecipeIngredient("butter", 10, "g"))));

        recipes.add(new Recipe(0, "Grilled Cheese Sandwich", joinSteps(
                "Butter the outside of two bread slices.",
                "Place cheese between the unbuttered sides.",
                "Grill in a pan until golden on both sides."),
                Arrays.asList(
                        new RecipeIngredient("bread", 2, "slices"),
                        new RecipeIngredient("cheese", 60, "g"),
                        new RecipeIngredient("butter", 15, "g"))));

        recipes.add(new Recipe(0, "Potato Hash", joinSteps(
                "Dice and fry potato in oil until crisp.",
                "Add chopped onion and cook until soft.",
                "Season with salt and serve."),
                Arrays.asList(
                        new RecipeIngredient("potato", 3, "pcs"),
                        new RecipeIngredient("onion", 1, "pcs"),
                        new RecipeIngredient("olive oil", 2, "tbsp"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Peanut Butter Toast", joinSteps(
                "Toast the bread.",
                "Spread peanut butter on top.",
                "Slice the banana over it and serve."),
                Arrays.asList(
                        new RecipeIngredient("bread", 2, "slices"),
                        new RecipeIngredient("peanut butter", 30, "g"),
                        new RecipeIngredient("banana", 1, "pcs"))));

        recipes.add(new Recipe(0, "Garlic Butter Rice", joinSteps(
                "Melt butter and fry garlic until fragrant.",
                "Stir in cooked rice and salt.",
                "Serve hot."),
                Arrays.asList(
                        new RecipeIngredient("rice", 250, "g"),
                        new RecipeIngredient("garlic", 2, "cloves"),
                        new RecipeIngredient("butter", 20, "g"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Egg Fried Rice", joinSteps(
                "Scramble the eggs in a hot pan and set aside.",
                "Fry rice with a little oil, then mix in the eggs.",
                "Season with salt and serve."),
                Arrays.asList(
                        new RecipeIngredient("rice", 300, "g"),
                        new RecipeIngredient("egg", 2, "pcs"),
                        new RecipeIngredient("olive oil", 1, "tbsp"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Chicken Soup", joinSteps(
                "Simmer chicken pieces in water with salt.",
                "Add chopped carrot and onion, cook until tender.",
                "Serve hot."),
                Arrays.asList(
                        new RecipeIngredient("chicken breast", 200, "g"),
                        new RecipeIngredient("carrot", 1, "pcs"),
                        new RecipeIngredient("onion", 1, "pcs"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Milk and Cereal", joinSteps(
                "Pour cereal into a bowl.",
                "Add cold milk and serve immediately."),
                Arrays.asList(
                        new RecipeIngredient("cereal", 60, "g"),
                        new RecipeIngredient("milk", 200, "ml"))));

        recipes.add(new Recipe(0, "Cheesy Baked Potato", joinSteps(
                "Bake or microwave the potato until soft.",
                "Cut open and top with butter and cheese.",
                "Season with salt and serve."),
                Arrays.asList(
                        new RecipeIngredient("potato", 1, "pcs"),
                        new RecipeIngredient("cheese", 40, "g"),
                        new RecipeIngredient("butter", 10, "g"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Onion Garlic Soup", joinSteps(
                "Fry chopped onion and garlic in butter until soft.",
                "Add water and salt, simmer 15 minutes.",
                "Serve hot."),
                Arrays.asList(
                        new RecipeIngredient("onion", 2, "pcs"),
                        new RecipeIngredient("garlic", 3, "cloves"),
                        new RecipeIngredient("butter", 15, "g"),
                        new RecipeIngredient("salt", 1, "tsp"))));

        recipes.add(new Recipe(0, "Tuna Rice Bowl", joinSteps(
                "Cook the rice.",
                "Mix in canned tuna and a little mayonnaise.",
                "Serve warm or cold."),
                Arrays.asList(
                        new RecipeIngredient("rice", 250, "g"),
                        new RecipeIngredient("tuna", 1, "can"),
                        new RecipeIngredient("mayonnaise", 20, "g"))));

        return recipes;
    }

    private static String joinSteps(String... steps) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < steps.length; i++) {
            sb.append(i + 1).append(". ").append(steps[i]);
            if (i < steps.length - 1) sb.append('\n');
        }
        return sb.toString();
    }
}

