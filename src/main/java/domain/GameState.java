package domain;

/**
 * Represents the current state of the chess game.
 *
 * Game state meanings:
 * - WHITE_TURN: White player is currently to move (game start state)
 * - BLACK_TURN: Black player is currently to move
 * - WHITE_WIN: White player has won the game
 * - BLACK_WIN: Black player has won the game
 * - DRAW: Game has ended in a draw
 *
 * The initial game state for a new Board is WHITE_TURN.
 */
public enum GameState {
  WHITE_TURN,
  BLACK_TURN,
  WHITE_WIN,
  BLACK_WIN,
  DRAW
}
