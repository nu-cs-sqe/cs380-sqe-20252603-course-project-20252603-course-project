# Design: Game Setup Phase

## Overview

This document describes the classes and methods needed to implement the Chess Game Setup Phase. The design follows a layered structure: model (game logic), view (GUI), and controller (coordination).

---

## Class Design

### 1. `Position`
Represents a square on the board.

```java
class Position {
    int row;   // 0–7 (rank 1–8)
    int col;   // 0–7 (file a–h)

    Position(int row, int col)
    boolean isValid()              // returns true if row and col are in [0,7]
    boolean equals(Object o)
}
```

---

### 2. `PieceType` (enum)
```java
enum PieceType { KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN }
```

---

### 3. `Color` (enum)
```java
enum Color { WHITE, BLACK }
```

---

### 4. `Piece`
Represents a single chess piece. Immutable value object.

```java
class Piece {
    PieceType type;
    Color color;

    Piece(PieceType type, Color color)
    PieceType getType()
    Color getColor()
}
```

---

### 5. `Board`
Holds the 8x8 grid and manages piece placement.

```java
class Board {
    Piece[][] grid;   // grid[row][col], null means empty

    Board()                                        // creates empty board
    void placePiece(Piece piece, Position pos)
    Piece getPieceAt(Position pos)
    boolean isEmpty(Position pos)
    void initialize()                              // places all 32 pieces in standard starting positions
}
```

**`initialize()` starting layout** (row 0 = rank 1 for White):

| Row | Color | Pieces (col 0–7) |
|-----|-------|-----------------|
| 0   | White | R N B Q K B N R |
| 1   | White | P P P P P P P P |
| 6   | Black | P P P P P P P P |
| 7   | Black | R N B Q K B N R |

---

### 6. `Player`
Represents one of the two players.

```java
class Player {
    String name;
    Color color;

    Player(String name, Color color)
    String getName()
    Color getColor()
}
```

---

### 7. `GameState`
Tracks whose turn it is and the overall game status.

```java
enum GameStatus { SETUP, IN_PROGRESS, CHECKMATE, STALEMATE, DRAW }

class GameState {
    Color currentTurn;      // whose turn it is
    GameStatus status;

    GameState()             // initializes with currentTurn=WHITE, status=SETUP
    Color getCurrentTurn()
    GameStatus getStatus()
    void setStatus(GameStatus status)
    void switchTurn()
}
```

---

### 8. `Game`
Top-level coordinator. Owns `Board`, both `Player`s, and `GameState`.

```java
class Game {
    Board board;
    Player whitePlayer;
    Player blackPlayer;
    GameState state;

    Game(String whiteName, String blackName)
    void setup()            // calls board.initialize(), sets state to IN_PROGRESS
    Board getBoard()
    Player getCurrentPlayer()
    GameState getState()
}
```

---

### 9. `BoardPanel` (GUI — Java Swing)
Renders the board in a `JPanel`.

```java
class BoardPanel extends JPanel {
    Game game;

    BoardPanel(Game game)
    void paintComponent(Graphics g)   // draws squares and pieces
    void refresh()                    // repaint after state change
}
```

---

### 10. `SetupDialog` (GUI — Java Swing)
Dialog for entering player names.

```java
class SetupDialog extends JDialog {
    JTextField whiteNameField;
    JTextField blackNameField;

    SetupDialog(JFrame parent)
    String getWhiteName()
    String getBlackName()
}
```

---

### 11. `MainFrame` (GUI — Java Swing)
The main application window.

```java
class MainFrame extends JFrame {
    BoardPanel boardPanel;
    JLabel statusLabel;

    MainFrame()
    void startNewGame()     // shows SetupDialog, creates Game, refreshes board
    void updateStatus()     // updates statusLabel with current turn
}
```

---

## Class Relationships

```
MainFrame
 ├── SetupDialog        (creates on "New Game" click)
 ├── BoardPanel         (renders game state)
 └── Game
      ├── Board          (holds Piece[][])
      ├── Player (White)
      ├── Player (Black)
      └── GameState
```

---

## Package Structure

```
src/main/java/
├── model/
│   ├── Board.java
│   ├── Color.java
│   ├── Game.java
│   ├── GameState.java
│   ├── GameStatus.java
│   ├── Piece.java
│   ├── PieceType.java
│   ├── Player.java
│   └── Position.java
└── ui/
    ├── BoardPanel.java
    ├── MainFrame.java
    ├── SetupDialog.java
    └── Main.java
```

---

## Notes

- GUI library: **Java Swing**
- Model classes contain no Swing dependencies — they are pure Java and fully unit-testable.
- `Board.initialize()` is the primary target for unit tests in the setup phase.