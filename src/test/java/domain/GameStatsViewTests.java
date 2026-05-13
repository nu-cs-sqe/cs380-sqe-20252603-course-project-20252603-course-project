package domain;

import org.junit.jupiter.api.Test;
import ui.GameStatsView;

import static org.junit.jupiter.api.Assertions.*;

public class GameStatsViewTests {
    @Test
    void panelIsCreated() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        assertNotNull(view);
    }
}
