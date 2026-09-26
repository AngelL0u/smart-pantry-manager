package com.example.smartpantry.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.smartpantry.data.DbContract.PantryEntry;
import com.example.smartpantry.data.DbContract.RecipeEntry;
import com.example.smartpantry.data.DbContract.RecipeIngredientEntry;
import com.example.smartpantry.model.PantryItem;
import com.example.smartpantry.model.Recipe;
import com.example.smartpantry.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Single point of access to the SQLite database.
 * Seeds 18 recipes the first time the database is created (see onCreate).
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PantryEntry.TABLE_NAME + " (" +
                PantryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PantryEntry.COL_NAME + " TEXT NOT NULL, " +
                PantryEntry.COL_QUANTITY + " REAL NOT NULL, " +
                PantryEntry.COL_UNIT + " TEXT NOT NULL, " +
                PantryEntry.COL_EXPIRY + " TEXT)");

        db.execSQL("CREATE TABLE " + RecipeEntry.TABLE_NAME + " (" +
                RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RecipeEntry.COL_NAME + " TEXT NOT NULL, " +
                RecipeEntry.COL_STEPS + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + RecipeIngredientEntry.TABLE_NAME + " (" +
                RecipeIngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RecipeIngredientEntry.COL_RECIPE_ID + " INTEGER NOT NULL, " +
                RecipeIngredientEntry.COL_NAME + " TEXT NOT NULL, " +
                RecipeIngredientEntry.COL_QUANTITY + " REAL NOT NULL, " +
                RecipeIngredientEntry.COL_UNIT + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + RecipeIngredientEntry.COL_RECIPE_ID + ") REFERENCES " +
                RecipeEntry.TABLE_NAME + "(" + RecipeEntry._ID + "))");

        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RecipeIngredientEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PantryEntry.TABLE_NAME);
        onCreate(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        for (Recipe recipe : RecipeSeedData.all()) {
            ContentValues recipeValues = new ContentValues();
            recipeValues.put(RecipeEntry.COL_NAME, recipe.getName());
            recipeValues.put(RecipeEntry.COL_STEPS, recipe.getSteps());
            long recipeId = db.insert(RecipeEntry.TABLE_NAME, null, recipeValues);

            for (RecipeIngredient ing : recipe.getIngredients()) {
                ContentValues ingValues = new ContentValues();
                ingValues.put(RecipeIngredientEntry.COL_RECIPE_ID, recipeId);
                ingValues.put(RecipeIngredientEntry.COL_NAME, ing.getName());
                ingValues.put(RecipeIngredientEntry.COL_QUANTITY, ing.getQuantity());
                ingValues.put(RecipeIngredientEntry.COL_UNIT, ing.getUnit());
                db.insert(RecipeIngredientEntry.TABLE_NAME, null, ingValues);
            }
        }
    }

    // ---------------------------------------------------------------
    // Pantry CRUD
    // ---------------------------------------------------------------

    /** Create. Returns the new row id, or -1 on failure. */
    public long addPantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PantryEntry.COL_NAME, item.getName());
        values.put(PantryEntry.COL_QUANTITY, item.getQuantity());
        values.put(PantryEntry.COL_UNIT, item.getUnit());
        values.put(PantryEntry.COL_EXPIRY, item.getExpiryDate());
        return db.insert(PantryEntry.TABLE_NAME, null, values);
    }

    /** Read all, newest first. */
    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(PantryEntry.TABLE_NAME, null, null, null, null, null,
                PantryEntry._ID + " DESC");
        try {
            while (c.moveToNext()) {
                items.add(pantryItemFromCursor(c));
            }
        } finally {
            c.close();
        }
        return items;
    }

    /** Update. Returns number of rows affected (should be 0 or 1). */
    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PantryEntry.COL_NAME, item.getName());
        values.put(PantryEntry.COL_QUANTITY, item.getQuantity());
        values.put(PantryEntry.COL_UNIT, item.getUnit());
        values.put(PantryEntry.COL_EXPIRY, item.getExpiryDate());
        return db.update(PantryEntry.TABLE_NAME, values,
                PantryEntry._ID + " = ?", new String[]{String.valueOf(item.getId())});
    }

    /** Delete. Returns number of rows removed (should be 0 or 1). */
    public int deletePantryItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(PantryEntry.TABLE_NAME, PantryEntry._ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    private PantryItem pantryItemFromCursor(Cursor c) {
        long id = c.getLong(c.getColumnIndexOrThrow(PantryEntry._ID));
        String name = c.getString(c.getColumnIndexOrThrow(PantryEntry.COL_NAME));
        double qty = c.getDouble(c.getColumnIndexOrThrow(PantryEntry.COL_QUANTITY));
        String unit = c.getString(c.getColumnIndexOrThrow(PantryEntry.COL_UNIT));
        int expiryIdx = c.getColumnIndexOrThrow(PantryEntry.COL_EXPIRY);
        String expiry = c.isNull(expiryIdx) ? null : c.getString(expiryIdx);
        return new PantryItem(id, name, qty, unit, expiry);
    }

    // ---------------------------------------------------------------
    // Recipe reads (recipes are seeded once; the app does not edit them)
    // ---------------------------------------------------------------

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(RecipeEntry.TABLE_NAME, null, null, null, null, null,
                RecipeEntry.COL_NAME + " ASC");
        try {
            while (c.moveToNext()) {
                long id = c.getLong(c.getColumnIndexOrThrow(RecipeEntry._ID));
                String name = c.getString(c.getColumnIndexOrThrow(RecipeEntry.COL_NAME));
                String steps = c.getString(c.getColumnIndexOrThrow(RecipeEntry.COL_STEPS));
                recipes.add(new Recipe(id, name, steps, getIngredientsFor(db, id)));
            }
        } finally {
            c.close();
        }
        return recipes;
    }

    public Recipe getRecipeById(long recipeId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(RecipeEntry.TABLE_NAME, null,
                RecipeEntry._ID + " = ?", new String[]{String.valueOf(recipeId)},
                null, null, null);
        try {
            if (c.moveToFirst()) {
                String name = c.getString(c.getColumnIndexOrThrow(RecipeEntry.COL_NAME));
                String steps = c.getString(c.getColumnIndexOrThrow(RecipeEntry.COL_STEPS));
                return new Recipe(recipeId, name, steps, getIngredientsFor(db, recipeId));
            }
            return null;
        } finally {
            c.close();
        }
    }

    private List<RecipeIngredient> getIngredientsFor(SQLiteDatabase db, long recipeId) {
        List<RecipeIngredient> ingredients = new ArrayList<>();
        Cursor c = db.query(RecipeIngredientEntry.TABLE_NAME, null,
                RecipeIngredientEntry.COL_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)}, null, null, null);
        try {
            while (c.moveToNext()) {
                String name = c.getString(c.getColumnIndexOrThrow(RecipeIngredientEntry.COL_NAME));
                double qty = c.getDouble(c.getColumnIndexOrThrow(RecipeIngredientEntry.COL_QUANTITY));
                String unit = c.getString(c.getColumnIndexOrThrow(RecipeIngredientEntry.COL_UNIT));
                ingredients.add(new RecipeIngredient(name, qty, unit));
            }
        } finally {
            c.close();
        }
        return ingredients;
    }
}