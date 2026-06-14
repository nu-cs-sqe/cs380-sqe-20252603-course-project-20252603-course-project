package ui;

import domain.Card;

import java.util.List;

public class SeeTheFutureCardControllerView {
    public void displayTopCards(List<Card> cards) {
        System.out.println("The top cards of the deck are:");
        for (int i = 0; i < cards.size(); i++) {
            System.out.printf("%d | %s%n", i + 1, cards.get(i).getType());
        }
    }
}
