package domain;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ExplodingWildkittenSteps {

    private Game game;
    private GameController gameController;
    private Player currentPlayer;

    @Given("a game with {int} players")
    public void aGameWithPlayers(int playerCount) {
        game = Game.createGame(playerCount);
        gameController = new GameController(game);
        currentPlayer = game.getAlivePlayers().get(0);
    }

    @And("the current player has no Defuse card")
    public void theCurrentPlayerHasNoDefuseCard() {
        // Fresh game players start with empty hands by default
    }

    @When("the current player draws an Exploding Wildkitten card")
    public void theCurrentPlayerDrawsAnExplodingWildkittenCard() {
        // Provide insert-position input before constructing the controller,
        // so the Scanner inside ExplodingKittenCardControllerView binds to it.
        System.setIn(new ByteArrayInputStream("0\n".getBytes(StandardCharsets.UTF_8)));
        ExplodingKittenCardController controller = new ExplodingKittenCardController();
        currentPlayer.addCard(Card.createCard(CardType.EXPLODING_KITTEN));
        controller.executeCardAction(gameController, currentPlayer, Optional.empty());
    }

    @Then("the current player is eliminated from the game")
    public void theCurrentPlayerIsEliminatedFromTheGame() {
        assertFalse(currentPlayer.isAlive());
        assertFalse(game.getAlivePlayers().contains(currentPlayer));
    }
}
