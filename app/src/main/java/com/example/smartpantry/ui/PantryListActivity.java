package com.example.smartpantry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantry.R;
import com.example.smartpantry.data.DatabaseHelper;
import com.example.smartpantry.model.PantryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/**
 * Shows every pantry item and lets the user add, edit or delete one.
 */
public class PantryListActivity extends AppCompatActivity implements PantryAdapter.OnItemActionListener {

    private DatabaseHelper databaseHelper;
    private PantryAdapter adapter;
    private TextView emptyStateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);

        databaseHelper = new DatabaseHelper(this);
        emptyStateText = findViewById(R.id.textEmptyState);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPantry);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PantryAdapter(this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fabAddItem).setOnClickListener(v ->
                startActivity(new Intent(this, AddEditItemActivity.class)));

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        NavigationHelper.setup(this, bottomNav, R.id.nav_pantry);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload every time this screen becomes visible, so changes made on
        // the Add/Edit screen (or a delete just performed) are always reflected.
        refreshList();
    }

    private void refreshList() {
        List<PantryItem> items = databaseHelper.getAllPantryItems();
        adapter.setItems(items);
        emptyStateText.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onEdit(PantryItem item) {
        Intent intent = new Intent(this, AddEditItemActivity.class);
        intent.putExtra(AddEditItemActivity.EXTRA_ITEM_ID, item.getId());
        startActivity(intent);
    }

    @Override
    public void onDelete(PantryItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete ingredient")
                .setMessage("Remove \"" + item.getName() + "\" from your pantry?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    databaseHelper.deletePantryItem(item.getId());
                    refreshList();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}