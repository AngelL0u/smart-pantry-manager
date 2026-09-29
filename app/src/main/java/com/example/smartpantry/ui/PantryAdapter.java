package com.example.smartpantry.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantry.R;
import com.example.smartpantry.model.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {

    public interface OnItemActionListener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    private final List<PantryItem> items = new ArrayList<>();
    private final OnItemActionListener listener;

    public PantryAdapter(OnItemActionListener listener) {
        this.listener = listener;
    }

    public void setItems(List<PantryItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public int getItemCountForEmptyState() {
        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PantryItem item = items.get(position);

        holder.name.setText(item.getName());
        holder.quantity.setText(formatQuantity(item.getQuantity()) + " " + item.getUnit());

        if (item.getExpiryDate() != null && !item.getExpiryDate().isEmpty()) {
            holder.expiry.setVisibility(View.VISIBLE);
            holder.expiry.setText("Expires " + item.getExpiryDate());
        } else {
            holder.expiry.setVisibility(View.GONE);
        }

        holder.editButton.setOnClickListener(v -> listener.onEdit(item));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String formatQuantity(double quantity) {
        if (quantity == Math.floor(quantity) && !Double.isInfinite(quantity)) {
            return String.valueOf((long) quantity);
        }
        return String.valueOf(quantity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView quantity;
        final TextView expiry;
        final Button editButton;
        final Button deleteButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textItemName);
            quantity = itemView.findViewById(R.id.textItemQuantity);
            expiry = itemView.findViewById(R.id.textItemExpiry);
            editButton = itemView.findViewById(R.id.buttonEdit);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }
}