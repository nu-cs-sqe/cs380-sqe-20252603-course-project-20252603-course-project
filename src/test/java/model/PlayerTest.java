package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test void validWhitePlayer_createdSuccessfully() {                   // BVA-PL-01
        Player player = new Player("Alice", Color.WHITE);
        assertEquals("Alice", player.getName());
        assertEquals(Color.WHITE, player.getColor());
    }

    @Test void validBlackPlayer_createdSuccessfully() {                   // BVA-PL-02
        Player player = new Player("Bob", Color.BLACK);
        assertEquals("Bob", player.getName());
        assertEquals(Color.BLACK, player.getColor());
    }

    @Test void emptyName_throwsException() {                              // BVA-PL-03
        assertThrows(IllegalArgumentException.class,
                () -> new Player("", Color.WHITE));
    }

    @Test void nullName_throwsException() {                               // BVA-PL-04
        assertThrows(IllegalArgumentException.class,
                () -> new Player(null, Color.WHITE));
    }

    @Test void nullColor_throwsException() {                              // BVA-PL-05
        assertThrows(IllegalArgumentException.class,
                () -> new Player("Alice", null));
    }

    @Test void blankName_throwsException() {                              // BVA-PL-06
        assertThrows(IllegalArgumentException.class,
                () -> new Player("  ", Color.WHITE));
    }
}