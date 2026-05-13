package ui;

import org.junit.jupiter.api.Test;

public class MainTests {
    @Test
    void mainRunsWithoutCrash() {
        Main.main(new String[]{});
    }

    @Test
    void mainHandlesNullArgs() {
        Main.main(null);
    }
}
