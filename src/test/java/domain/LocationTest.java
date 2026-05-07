package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    // --- getX() boundary tests (TC1–TC6) ---

    @Test
    void getX_returnsMin_whenXIsZero() {                   // TC1
        Location loc = new Location(0, 3);
        assertEquals(0, loc.getX());
    }

    @Test
    void getX_returnsMax_whenXIsSeven() {                  // TC2
        Location loc = new Location(7, 3);
        assertEquals(7, loc.getX());
    }

    @Test
    void getX_returnsMinPlusOne_whenXIsOne() {             // TC3
        Location loc = new Location(1, 3);
        assertEquals(1, loc.getX());
    }

    @Test
    void getX_returnsMaxMinusOne_whenXIsSix() {            // TC4
        Location loc = new Location(6, 3);
        assertEquals(6, loc.getX());
    }

    @Test
    void getX_returnsBelowMin_whenXIsNegativeOne() {       // TC5
        Location loc = new Location(-1, 3);
        assertEquals(-1, loc.getX());
    }

    @Test
    void getX_returnsAboveMax_whenXIsEight() {             // TC6
        Location loc = new Location(8, 3);
        assertEquals(8, loc.getX());
    }

    // --- getY() boundary tests (TC7–TC12) ---

    @Test
    void getY_returnsMin_whenYIsZero() {                   // TC7
        Location loc = new Location(3, 0);
        assertEquals(0, loc.getY());
    }

    @Test
    void getY_returnsMax_whenYIsSeven() {                  // TC8
        Location loc = new Location(3, 7);
        assertEquals(7, loc.getY());
    }

    @Test
    void getY_returnsMinPlusOne_whenYIsOne() {             // TC9
        Location loc = new Location(3, 1);
        assertEquals(1, loc.getY());
    }

    @Test
    void getY_returnsMaxMinusOne_whenYIsSix() {            // TC10
        Location loc = new Location(3, 6);
        assertEquals(6, loc.getY());
    }

    @Test
    void getY_returnsBelowMin_whenYIsNegativeOne() {       // TC11
        Location loc = new Location(3, -1);
        assertEquals(-1, loc.getY());
    }

    @Test
    void getY_returnsAboveMax_whenYIsEight() {             // TC12
        Location loc = new Location(3, 8);
        assertEquals(8, loc.getY());
    }
}
