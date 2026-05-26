package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

  // --- getX() boundary tests (TC1–TC6) ---

  @Test
  void GetX_XIsZero_ReturnsZero() {                        // TC1
    Location loc = new Location(0, 3);
    assertEquals(0, loc.getX());
  }

  @Test
  void GetX_XIsSeven_ReturnsSeven() {                      // TC2
    Location loc = new Location(7, 3);
    assertEquals(7, loc.getX());
  }

  @Test
  void GetX_XIsOne_ReturnsOne() {                          // TC3
    Location loc = new Location(1, 3);
    assertEquals(1, loc.getX());
  }

  @Test
  void GetX_XIsSix_ReturnsSix() {                          // TC4
    Location loc = new Location(6, 3);
    assertEquals(6, loc.getX());
  }

  @Test
  void GetX_XIsNegativeOne_ReturnsNegativeOne() {          // TC5
    Location loc = new Location(-1, 3);
    assertEquals(-1, loc.getX());
  }

  @Test
  void GetX_XIsEight_ReturnsEight() {                      // TC6
    Location loc = new Location(8, 3);
    assertEquals(8, loc.getX());
  }

  // --- getY() boundary tests (TC7–TC12) ---

  @Test
  void GetY_YIsZero_ReturnsZero() {                        // TC7
    Location loc = new Location(3, 0);
    assertEquals(0, loc.getY());
  }

  @Test
  void GetY_YIsSeven_ReturnsSeven() {                      // TC8
    Location loc = new Location(3, 7);
    assertEquals(7, loc.getY());
  }

  @Test
  void GetY_YIsOne_ReturnsOne() {                          // TC9
    Location loc = new Location(3, 1);
    assertEquals(1, loc.getY());
  }

  @Test
  void GetY_YIsSix_ReturnsSix() {                          // TC10
    Location loc = new Location(3, 6);
    assertEquals(6, loc.getY());
  }

  @Test
  void GetY_YIsNegativeOne_ReturnsNegativeOne() {          // TC11
    Location loc = new Location(3, -1);
    assertEquals(-1, loc.getY());
  }

  @Test
  void GetY_YIsEight_ReturnsEight() {                      // TC12
    Location loc = new Location(3, 8);
    assertEquals(8, loc.getY());
  }
}
