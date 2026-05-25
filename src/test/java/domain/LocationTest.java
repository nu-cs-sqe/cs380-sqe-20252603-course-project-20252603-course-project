package domain;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  // --- isOnBoard() boundary tests (TC13–TC24) ---

  @Test
  void isOnBoard_returnsTrue_whenXIsMin() {              // TC13
    assertTrue(new Location(0, 3).isOnBoard());
  }

  @Test
  void isOnBoard_returnsTrue_whenXIsMax() {              // TC14
    assertTrue(new Location(7, 3).isOnBoard());
  }

  @Test
  void isOnBoard_returnsTrue_whenXIsMinPlusOne() {       // TC15
    assertTrue(new Location(1, 3).isOnBoard());
  }

  @Test
  void isOnBoard_returnsTrue_whenXIsMaxMinusOne() {      // TC16
    assertTrue(new Location(6, 3).isOnBoard());
  }

  @Test
  void isOnBoard_returnsFalse_whenXIsBelowMin() {        // TC17
    assertFalse(new Location(-1, 3).isOnBoard());
  }

  @Test
  void isOnBoard_returnsFalse_whenXIsAboveMax() {        // TC18
    assertFalse(new Location(8, 3).isOnBoard());
  }

  @Test
  void isOnBoard_returnsTrue_whenYIsMin() {              // TC19
    assertTrue(new Location(3, 0).isOnBoard());
  }

  @Test
  void isOnBoard_returnsTrue_whenYIsMax() {              // TC20
    assertTrue(new Location(3, 7).isOnBoard());
  }

  @Test
  void isOnBoard_returnsTrue_whenYIsMinPlusOne() {       // TC21
    assertTrue(new Location(3, 1).isOnBoard());
  }

  @Test
  void isOnBoard_returnsTrue_whenYIsMaxMinusOne() {      // TC22
    assertTrue(new Location(3, 6).isOnBoard());
  }

  @Test
  void isOnBoard_returnsFalse_whenYIsBelowMin() {        // TC23
    assertFalse(new Location(3, -1).isOnBoard());
  }

  @Test
  void isOnBoard_returnsFalse_whenYIsAboveMax() {        // TC24
    assertFalse(new Location(3, 8).isOnBoard());
  }

  // --- getIntermediateSquares() tests (TC25–TC32) ---

  @Test
  void getIntermediateSquares_returnsEmpty_whenSameLocation() { // TC25
    Location loc = new Location(4, 4);
    assertTrue(loc.getIntermediateSquares(new Location(4, 4)).isEmpty());
  }

  @Test
  void getIntermediateSquares_returnsEmpty_whenAdjacentHorizontal() { // TC26
    Location loc = new Location(3, 4);
    assertTrue(loc.getIntermediateSquares(new Location(4, 4)).isEmpty());
  }

  @Test
  void getIntermediateSquares_returnsEmpty_whenAdjacentVertical() { // TC27
    Location loc = new Location(4, 3);
    assertTrue(loc.getIntermediateSquares(new Location(4, 4)).isEmpty());
  }

  @Test
  void getIntermediateSquares_returnsEmpty_whenAdjacentDiagonal() { // TC28
    Location loc = new Location(3, 3);
    assertTrue(loc.getIntermediateSquares(new Location(4, 4)).isEmpty());
  }

  @Test
  void getIntermediateSquares_returnsIntermediateSquares_whenHorizontal() { // TC29
    Location loc = new Location(0, 4);
    List<Location> squares = loc.getIntermediateSquares(new Location(3, 4));
    assertEquals(2, squares.size());
    assertEquals(1, squares.get(0).getX());
    assertEquals(4, squares.get(0).getY());
    assertEquals(2, squares.get(1).getX());
    assertEquals(4, squares.get(1).getY());
  }

  @Test
  void getIntermediateSquares_returnsIntermediateSquares_whenVertical() { // TC30
    Location loc = new Location(4, 0);
    List<Location> squares = loc.getIntermediateSquares(new Location(4, 3));
    assertEquals(2, squares.size());
    assertEquals(4, squares.get(0).getX());
    assertEquals(1, squares.get(0).getY());
    assertEquals(4, squares.get(1).getX());
    assertEquals(2, squares.get(1).getY());
  }

  @Test
  void getIntermediateSquares_returnsIntermediateSquares_whenDiagonal() { // TC31
    Location loc = new Location(0, 0);
    List<Location> squares = loc.getIntermediateSquares(new Location(3, 3));
    assertEquals(2, squares.size());
    assertEquals(1, squares.get(0).getX());
    assertEquals(1, squares.get(0).getY());
    assertEquals(2, squares.get(1).getX());
    assertEquals(2, squares.get(1).getY());
  }

  @Test
  void getIntermediateSquares_throwsIllegalArgumentException_whenToIsNull() { // TC32
    Location loc = new Location(4, 4);
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> loc.getIntermediateSquares(null)
    );
    assertEquals("to must not be null", exception.getMessage());
  }
}
