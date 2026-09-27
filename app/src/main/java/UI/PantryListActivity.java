package com.example.smartpantry.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantry.R;
import com.example.smartpantry.data.DatabaseHelper;
import com.example.smartpantry.model.PantryItem;

import java.util.List;

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

        findViewById(R.id.fabAddItem).setOnClickListener(v -> onAddItem());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        List<PantryItem> items = databaseHelper.getAllPantryItems();
        adapter.setItems(items);
        emptyStateText.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void onAddItem() {
        Toast.makeText(this, "Add screen coming in Phase 4", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEdit(PantryItem item) {
        Toast.makeText(this, "Edit screen coming in Phase 4: " + item.getName(),
                Toast.LENGTH_SHORT).show();
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