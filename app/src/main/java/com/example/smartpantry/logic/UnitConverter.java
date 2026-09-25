package com.example.smartpantry.logic;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Converts quantities into a base unit so "1 kg" and "500 g" can be compared.
 *
 * Every unit belongs to one Dimension:
 *   MASS   -> base unit grams
 *   VOLUME -> base unit millilitres
 *   COUNT  -> base unit "one item" (pieces, cloves, slices, ...)
 *
 * Amounts in different dimensions are NOT convertible (we cannot know how many
 * grams one onion weighs), so under the strict rule they count as "not enough".
 */
public final class UnitConverter {

    public enum Dimension { MASS, VOLUME, COUNT }

    private static final class UnitInfo {
        final Dimension dimension;
        final double factor;   // multiply by this to reach the base unit
        UnitInfo(Dimension dimension, double factor) {
            this.dimension = dimension;
            this.factor = factor;
        }
    }

    private static final Map<String, UnitInfo> UNITS = new HashMap<>();

    static {
        // Mass (base: g)
        put(Dimension.MASS, 1, "g", "gram", "grams");
        put(Dimension.MASS, 1000, "kg", "kilogram", "kilograms");
        // Volume (base: ml)
        put(Dimension.VOLUME, 1, "ml", "millilitre", "millilitres", "milliliter", "milliliters");
        put(Dimension.VOLUME, 1000, "l", "litre", "litres", "liter", "liters");
        put(Dimension.VOLUME, 5, "tsp", "teaspoon", "teaspoons");
        put(Dimension.VOLUME, 15, "tbsp", "tablespoon", "tablespoons");
        put(Dimension.VOLUME, 250, "cup", "cups");
        // Count (base: 1 item)
        put(Dimension.COUNT, 1, "", "pc", "pcs", "piece", "pieces", "whole", "item", "items",
                "clove", "cloves", "slice", "slices", "can", "cans", "tin", "tins",
                "packet", "packets", "bunch", "bunches");
    }

    private UnitConverter() { }

    private static void put(Dimension d, double factor, String... names) {
        for (String n : names) {
            UNITS.put(n, new UnitInfo(d, factor));
        }
    }

    /** Result of converting a quantity: the amount in base units plus its dimension. */
    public static final class Amount {
        public final double baseValue;
        public final Dimension dimension;
        Amount(double baseValue, Dimension dimension) {
            this.baseValue = baseValue;
            this.dimension = dimension;
        }
    }

    /**
     * @return the amount in base units, or null if the unit is not recognised.
     */
    public static Amount toBase(double quantity, String unit) {
        String key = unit == null ? "" : unit.trim().toLowerCase(Locale.ROOT);
        UnitInfo info = UNITS.get(key);
        if (info == null) {
            return null;
        }
        return new Amount(quantity * info.factor, info.dimension);
    }
}