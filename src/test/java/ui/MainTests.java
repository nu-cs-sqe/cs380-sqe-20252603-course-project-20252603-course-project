package ui;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainTests {
    @Test
    void mainRunsWithoutCrash() {
        Main.main(new String[]{});
    }

    @Test
    void mainHandlesUnusedArgs() {
        Main.main(new String[]{"test"});
    }

    @Test
    void mainHandlesNullArgs() {
        Main.main(null);
    }

    @Test
    void mainCreatesView() {
        Main.main(new String[]{});
    }

    @Test
    void welcomeViewIsVisible() {
        SwingUtilities.invokeLater(() -> {
            Main.main(new String[]{});

            assertDoesNotThrow(() -> new WelcomeView().setVisible(true));
        });
    }
}
