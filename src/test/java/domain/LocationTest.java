package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LocationTest {

  // --- getX() boundary tests (TC1–TC6) ---

  @Test
  void getXReturnsMinWhenXIsZero() {                   // TC1
    Location loc = new Location(0, 3);
    assertEquals(0, loc.getX());
  }

  @Test
  void getXReturnsMaxWhenXIsSeven() {                  // TC2
    Location loc = new Location(7, 3);
    assertEquals(7, loc.getX());
  }

  @Test
  void getXReturnsMinPlusOneWhenXIsOne() {             // TC3
    Location loc = new Location(1, 3);
    assertEquals(1, loc.getX());
  }

  @Test
  void getXReturnsMaxMinusOneWhenXIsSix() {            // TC4
    Location loc = new Location(6, 3);
    assertEquals(6, loc.getX());
  }

  @Test
  void getXReturnsBelowMinWhenXIsNegativeOne() {       // TC5
    Location loc = new Location(-1, 3);
    assertEquals(-1, loc.getX());
  }

  @Test
  void getXReturnsAboveMaxWhenXIsEight() {             // TC6
    Location loc = new Location(8, 3);
    assertEquals(8, loc.getX());
  }

  // --- getY() boundary tests (TC7–TC12) ---

  @Test
  void getYReturnsMinWhenYIsZero() {                   // TC7
    Location loc = new Location(3, 0);
    assertEquals(0, loc.getY());
  }

  @Test
  void getYReturnsMaxWhenYIsSeven() {                  // TC8
    Location loc = new Location(3, 7);
    assertEquals(7, loc.getY());
  }

  @Test
  void getYReturnsMinPlusOneWhenYIsOne() {             // TC9
    Location loc = new Location(3, 1);
    assertEquals(1, loc.getY());
  }

  @Test
  void getYReturnsMaxMinusOneWhenYIsSix() {            // TC10
    Location loc = new Location(3, 6);
    assertEquals(6, loc.getY());
  }

  @Test
  void getYReturnsBelowMinWhenYIsNegativeOne() {       // TC11
    Location loc = new Location(3, -1);
    assertEquals(-1, loc.getY());
  }

  @Test
  void getYReturnsAboveMaxWhenYIsEight() {             // TC12
    Location loc = new Location(3, 8);
    assertEquals(8, loc.getY());
  }
}
