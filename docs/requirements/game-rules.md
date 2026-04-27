# Chess Game Rules

## Overview

Chess is a two-player strategy board game played on an 8x8 grid. Each player controls 16 pieces: one king, one queen, two rooks, two knights, two bishops, and eight pawns. The objective is to checkmate the opponent's king.

## Players

- Two players: White and Black.
- White always moves first.

## The Board

- 8x8 grid of alternating light and dark squares.
- Columns are labeled a–h (files); rows are labeled 1–8 (ranks).
- White pieces start on ranks 1–2; Black pieces start on ranks 7–8.

## Initial Setup

```
Black (rank 8): Rook Knight Bishop Queen King Bishop Knight Rook
Black pawns (rank 7): 8 Pawns
... (empty ranks 3–6) ...
White pawns (rank 2): 8 Pawns
White (rank 1): Rook Knight Bishop Queen King Bishop Knight Rook
```

## Piece Movement Rules

| Piece  | Movement |
|--------|----------|
| King   | One square in any direction |
| Queen  | Any number of squares in any direction |
| Rook   | Any number of squares horizontally or vertically |
| Bishop | Any number of squares diagonally |
| Knight | L-shape: 2 squares in one direction + 1 perpendicular; can jump over pieces |
| Pawn   | Forward one square (two squares from starting rank); captures diagonally |

## Special Moves

- **Castling**: King moves two squares toward a rook; rook jumps to the other side. Conditions: neither piece has moved, no pieces between them, king not in check.
- **En Passant**: A pawn captures an adjacent enemy pawn that just advanced two squares, as if it had advanced only one.
- **Pawn Promotion**: When a pawn reaches the opposite back rank, it is promoted to a queen, rook, bishop, or knight.

## Win/Draw Conditions

- **Checkmate**: The king is in check and has no legal moves — that player loses.
- **Stalemate**: A player has no legal moves but is not in check — the game is a draw.
- **Draw**: By agreement, insufficient material, threefold repetition, or 50-move rule.