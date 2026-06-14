package ui;

import domain.Game;
import domain.GameController;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.print("Enter number of players (2-5): ");
        int playerCount = Integer.parseInt(scanner.nextLine().trim());

        Game game = Game.createGame(playerCount);
        game.setup();

        GameController controller = new GameController(game);
        controller.setCurrentPlayerIndex(0);
        controller.setNextPlayerIndex(1);
        controller.setCurrentPlayerTurnsLeft(1);
        controller.setNextPlayerTurnsLeft(1);

        GameControllerView view = new GameControllerView();
        controller.runGame(view);

        System.out.println("Game over! Winner: " + game.getAlivePlayers().get(0).getPlayerName());
    }
}
