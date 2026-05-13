package domain;

import org.junit.jupiter.api.Test;
import ui.GameStatsView;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameStatsViewTests {
    @Test
    void panelIsCreated() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        assertNotNull(view);
    }

    @Test
    void layoutIsBoxLayoutYAxis() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        assertTrue(view.getLayout() instanceof BoxLayout);
    }

    @Test
    void hasFourComponents() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        assertEquals(4, view.getComponentCount());
    }
}
