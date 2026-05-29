package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LocationTest {

  // --- getX() boundary tests (TC1–TC6) ---

  @Test
  void getX_returnsMin_whenXisZero() {                   // TC1
    Location loc = new Location(0, 3);
    assertEquals(0, loc.getX());
  }

  @Test
  void getX_returnsMax_whenXisSeven() {                  // TC2
    Location loc = new Location(7, 3);
    assertEquals(7, loc.getX());
  }

  @Test
  void getX_returnsMinPlusOne_whenXisOne() {             // TC3
    Location loc = new Location(1, 3);
    assertEquals(1, loc.getX());
  }

  @Test
  void getX_returnsMaxMinusOne_whenXisSix() {            // TC4
    Location loc = new Location(6, 3);
    assertEquals(6, loc.getX());
  }

  @Test
  void getX_returnsBelowMin_whenXisNegativeOne() {       // TC5
    Location loc = new Location(-1, 3);
    assertEquals(-1, loc.getX());
  }

  @Test
  void getX_returnsAboveMax_whenXisEight() {             // TC6
    Location loc = new Location(8, 3);
    assertEquals(8, loc.getX());
  }

  // --- getY() boundary tests (TC7–TC12) ---

  @Test
  void getY_returnsMin_whenYisZero() {                   // TC7
    Location loc = new Location(3, 0);
    assertEquals(0, loc.getY());
  }

  @Test
  void getY_returnsMax_whenYisSeven() {                  // TC8
    Location loc = new Location(3, 7);
    assertEquals(7, loc.getY());
  }

  @Test
  void getY_returnsMinPlusOne_whenYisOne() {             // TC9
    Location loc = new Location(3, 1);
    assertEquals(1, loc.getY());
  }

  @Test
  void getY_returnsMaxMinusOne_whenYisSix() {            // TC10
    Location loc = new Location(3, 6);
    assertEquals(6, loc.getY());
  }

  @Test
  void getY_returnsBelowMin_whenYisNegativeOne() {       // TC11
    Location loc = new Location(3, -1);
    assertEquals(-1, loc.getY());
  }

  @Test
  void getY_returnsAboveMax_whenYisEight() {             // TC12
    Location loc = new Location(3, 8);
    assertEquals(8, loc.getY());
  }
}
