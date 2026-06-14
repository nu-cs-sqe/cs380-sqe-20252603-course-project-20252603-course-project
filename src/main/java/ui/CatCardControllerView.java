package ui;

import domain.CardType;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

public class CatCardControllerView {
    private final Scanner scanner;

    public CatCardControllerView() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public Optional<CardType> getRequestedCardType() {
        System.out.println("Enter the card type you want to steal:");
        System.out.println("(e.g. ATTACK, SKIP, NOPE, SHUFFLE, SEE_THE_FUTURE,");
        System.out.println("DRAW_FROM_BOTTOM, CAT_CARD_1, CAT_CARD_2, CAT_CARD_3, CAT_CARD_4):");
        String input = scanner.nextLine().trim().toUpperCase();
        try {
            return Optional.of(CardType.valueOf(input));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}