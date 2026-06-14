package ui;

import domain.Player;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class TargetAttackControllerView {
    private final Scanner scanner;

    public TargetAttackControllerView() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void displayAlivePlayers(ArrayList<Player> alivePlayers, Player initiator) {
        System.out.println("Choose a player to target:");
        for (int i = 0; i < alivePlayers.size(); i++) {
            Player player = alivePlayers.get(i);
            System.out.printf("%d | %s%n", i, player.getPlayerName());
        }
    }

    public void displayInvalidIndex(String userChoice) {
        System.out.printf("%s is not a valid player index. Please try again.%n", userChoice);
    }

    public void displayInvalidTarget(String message) {
        System.out.printf("%s Please try again.%n", message);
    }

    public String getTargetPlayerIndex() {
        System.out.print("Enter the number before the player to target them:");
        return scanner.nextLine();
    }
}