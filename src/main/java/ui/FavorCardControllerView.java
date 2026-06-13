package ui;

import domain.Card;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class FavorCardControllerView {
    private final Scanner scanner;

    public FavorCardControllerView() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public Card getCardToGive(List<Card> hand) {
        System.out.println("Choose a card to give:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + ": " + hand.get(i).getType());
        }
        int choice = scanner.nextInt();
        return hand.get(choice);
    }
}