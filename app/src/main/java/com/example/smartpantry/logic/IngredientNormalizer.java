package com.example.smartpantry.logic;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Turns messy user input into a canonical ingredient key so that
 * "Tomatoes", " tomato " and "TOMATO" all become "tomato".
 *
 * Note: the goal is CONSISTENCY, not perfect English. Both the pantry name and
 * the recipe name go through the same method, so they end up with the same key
 * even if the singular form is not a real word.
 */
public final class IngredientNormalizer {

    private static final Map<String, String> IRREGULAR = new HashMap<>();

    static {
        IRREGULAR.put("loaves", "loaf");
        IRREGULAR.put("leaves", "leaf");
        IRREGULAR.put("knives", "knife");
        IRREGULAR.put("halves", "half");
    }

    private IngredientNormalizer() { }

    public static String normalize(String raw) {
        if (raw == null) {
            return "";
        }
        // lower-case, drop punctuation/digits, collapse whitespace
        String cleaned = raw.trim().toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
        if (cleaned.isEmpty()) {
            return "";
        }
        // only the last word carries the plural: "cherry tomatoes" -> "cherry tomato"
        String[] words = cleaned.split(" ");
        words[words.length - 1] = singularize(words[words.length - 1]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(words[i]);
        }
        return sb.toString();
    }

    private static String singularize(String w) {
        if (IRREGULAR.containsKey(w)) return IRREGULAR.get(w);
        if (w.length() <= 3) return w;                          // "egg", "oil", "yam"
        if (w.endsWith("ies")) return w.substring(0, w.length() - 3) + "y";   // berries -> berry
        if (w.endsWith("oes")) return w.substring(0, w.length() - 2);         // tomatoes -> tomato
        if (w.endsWith("ches") || w.endsWith("shes") || w.endsWith("xes") || w.endsWith("sses")) {
            return w.substring(0, w.length() - 2);                            // peaches -> peach
        }
        if (w.endsWith("ss") || w.endsWith("us")) return w;     // watercress, couscous, hummus
        if (w.endsWith("s")) return w.substring(0, w.length() - 1);           // eggs -> egg
        return w;
    }
}