package com.example.smartpantry.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Plain JVM tests for the singular/plural and case handling used by the strict matcher. */
public class IngredientNormalizerTest {

    @Test
    public void caseAndWhitespaceAreIgnored() {
        assertEquals("tomato", IngredientNormalizer.normalize("TOMATO"));
        assertEquals("tomato", IngredientNormalizer.normalize("  tomato  "));
    }

    @Test
    public void pluralsBecomeSingular() {
        assertEquals("tomato", IngredientNormalizer.normalize("Tomatoes"));
        assertEquals("potato", IngredientNormalizer.normalize("Potatoes"));
        assertEquals("egg", IngredientNormalizer.normalize("Eggs"));
        assertEquals("onion", IngredientNormalizer.normalize("Onions"));
        assertEquals("berry", IngredientNormalizer.normalize("berries"));
        assertEquals("peach", IngredientNormalizer.normalize("Peaches"));
    }

    @Test
    public void onlyTheLastWordIsSingularised() {
        assertEquals("cherry tomato", IngredientNormalizer.normalize("Cherry Tomatoes"));
    }

    @Test
    public void irregularAndProtectedWordsAreHandled() {
        assertEquals("loaf", IngredientNormalizer.normalize("loaves"));
        assertEquals("hummus", IngredientNormalizer.normalize("hummus"));
        assertEquals("couscous", IngredientNormalizer.normalize("couscous"));
    }

    @Test
    public void punctuationIsStripped() {
        assertEquals("rice", IngredientNormalizer.normalize("Rice!"));
    }

    @Test
    public void nullAndBlankInputGiveEmptyString() {
        assertEquals("", IngredientNormalizer.normalize(null));
        assertEquals("", IngredientNormalizer.normalize("   "));
    }
}