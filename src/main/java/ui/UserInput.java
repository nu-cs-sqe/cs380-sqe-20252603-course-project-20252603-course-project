package ui;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UserInput {
    private final Scanner scanner;

    public UserInput() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public int getInsertPosition(int deckSize) {
        System.out.println("Where do you want to insert the Exploding Kitten?");
        System.out.println("(0 = top, " + deckSize + " = bottom):");
        return scanner.nextInt();
    }
}