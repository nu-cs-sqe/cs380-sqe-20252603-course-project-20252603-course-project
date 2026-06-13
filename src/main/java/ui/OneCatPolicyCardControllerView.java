package ui;

import domain.Card;
import domain.Player;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class OneCatPolicyCardControllerView {
    private final Scanner scanner;

    public OneCatPolicyCardControllerView() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void displayTurnsAdded(int turnsAdded) {
        System.out.printf("Pairs or triples of cat cards for next player (turns added):" +
                " %d%n", turnsAdded);

    }

    public void displayNoTurnsAdded() {
        System.out.println("No pairs or triples of cat cards detected in next player's hand. " +
                "No turns added.");

    }
}
