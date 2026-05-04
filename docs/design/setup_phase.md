
# Game Setup Phase Design

## Feature Scope
The setup phase initializes a new chess game so that the board is ready for the first move.

---

## Architectural Pattern
This project follows the MVC pattern.

- Model: Handles game logic and state
- View: Displays the board and UI
- Controller: Handles user actions and triggers model updates

## Model (Domain Logic)

### Game

Methods:
- initializeGame()
    - Behavior: Initializes the board with standard chess starting positions and sets current turn to WHITE

- getBoard()
    - Returns: Board

- getCurrentTurn()
    - Returns: PieceColor

---

### Board

Responsibilities:
- Maintains 8x8 grid of pieces
- Places pieces in starting positions

Methods:
- getSize()
    - Returns 8

- getSquare(int row, int col)
    - Returns: Piece at given position
    - Throws IndexOutOfBoundsException if row or col is outside 0–7
 
- setupInitialPosition()
    - Behavior: Places all pieces in standard starting positions
    - Assumption: Board is empty before initialization
    - Edge Case: If board is not empty, behavior is undefined or may overwrite  

- isEmpty(int row, int col)
    - Returns: true if no piece exists at position
    - Throws IndexOutOfBoundsException if row or col is outside 0–7
---

### Piece
Responsibilities:
- Represents a chess piece

Fields:
- PieceType type
- PieceColor color

---

### PieceType (Enum)
- KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN

---

### PieceColor (Enum)
- WHITE, BLACK

---

## Controller

### GameController
Responsibilities:
- Handles user actions (start game)
- Calls Game.initializeGame()

Methods:
- startGame()

---

## View

### BoardView
Responsibilities:
- Displays the chess board
- Updates UI after setup

Methods:
- displayBoard(Board board)

---


## Dependencies

GameController depends on:
- Game

Game depends on:
- Board

Board depends on:
- Piece

BoardView depends on:
- Board

---

## Testability

- Model classes (Game, Board) are unit testable
- View classes are not unit tested
- No external dependencies → no mocking required in setup phase
