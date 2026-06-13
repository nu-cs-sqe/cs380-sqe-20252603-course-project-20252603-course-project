package ui;

import domain.Card;
import domain.Player;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class GameControllerView {
    private final Scanner scanner;

    public GameControllerView() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void displayCurrentPlayerAndCardsInHand(Player player) {
        System.out.printf("Current Player: %s%n", player.getPlayerName());
        System.out.println("You have these cards in your hand:");
        ArrayList<Card> hand = player.getHand();
        for (int i = 0; i < hand.size();  i++) {
            Card card = hand.get(i);
            System.out.printf("%d | %s%n", i, card.getType());
        }
    }

    public void displayInvalidChoice(String userChoice) {
        System.out.printf("\"%s\" is not a valid choice. Please try again.%n", userChoice);
    }

    public void displayInvalidMove(ArrayList<Card> cardsPlayed) {
        ArrayList<String> cardsList = new ArrayList<String>();
        for (Card card : cardsPlayed) {
            cardsList.add(card.getType().toString());
        }
        String cardsString = String.join(", ",cardsList);
        System.out.printf("\"%s\" is not a valid move. Please try again.%n", cardsString);
    }

    public void displayInvalidIndex(String userChoice) {
        System.out.printf("%s is not a valid card position. Please try again.%n", userChoice);
    }

    public void displayDuplicateCardInMove(String userChoice) {
        System.out.printf("Cannot card #%s twice in one move. Please try again.%n", userChoice);
    }

    public String getCardChoiceOrDraw() {
        System.out.println("Enter the number before the card to play the card;");
        System.out.println("numbers separated by commas to play multiple cards (e.g. 0,2,5);");
        System.out.print("or d to draw a card and end turn: ");
        return scanner.nextLine();
    }

    public String getTargetPlayerIndex(ArrayList<Player> alivePlayers, Player currentPlayer) {
        System.out.println("You may target these players:");
        for (int i = 0; i < alivePlayers.size();  i++) {
            Player player = alivePlayers.get(i);
            if (!currentPlayer.equals(player)) {
                System.out.printf("%d | %s%n", i, player.getPlayerName());
            }
        }
        System.out.print("Enter the number before the player you want to target: ");
        return scanner.nextLine();
    }
}
