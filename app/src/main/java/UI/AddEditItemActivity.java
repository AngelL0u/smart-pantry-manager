package com.example.smartpantry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantry.R;
import com.example.smartpantry.data.DatabaseHelper;
import com.example.smartpantry.model.PantryItem;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

/**
 * One screen used for BOTH adding a new pantry item and editing an existing one.
 * If EXTRA_ITEM_ID is passed in the launching Intent, the screen loads that
 * item and updates it on Save; otherwise it inserts a new row.
 */
public class AddEditItemActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_ID = "extra_item_id";
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    private DatabaseHelper databaseHelper;
    private long editingItemId = -1; // -1 means "adding a new item"

    private TextInputLayout layoutName, layoutQuantity, layoutUnit, layoutExpiry;
    private TextInputEditText editName, editQuantity, editUnit, editExpiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        databaseHelper = new DatabaseHelper(this);

        layoutName = findViewById(R.id.layoutName);
        layoutQuantity = findViewById(R.id.layoutQuantity);
        layoutUnit = findViewById(R.id.layoutUnit);
        layoutExpiry = findViewById(R.id.layoutExpiry);
        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiry = findViewById(R.id.editExpiry);

        editingItemId = getIntent().getLongExtra(EXTRA_ITEM_ID, -1);
        if (editingItemId != -1) {
            loadExistingItem(editingItemId);
        }

        findViewById(R.id.buttonSave).setOnClickListener(v -> onSave());
    }

    private void loadExistingItem(long id) {
        // getAllPantryItems is simple enough for this app's scale; a getById
        // could be added to DatabaseHelper if the pantry ever grows large.
        for (PantryItem item : databaseHelper.getAllPantryItems()) {
            if (item.getId() == id) {
                setTitle("Edit Ingredient");
                editName.setText(item.getName());
                editQuantity.setText(String.valueOf(item.getQuantity()));
                editUnit.setText(item.getUnit());
                if (item.getExpiryDate() != null) {
                    editExpiry.setText(item.getExpiryDate());
                }
                return;
            }
        }
    }

    private void onSave() {
        layoutName.setError(null);
        layoutQuantity.setError(null);
        layoutUnit.setError(null);
        layoutExpiry.setError(null);

        String name = textOf(editName);
        String quantityText = textOf(editQuantity);
        String unit = textOf(editUnit);
        String expiry = textOf(editExpiry);

        boolean valid = true;

        if (name.isEmpty()) {
            layoutName.setError("Enter an ingredient name");
            valid = false;
        }

        double quantity = 0;
        if (quantityText.isEmpty()) {
            layoutQuantity.setError("Enter a quantity");
            valid = false;
        } else {
            try {
                quantity = Double.parseDouble(quantityText);
                if (quantity <= 0) {
                    layoutQuantity.setError("Must be greater than 0");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                layoutQuantity.setError("Enter a valid number");
                valid = false;
            }
        }

        if (unit.isEmpty()) {
            layoutUnit.setError("Enter a unit, e.g. g, kg, pcs");
            valid = false;
        }

        if (!expiry.isEmpty() && !DATE_PATTERN.matcher(expiry).matches()) {
            layoutExpiry.setError("Use yyyy-MM-dd, e.g. 2026-12-31");
            valid = false;
        }

        if (!valid) {
            return;
        }

        PantryItem item = new PantryItem(
                editingItemId == -1 ? 0 : editingItemId,
                name, quantity, unit, expiry.isEmpty() ? null : expiry);

        if (editingItemId == -1) {
            databaseHelper.addPantryItem(item);
            Toast.makeText(this, "Added " + name, Toast.LENGTH_SHORT).show();
        } else {
            databaseHelper.updatePantryItem(item);
            Toast.makeText(this, "Updated " + name, Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK, new Intent());
        finish();
    }

    private String textOf(TextInputEditText field) {
        return field.getText() == null ? "" : field.getText().toString().trim();
    }
}