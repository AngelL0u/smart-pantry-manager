package com.example.smartpantry.ui;

import android.app.Activity;
import android.content.Intent;

import com.example.smartpantry.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Wires the same bottom navigation bar on PantryListActivity, SuggestedRecipesActivity
 * and SettingsActivity. Selecting a tab that isn't the current screen navigates there
 * and finishes the current Activity, so switching tabs never builds up a back stack.
 */
final class NavigationHelper {

    private NavigationHelper() { }

    static void setup(Activity activity, BottomNavigationView bottomNav, int currentItemId) {
        bottomNav.setSelectedItemId(currentItemId);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == currentItemId) {
                return true; // already on this screen
            }
            Class<? extends Activity> target;
            if (id == R.id.nav_pantry) {
                target = PantryListActivity.class;
            } else if (id == R.id.nav_recipes) {
                target = SuggestedRecipesActivity.class;
            } else {
                target = SettingsActivity.class;
            }
            activity.startActivity(new Intent(activity, target));
            activity.finish();
            return true;
        });
    }
}