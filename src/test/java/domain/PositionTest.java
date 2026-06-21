package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    @Test
    void constructor_rowAndColAtLowerBound_createsSuccessfully() {
        Position position = new Position(1, 1);
        assertNotNull(position);
    }

    @Test
    void constructor_rowAndColAtUpperBound_createsSuccessfully() {
        Position position = new Position(8, 8);
        assertNotNull(position);
    }

    @Test
    void constructor_rowBelowLowerBound_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Position(0, 1));
    }

    @Test
    void constructor_rowAboveUpperBound_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Position(9, 1));
    }

    @Test
    void constructor_colBelowLowerBound_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Position(1, 0));
    }

    @Test
    void constructor_colAboveUpperBound_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Position(1, 9));
    }

    @Test
    void getRow_positionCreatedWithRowOne_returnsOne() {
        Position position = new Position(1, 1);
        assertEquals(1, position.getRow());
    }

    @Test
    void getRow_positionCreatedWithRowEight_returnsEight() {
        Position position = new Position(8, 1);
        assertEquals(8, position.getRow());
    }

    @Test
    void getCol_positionCreatedWithColOne_returnsOne() {
        Position position = new Position(1, 1);
        assertEquals(1, position.getCol());
    }

    @Test
    void getCol_positionCreatedWithColEight_returnsEight() {
        Position position = new Position(1, 8);
        assertEquals(8, position.getCol());
    }

    @Test
    void equals_sameObjectReference_returnsTrue() {
        Position position = new Position(1, 1);
        assertTrue(position.equals(position));
    }

    @Test
    void equals_differentObjectSameRowAndCol_returnsTrue() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(1, 1);
        assertTrue(position1.equals(position2));
    }

    @Test
    void equals_differentRowSameCol_returnsFalse() {
        Position position1 = new Position(8, 1);
        Position position2 = new Position(1, 1);
        assertFalse(position1.equals(position2));
    }

    @Test
    void equals_sameRowDifferentCol_returnsFalse() {
        Position position1 = new Position(1,8);
        Position position2 = new Position(1, 1);
        assertFalse(position1.equals(position2));
    }

    @Test
    void equals_null_returnsFalse() {
        Position position = new Position(1,8);
        assertFalse(position.equals(null));
    }
}
