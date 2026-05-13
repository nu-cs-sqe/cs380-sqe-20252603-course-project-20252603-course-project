package ui;

import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Test;

public class MainTests {
    @Test
    void mainRunsWithoutCrash() {
        Main.main(new String[]{});
    }
}
