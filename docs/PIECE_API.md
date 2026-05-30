# Piece Movement API Documentation

## `isValidMoveShape(from: Location, to: Location): boolean`

This method validates if the geometric shape of the move is valid for the piece, regardless of the board state (except for the board boundaries and self-displacement).

### Concrete Pieces

#### Pawn
- **Valid Shapes**:
    - One square forward (towards row 0 for White, towards row 7 for Black).
    - Two squares forward if at the starting row (row 6 for White, row 1 for Black).
    - One square diagonally forward (for captures).
- **Invalid Shapes**:
    - Backward moves.
    - Horizontal moves.
    - Moves exceeding the forward or diagonal reach.
- **Special Conditions**: First-move rule (two squares) is handled based on the starting row.

#### Knight
- **Valid Shapes**: "L-shape" moves (2 squares in one cardinal direction and 1 square perpendicularly).
- **Invalid Shapes**: Any non-L-shape move.
- **Special**: Can jump over other pieces (handled by `canJump()`).

#### Rook
- **Valid Shapes**: Any number of squares horizontally or vertically.
- **Invalid Shapes**: Diagonal moves or any move not in a straight line.

#### Bishop
- **Valid Shapes**: Any number of squares diagonally.
- **Invalid Shapes**: Horizontal or vertical moves.

#### Queen
- **Valid Shapes**: Any number of squares horizontally, vertically, or diagonally.
- **Invalid Shapes**: Any move not in a straight line.

#### King
- **Valid Shapes**: One square in any direction (horizontal, vertical, or diagonal).
- **Invalid Shapes**: Any move exceeding one square.

---

## `canJump(): boolean`

Returns `true` if the piece can jump over other pieces on the board.

- **Knight**: Returns `true`.
- **All other pieces**: Returns `false`.

---

## Piece API Gaps

During the API review, the following gaps were identified:

1.  **En Passant**: Not currently implemented in the `Pawn` movement logic. This requires tracking the opponent's last move.
2.  **Pawn Promotion**: Not implemented. `Board` logic needs to handle pawns reaching the back rank.
3.  **Draw conditions**: Stalemate, threefold repetition, and insufficient material are not yet implemented.
4.  **Move History**: Pieces have a `hasMoved` flag, but a full move history is not yet maintained at the board or game level.
