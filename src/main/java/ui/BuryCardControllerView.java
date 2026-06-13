package ui;

import domain.Card;
import domain.CardType;
import domain.Deck;
import domain.Player;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BuryCardControllerView {
    private final Scanner scanner;

    public BuryCardControllerView() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void displayDrawnCard(Card card) {
        System.out.println("Drawing Card...");
        CardType cardType = card.getType();
        System.out.printf("Card drawn: %s%n", cardType);
        }

    public void displayNoCardsInDeck() {
        System.out.println("The deck has no cards left, so cannot draw");
    }

    public void displayInvalidIndex(String userChoice) {
        System.out.printf("%s is not a valid card position. Please try again.%n", userChoice);
    }

    public void displayValidInsert(Card card, int cardIndex) {
        System.out.printf("%s is now at index %d in the draw pile.%n", card.getType(), cardIndex);
    }

    public String getIndexChoice(Deck deck) {
        int deckSize = deck.count();
        System.out.printf("The deck currently has %d cards.%n", deckSize);
        System.out.printf("Enter where to place the card at (between 0 and %d)%n", deckSize);
        System.out.printf("(e.g. 0 = top of deck, 1 = after the first card, %d = at the bottom of deck)", deckSize);
        return scanner.nextLine();
    }
}


