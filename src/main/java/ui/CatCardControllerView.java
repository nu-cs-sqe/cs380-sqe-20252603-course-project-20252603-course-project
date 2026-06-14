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
        System.out.println("DRAW_FROM_BOTTOM, TACOCAT, CATERMELLON, BEARD_CAT, HAIRY_POTATO_CAT):");
        String input = scanner.nextLine().trim().toUpperCase();
        try {
            return Optional.of(CardType.valueOf(input));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}