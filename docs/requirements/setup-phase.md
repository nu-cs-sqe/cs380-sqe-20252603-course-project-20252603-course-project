# Feature: Game Setup Phase

## User Story

As a player of Chess, I want the game to be properly set up automatically when a new game starts, so that I can begin playing immediately with the correct board layout and piece positions.

### Acceptance Criteria

✅ The game must support exactly two players (White and Black).

✅ Players must be able to enter their names before the game begins.

✅ All 32 pieces must be placed on the correct starting squares when the game initializes.

✅ White pieces must occupy ranks 1 and 2; Black pieces must occupy ranks 7 and 8.

✅ The GUI board must display the correct colors and piece symbols/icons.

✅ White must be designated as the first player to move.

✅ The game status must indicate "White's turn" at the start.

✅ No moves are permitted until setup is complete.

---

## Use Case 1: Start New Game

**Actor**: Player

**Preconditions**: The chess application is launched.

**Main Flow**:

1. Player clicks "New Game" button.
2. System prompts for Player 1 (White) name.
3. Player enters name for White.
4. System prompts for Player 2 (Black) name.
5. Player enters name for Black.
6. Player clicks "Start".
7. System initializes the 8x8 board.
8. System places all 16 White pieces on ranks 1–2 in standard positions.
9. System places all 16 Black pieces on ranks 7–8 in standard positions.
10. System sets the current turn to White.
11. GUI renders the board with all pieces in correct positions.
12. Game status bar displays "White's turn".

**Alternate Flows**:

- 3a. Player leaves name blank → system assigns default name "Player 1".
- 5a. Player leaves name blank → system assigns default name "Player 2".

**Postconditions**:

- The board is fully initialized with all 32 pieces in standard chess starting positions.
- White is set as the active player.
- The game is ready for White's first move.

---

## Use Case 2: View Initial Board

**Actor**: Player

**Preconditions**: Game setup has completed (Use Case 1 postconditions are met).

**Main Flow**:

1. Player views the GUI board window.
2. System displays the 8x8 grid with alternating light/dark squares.
3. System renders each piece on its correct starting square.
4. System highlights that it is White's turn.

**Postconditions**: Player can visually verify the standard starting position.