package com.example.smartpantry.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantry.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

/** Lets the user toggle expiring-soon alerts and a metric/imperial unit preference. */
public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "smart_pantry_prefs";
    private static final String KEY_EXPIRY_ALERTS = "expiry_alerts_enabled";
    private static final String KEY_METRIC_UNITS = "prefer_metric_units";

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        SwitchMaterial expirySwitch = findViewById(R.id.switchExpiryAlerts);
        SwitchMaterial unitsSwitch = findViewById(R.id.switchMetricUnits);

        expirySwitch.setChecked(prefs.getBoolean(KEY_EXPIRY_ALERTS, true));
        unitsSwitch.setChecked(prefs.getBoolean(KEY_METRIC_UNITS, true));

        expirySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_EXPIRY_ALERTS, isChecked).apply();
            Toast.makeText(this, "Preference saved", Toast.LENGTH_SHORT).show();
        });

        unitsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_METRIC_UNITS, isChecked).apply();
            Toast.makeText(this, "Preference saved", Toast.LENGTH_SHORT).show();
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        NavigationHelper.setup(this, bottomNav, R.id.nav_settings);
    }
}