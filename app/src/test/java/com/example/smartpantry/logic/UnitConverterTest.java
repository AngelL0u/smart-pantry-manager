package com.example.smartpantry.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/** Plain JVM tests for unit conversion into base units (g, ml, count). */
public class UnitConverterTest {

    private static final double DELTA = 1e-9;

    @Test
    public void massIsConvertedToGrams() {
        UnitConverter.Amount kg = UnitConverter.toBase(2, "kg");
        assertNotNull(kg);
        assertEquals(UnitConverter.Dimension.MASS, kg.dimension);
        assertEquals(2000.0, kg.baseValue, DELTA);
        assertEquals(2.0, UnitConverter.toBase(2, "g").baseValue, DELTA);
    }

    @Test
    public void volumeIsConvertedToMillilitres() {
        assertEquals(2000.0, UnitConverter.toBase(2, "l").baseValue, DELTA);
        assertEquals(10.0, UnitConverter.toBase(2, "tsp").baseValue, DELTA);
        assertEquals(30.0, UnitConverter.toBase(2, "tbsp").baseValue, DELTA);
        assertEquals(500.0, UnitConverter.toBase(2, "cup").baseValue, DELTA);
        assertEquals(UnitConverter.Dimension.VOLUME, UnitConverter.toBase(2, "ml").dimension);
    }

    @Test
    public void countUnitsAreOneToOne() {
        assertEquals(2.0, UnitConverter.toBase(2, "pcs").baseValue, DELTA);
        assertEquals(2.0, UnitConverter.toBase(2, "cloves").baseValue, DELTA);
        assertEquals(UnitConverter.Dimension.COUNT, UnitConverter.toBase(2, "slices").dimension);
    }

    @Test
    public void unitLookupIgnoresCaseAndSpaces() {
        assertEquals(2000.0, UnitConverter.toBase(2, " KG ").baseValue, DELTA);
    }

    @Test
    public void unknownUnitReturnsNull() {
        assertNull(UnitConverter.toBase(2, "xyz"));
    }
}