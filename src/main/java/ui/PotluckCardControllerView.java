package ui;

import domain.Card;
import domain.Player;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class PotluckCardControllerView {
    private final Scanner scanner;

    public PotluckCardControllerView () {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void displayPlayerAndCardsInHand(Player player) {
        System.out.printf("Player: %s%n", player.getPlayerName());
        System.out.println("You have these cards in your hand:");
        ArrayList<Card> hand = player.getHand();
        for (int i = 0; i < hand.size();  i++) {
            Card card = hand.get(i);
            System.out.printf("%d | %s%n", i, card.getType());
        }
    }

    public void displayNoCardsAvailable(Player player) {
        System.out.printf("Player: %s%n", player.getPlayerName());
        System.out.print("You have no cards in your hand. ");
        System.out.println("So you will be skipped from putting a card in the draw pile.");
    }

    public void displayInvalidIndex(String userChoice) {
        System.out.printf("%s is not a valid card position. Please try again.%n", userChoice);
    }

    public void displayValidCard(Card card) {
        System.out.printf("%s is now at the top of the draw pile.%n", card.getType());
    }

    public String getCardChoice() {
        System.out.print("Enter the number before the card to play the card;");
        return scanner.nextLine();
    }

}
