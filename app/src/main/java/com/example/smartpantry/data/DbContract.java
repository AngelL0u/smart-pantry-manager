package com.example.smartpantry.data;

import android.provider.BaseColumns;

/**
 * Table and column names for the Smart Pantry database.
 * Keeping them in one place avoids typos scattered across the DAO code.
 */
public final class DbContract {

    private DbContract() { }

    public static final class PantryEntry implements BaseColumns {
        public static final String TABLE_NAME = "pantry_items";
        public static final String COL_NAME = "name";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_UNIT = "unit";
        public static final String COL_EXPIRY = "expiry_date"; // nullable TEXT, yyyy-MM-dd
    }

    public static final class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipes";
        public static final String COL_NAME = "name";
        public static final String COL_STEPS = "steps";
    }

    public static final class RecipeIngredientEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipe_ingredients";
        public static final String COL_RECIPE_ID = "recipe_id"; // FK -> recipes._id
        public static final String COL_NAME = "name";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_UNIT = "unit";
    }
}