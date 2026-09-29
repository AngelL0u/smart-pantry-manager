# Smart Pantry Manager

An Android app (Java) that helps reduce food waste by tracking ingredients you
already have at home and suggesting recipes you can cook **strictly** from
what's in your pantry — no shopping trip required.

## Features

- Add, edit and delete pantry ingredients (name, quantity, unit, optional expiry date)
- A pantry list screen backed by a RecyclerView and a live SQLite database
- 18 pre-loaded recipes, each with an ingredient list and preparation steps
- **Strict-matching suggestions**: a recipe is only suggested if every single
  ingredient it needs is present in the pantry in at least the required
  quantity. Matching handles unit conversion (e.g. 500 g vs 1 kg) and simple
  plural/singular differences (e.g. "tomato" vs "tomatoes").
- A recipe detail screen with the full ingredient list and method
- A settings screen for expiring-soon alerts and a metric units preference
- Bottom navigation between Pantry, Recipes and Settings

## Database

This app uses **SQLite** via `SQLiteOpenHelper`, chosen because it requires no
external account or network connection, keeps all data on-device, and matches
the persistent-storage approach covered in the module. Three tables are used:
`pantry_items`, `recipes`, and `recipe_ingredients` (one recipe has many
ingredients, linked by a foreign key).

## Project structure

```
com.example.smartpantry
├── data/       DatabaseHelper, DbContract, RecipeSeedData (SQLite layer)
├── logic/      RecipeMatcher, UnitConverter, IngredientNormalizer (the
│               strict-matching algorithm; no Android dependencies, unit tested)
├── model/      PantryItem, Recipe, RecipeIngredient (plain data classes)
└── ui/         All Activities and RecyclerView adapters
```

## Setup / run instructions

1. Clone this repository.
2. Open the project folder in Android Studio (tested on a recent stable release).
3. Let Gradle sync (first sync downloads dependencies and may take a few minutes).
4. Run on an emulator or a physical Android device (minimum SDK 24) via the
   ▶ Run button.
5. To run the unit tests for the matching logic: right-click
   `app/src/test/java/com/example/smartpantry/logic/RecipeMatcherTest.java` →
   **Run 'RecipeMatcherTest'**.

## Out of scope

Per the assignment brief, this app does not use Google Maps, any mapping SDK,
or device location/GPS services.