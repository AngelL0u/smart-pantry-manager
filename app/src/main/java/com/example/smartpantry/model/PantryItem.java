package com.example.smartpantry.model;

/**
 * One ingredient the user currently has at home.
 * Maps to a row in the pantry_items table.
 */
public class PantryItem {
    private long id;            // primary key (0 = not saved yet)
    private String name;        // e.g. "Tomatoes"
    private double quantity;    // e.g. 500
    private String unit;        // e.g. "g", "kg", "ml", "pcs"
    private String expiryDate;  // optional, "yyyy-MM-dd" or null

    public PantryItem(long id, String name, double quantity, String unit, String expiryDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
}