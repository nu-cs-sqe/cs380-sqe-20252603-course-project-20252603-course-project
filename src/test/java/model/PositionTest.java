package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    // --- isValid ---

    @Test void rowJustBelowMin_isInvalid() {
        assertFalse(new Position(-1, 4).isValid());  // BVA-P-01
    }

    @Test void rowAtMin_isValid() {
        assertTrue(new Position(0, 4).isValid());    // BVA-P-02
    }

    @Test void rowJustAboveMin_isValid() {
        assertTrue(new Position(1, 4).isValid());    // BVA-P-03
    }

    @Test void rowJustBelowMax_isValid() {
        assertTrue(new Position(6, 4).isValid());    // BVA-P-04
    }

    @Test void rowAtMax_isValid() {
        assertTrue(new Position(7, 4).isValid());    // BVA-P-05
    }

    @Test void rowJustAboveMax_isInvalid() {
        assertFalse(new Position(8, 4).isValid());   // BVA-P-06
    }

    @Test void colJustBelowMin_isInvalid() {
        assertFalse(new Position(4, -1).isValid());  // BVA-P-07
    }

    @Test void colAtMin_isValid() {
        assertTrue(new Position(4, 0).isValid());    // BVA-P-08
    }

    @Test void colAtMax_isValid() {
        assertTrue(new Position(4, 7).isValid());    // BVA-P-09
    }

    @Test void colJustAboveMax_isInvalid() {
        assertFalse(new Position(4, 8).isValid());   // BVA-P-10
    }

    @Test void bothAtMin_isValid() {
        assertTrue(new Position(0, 0).isValid());    // BVA-P-11
    }

    @Test void bothAtMax_isValid() {
        assertTrue(new Position(7, 7).isValid());    // BVA-P-12
    }

    @Test void bothBelowMin_isInvalid() {
        assertFalse(new Position(-1, -1).isValid()); // BVA-P-13
    }

    @Test void bothAboveMax_isInvalid() {
        assertFalse(new Position(8, 8).isValid());   // BVA-P-14
    }

    // --- equals ---

    @Test void sameRowAndCol_areEqual() {
        assertEquals(new Position(3, 4), new Position(3, 4));    // BVA-PE-01
    }

    @Test void sameRowDifferentCol_areNotEqual() {
        assertNotEquals(new Position(3, 4), new Position(3, 5)); // BVA-PE-02
    }

    @Test void differentRowSameCol_areNotEqual() {
        assertNotEquals(new Position(3, 4), new Position(4, 4)); // BVA-PE-03
    }

    @Test void compareWithNull_isNotEqual() {
        assertNotEquals(new Position(3, 4), null);               // BVA-PE-04
    }

    // --- getters ---

    @Test void getRow_returnsRow() {
        assertEquals(3, new Position(3, 4).getRow());
    }

    @Test void getCol_returnsCol() {
        assertEquals(4, new Position(3, 4).getCol());
    }
}