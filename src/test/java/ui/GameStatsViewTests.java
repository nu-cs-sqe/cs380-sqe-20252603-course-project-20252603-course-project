package ui;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

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

    @Test
    void labelsHaveStyling() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        JLabel label = (JLabel) view.getComponent(0);

        assertEquals(new Font("Arial", Font.BOLD, 30), label.getFont());
    }

    @Test
    void backgroundColorCorrect() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        assertEquals(new Color(104, 76, 150), view.getBackground());
    }

    @Test
    void labelsAreInCorrectOrder() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        assertEquals("Player Information", ((JLabel) view.getComponent(0)).getText());
    }

    @Test
    void nullNamesDoNotCrash() {
        GameStatsView view = new GameStatsView(null, "Bob");

        assertNotNull(view);
    }

    @Test
    void currentPlayerLabelAccessible() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        assertNotNull(view.currentPlayerLabel);
    }
}
