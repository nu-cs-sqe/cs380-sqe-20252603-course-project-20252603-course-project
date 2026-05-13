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

    @Test
    void player1LabelCorrect() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        JLabel label = (JLabel) view.getComponent(1);

        assertTrue(label.getText().contains("Alice"));
        assertTrue(label.getText().contains("White"));
    }

    @Test
    void player2LabelCorrect() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        JLabel label = (JLabel) view.getComponent(2);

        assertTrue(label.getText().contains("Bob"));
        assertTrue(label.getText().contains("Black"));
    }

    @Test
    void currentPlayerStartsAsPlayer1() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        JLabel label = (JLabel) view.getComponent(3);

        assertTrue(label.getText().contains("Alice"));
    }
}
