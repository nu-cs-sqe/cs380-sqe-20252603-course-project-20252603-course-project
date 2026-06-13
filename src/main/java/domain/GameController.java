package domain;

import java.util.*;

import ui.GameControllerView;

public class GameController {
    private Game game;
    private int currentPlayerIndex;
    private int nextPlayerIndex;
    private int currentPlayerTurnsLeft;
    private int nextPlayerTurnsLeft;

    GameController(Game game) {
        this.game = game;
    }

    Game getGame() {
        return this.game;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        if (currentPlayerIndex < 0 || currentPlayerIndex >= this.game.getAlivePlayerCount()) {
            throw new IllegalArgumentException("Invalid player index: " + currentPlayerIndex);
        }

        this.currentPlayerIndex = currentPlayerIndex;
    }

    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }

    public void setNextPlayerIndex(int newNextPlayerIndex) {
        if (newNextPlayerIndex < 0 || newNextPlayerIndex >= this.game.getAlivePlayerCount()) {
            throw new IllegalArgumentException("invalid next index");
        }

        this.nextPlayerIndex = newNextPlayerIndex;
    }

    public int getNextPlayerIndex() {
        return this.nextPlayerIndex;
    }

    public void setPlayerOrder(List<Player> playerOrder) {
        if (playerOrder.size() != this.game.getAlivePlayerCount()){
            throw new IllegalArgumentException("list size doesn’t match alivePlayer");
        }

        this.game.setAlivePlayersOrder(playerOrder);
    }

    public void setCurrentPlayerTurnsLeft(int currentPlayerTurnsLeft) {
        if (currentPlayerTurnsLeft < 0) {
            throw new IllegalArgumentException("invalid turn count");
        }
        this.currentPlayerTurnsLeft = currentPlayerTurnsLeft;
    }

    public int getCurrentPlayerTurnsLeft() {
        return this.currentPlayerTurnsLeft;
    }

    public void setNextPlayerTurnsLeft(int nextPlayerTurnsLeft) {
        if (nextPlayerTurnsLeft < 0) {
            throw new IllegalArgumentException("invalid turn count");
        }
        this.nextPlayerTurnsLeft = nextPlayerTurnsLeft;

    }

    public int getNextPlayerTurnsLeft() {
        return this.nextPlayerTurnsLeft;
    }

    public CardController getControllerType(Card card) {
        Map<CardType, CardController> cardToControllerMap = new EnumMap<>(CardType.class);
        cardToControllerMap.put(CardType.ATTACK, new AttackCardController());
        cardToControllerMap.put(CardType.DEFUSE, new DefuseCardController());
        cardToControllerMap.put(CardType.SEE_THE_FUTURE, new SeeTheFutureCardController());
        cardToControllerMap.put(CardType.SHUFFLE, new ShuffleCardController());
        cardToControllerMap.put(CardType.SKIP, new SkipCardController());
        cardToControllerMap.put(CardType.NOPE, new NopeCardController());
        cardToControllerMap.put(CardType.DRAW_FROM_BOTTOM, new DrawFromBottomCardController());
        cardToControllerMap.put(CardType.CAT_CARD_1, new CatCardController());
        cardToControllerMap.put(CardType.CAT_CARD_2, new CatCardController());
        cardToControllerMap.put(CardType.CAT_CARD_3, new CatCardController());
        cardToControllerMap.put(CardType.CAT_CARD_4, new CatCardController());

        if (cardToControllerMap.containsKey(card.getType())) {
            return cardToControllerMap.get(card.getType());
        }

        throw new IllegalArgumentException("invalid test type");
    }

    public void advanceTurn() {
        this.currentPlayerIndex = this.nextPlayerIndex;
        this.nextPlayerIndex = (this.currentPlayerIndex + 1) % this.game.getAlivePlayerCount();
        this.currentPlayerTurnsLeft = this.nextPlayerTurnsLeft;
        this.nextPlayerTurnsLeft = 1;
    }

    public boolean isTargetValid(CardType type, Player initiator, Player target) {
        return type.canHaveTarget() && target != initiator;
    }

    public boolean cardsAllMatchingCatCards(ArrayList<Card> cards) {
        CardType firstType = cards.get(0).getType();
        if (!firstType.canHaveTarget()) return false;
        for (Card card : cards) {
            if (!card.getType().equals(firstType)) return false;
        }
        return true;
    }

    public boolean playerHasCards(Player initiator, ArrayList<Card> cards) {
        if (cards.isEmpty()) {
            throw new IllegalArgumentException("cards list cannot be empty");
        }
        ArrayList<Card> hand = initiator.getHand();
        ArrayList<CardType> handTypes = new ArrayList<CardType>();

        for (Card card : hand) {
            handTypes.add(card.getType());
        }

        for (Card card : cards) {
            CardType cardType = card.getType();
            if (!handTypes.remove(cardType)) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidMove(ArrayList<Card> cards, Player initiator, Optional<Player> target) {
        if (cards.isEmpty()) return false;
        if (!playerHasCards(initiator, cards)) return false;

        if (cards.size() == 1) {
            CardType type = cards.get(0).getType();
            return !type.canHaveTarget() && target.isEmpty();
        } else if (cards.size() == 2 || cards.size() == 3) {
            return cardsAllMatchingCatCards(cards)
                    && target.isPresent()
                    && isTargetValid(cards.get(0).getType(), target.get(), initiator);
        } else {
            return false;
        }
    }

    public void takeTurn(GameControllerView controllerView) {
        Player currentPlayer = game.getAlivePlayers().get(currentPlayerIndex);

        controllerView.displayCurrentPlayerAndCardsInHand(currentPlayer);
        String userChoice = controllerView.getCardChoiceOrDraw();

        if (userChoice.equalsIgnoreCase("d")) {
            game.draw(currentPlayer, game.getDeck());
            this.currentPlayerTurnsLeft--;
        } else {
            try {
                String[] inputIndices = userChoice.split(",");
                ArrayList<Card> cardsPlayed = new ArrayList<>();

                Set<Integer> uniqueIndices = new HashSet<>();
                boolean hasDuplicateIndex = false;

                for (String input : inputIndices) {
                    int cardIndex = Integer.parseInt(input.trim());

                    if (!uniqueIndices.add(cardIndex)) {
                        hasDuplicateIndex = true;
                        break;
                    }
                    cardsPlayed.add(currentPlayer.getHand().get(cardIndex));
                }

                if (hasDuplicateIndex) {
                    controllerView.displayDuplicateCardInMove(userChoice);
                } else {
                    Optional<Player> target = Optional.empty();
                    if (cardsPlayed.size() >= 2) {
                        ArrayList<Player> arrayListAlivePlayers =
                                new ArrayList<Player>(game.getAlivePlayers());
                        String targetChoice = controllerView
                                .getTargetPlayerIndex(arrayListAlivePlayers, currentPlayer);
                        int targetIdx = Integer.parseInt(targetChoice.trim());
                        target = Optional.of(game.getAlivePlayers().get(targetIdx));
                    }

                    if (isValidMove(cardsPlayed, currentPlayer, target)) {
                        CardController cardController = getControllerType(cardsPlayed.get(0));
                        cardController.executeCardAction(this, currentPlayer, target);

                        for (Card card : cardsPlayed) {
                            currentPlayer.removeCard(card);
                        }
                    } else {
                        controllerView.displayInvalidMove(cardsPlayed);
                    }
                }
            } catch (NumberFormatException e) {
                controllerView.displayInvalidChoice(userChoice);
            } catch (IndexOutOfBoundsException e) {
                controllerView.displayInvalidIndex(userChoice);
            }
        }
    }


    boolean playerHasCardOfType(Player player, CardType type) {
        return player.getHand().stream()
                .anyMatch(c -> c.getType() == type);
    }

    public void runGame(GameControllerView view) {
    while (game.getAlivePlayerCount() > 1) {
        while (currentPlayerTurnsLeft > 0) {
            Player currentPlayer = game.getAlivePlayers().get(currentPlayerIndex);
            takeTurn(view);
            if (playerHasCardOfType(currentPlayer, CardType.EXPLODING_KITTEN)
                    && !playerHasCardOfType(currentPlayer, CardType.DEFUSE)) {
                int eliminatedIndex = currentPlayerIndex;
                game.removeAlivePlayer(currentPlayer);
                if (nextPlayerIndex > eliminatedIndex) {
                    nextPlayerIndex--;
                }
                break;
            }
        }
        if (game.getAlivePlayerCount() > 1) {
            advanceTurn();
            }
        }
    }
}
