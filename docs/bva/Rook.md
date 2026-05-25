# BVA Analysis for Rook

`Rook` is a concrete `Piece` subclass. Its constructor fixes the piece type to `PieceType.ROOK`, so the only constructor input boundary is the `PieceColor color` argument. The source under analysis is `src/main/java/domain/piece/Rook.java`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `PieceColor` is a Java reference type, `null` is an invalid pointer boundary for `color`.
  - `makeCopy()` has no parameters, so its input classes come from the existing valid `Rook` state: `color = BLACK` or `color = WHITE`.
  - A `Rook` with `color = null` is not a valid public state once constructor null handling is corrected.
  - `isValidMoveShape(from, to)` boundary variables: `from` (null vs. Location), `to` (null vs. valid/invalid Location), rank/file deltas: dx=0 (file move), dy=0 (rank move), dx≠0 and dy≠0 (non-rook shape).
  - `isLegalMove(from, to, board)` adds: `board` (null vs. Piece[][]), path occupation (clear vs. blocked), destination occupation (empty, own piece, opponent piece).
- Step 1, output equivalence classes:
  - Constructor: successful Rook with `type=ROOK` and given `color`; or `IllegalArgumentException` for null.
  - `makeCopy()`: distinct Rook with same color.
  - `isValidMoveShape`: `true` for rank/file moves with valid destination; `false` for diagonal, same square, or off-board destination.
  - `isLegalMove`: `true` for clear path to empty/opponent square; `false` for blocked path, own-piece destination, or invalid shape.
- Step 2, BVA catalog mapping:
  - `PieceColor`: Cases (BLACK, WHITE) + Pointers (null).
  - Move deltas dx, dy: Bounded Integer with domain [−7, 7]; key boundary is 0 (rank/file) vs. non-zero.
  - Path length: Integer with min 0 (adjacent move, no intermediates) and max 6 (full-board move, 6 intermediates).
  - Destination piece: Cases (null=empty, own color, opponent color) + Pointers (null reference for null board).
- Step 3, concrete boundary values selected:
  - `PieceColor`: BLACK, WHITE, null.
  - dx, dy combinations: (dx=0, dy≠0), (dx≠0, dy=0), (dx≠0, dy≠0), (dx=0, dy=0).
  - Path: 0 intermediates (min), 6 intermediates (max = 7-square board move minus endpoints).
  - Destination: empty (null), own piece, opponent piece.
  - Board: null pointer boundary.
- Step 4 strategy: each-choice across the color enum and null pointer boundaries for constructor/makeCopy; each-choice across dx/dy shape categories, path length extremes, and destination piece categories for isValidMoveShape/isLegalMove.


### Method under test: `Rook(PieceColor color)`

|             | System under test                      | Expected output                                                               | Implemented? |
|-------------|----------------------------------------|-------------------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `color = BLACK`      | rook constructs successfully; rook has `type = ROOK`; rook has `color = BLACK` | :white_check_mark: |
| Test Case 2 | constructor args: `color = WHITE`      | rook constructs successfully; rook has `type = ROOK`; rook has `color = WHITE` | :white_check_mark: |
| Test Case 3 | constructor args: `color = null`       | throws `IllegalArgumentException` with message `"color must not be null"`       | :white_check_mark: |


### Method under test: `makeCopy()`

|             | System under test               | Expected output                                                                                  | Implemented? |
|-------------|---------------------------------|--------------------------------------------------------------------------------------------------|--------------|
| Test Case 4 | rook: `Rook(BLACK)`             | return a distinct `Rook` as a `Piece`; copied piece has `type = ROOK`; copied piece has `color = BLACK` | :white_check_mark: |
| Test Case 5 | rook: `Rook(WHITE)`             | return a distinct `Rook` as a `Piece`; copied piece has `type = ROOK`; copied piece has `color = WHITE` | :white_check_mark: |
| Test Case 6 | rook: `Rook(null)`              | null-color rook is `CAN'T SET` through the public constructor; covered by constructor null rejection with message `"color must not be null"` | implemented in `RookConstructor_NullColor_ThrowsIllegalArgumentException` |


### Method under test: `isValidMoveShape(Location from, Location to)`

The rook moves along a rank (dy=0, dx≠0) or file (dx=0, dy≠0). Maximum legal delta is 7 (full board). Minimum legal delta is 1 (one square). Same square (dx=0, dy=0) is rejected. Off-board destination is rejected. `from` or `to` null throws `IllegalArgumentException`.

|              | System under test                                                         | Expected output | Implemented? |
|--------------|---------------------------------------------------------------------------|-----------------|--------------|
| Test Case 7  | `from=(0,4)`, `to=(7,4)` — rank move right, max delta dx=7               | `true`          | :white_check_mark: |
| Test Case 8  | `from=(7,4)`, `to=(0,4)` — rank move left, max delta dx=7                | `true`          | :white_check_mark: |
| Test Case 9  | `from=(3,0)`, `to=(3,7)` — file move down, max delta dy=7                | `true`          | :white_check_mark: |
| Test Case 10 | `from=(3,7)`, `to=(3,0)` — file move up, max delta dy=7                  | `true`          | :white_check_mark: |
| Test Case 11 | `from=(3,4)`, `to=(4,4)` — rank move, min delta dx=1                     | `true`          | :white_check_mark: |
| Test Case 12 | `from=(3,4)`, `to=(3,5)` — file move, min delta dy=1                     | `true`          | :white_check_mark: |
| Test Case 13 | `from=(3,4)`, `to=(5,6)` — diagonal (dx≠0 and dy≠0)                     | `false`         | :white_check_mark: |
| Test Case 14 | `from=(4,4)`, `to=(4,4)` — same square (dx=0 and dy=0)                  | `false`         | :white_check_mark: |
| Test Case 15 | `from=(3,4)`, `to=(3,8)` — destination y above max                       | `false`         | :white_check_mark: |
| Test Case 16 | `from=(3,4)`, `to=(3,-1)` — destination y below min                      | `false`         | :white_check_mark: |
| Test Case 17 | `from=(3,4)`, `to=(8,4)` — destination x above max                       | `false`         | :white_check_mark: |
| Test Case 18 | `from=(3,4)`, `to=(-1,4)` — destination x below min                      | `false`         | :white_check_mark: |
| Test Case 19 | `from=null`, `to=(4,4)`                                                   | `IllegalArgumentException("from must not be null")` | :white_check_mark: |
| Test Case 20 | `from=(4,4)`, `to=null`                                                   | `IllegalArgumentException("to must not be null")`   | :white_check_mark: |


### Method under test: `isLegalMove(Location from, Location to, Piece[][] board)`

A rook move is legal when: the shape is valid, no piece occupies any intermediate square, and the destination is either empty or holds an opponent piece.

|              | System under test                                                                         | Expected output | Implemented? |
|--------------|-------------------------------------------------------------------------------------------|-----------------|--------------|
| Test Case 21 | empty board; `from=(0,4)`, `to=(7,4)` — clear rank path                                  | `true`          | :white_check_mark: |
| Test Case 22 | empty board; `from=(4,0)`, `to=(4,7)` — clear file path                                  | `true`          | :white_check_mark: |
| Test Case 23 | opponent piece at `(3,4)`; `from=(0,4)`, `to=(6,4)` — intermediate square blocked       | `false`         | :white_check_mark: |
| Test Case 24 | opponent piece at `(4,3)`; `from=(4,0)`, `to=(4,6)` — intermediate square blocked       | `false`         | :white_check_mark: |
| Test Case 25 | own piece at destination `(7,4)`; `from=(0,4)`, `to=(7,4)`                               | `false`         | :white_check_mark: |
| Test Case 26 | opponent piece at destination `(7,4)`; `from=(0,4)`, `to=(7,4)` — capture                | `true`          | :white_check_mark: |
| Test Case 27 | empty board; `from=(4,4)`, `to=(6,6)` — diagonal shape (invalid)                         | `false`         | :white_check_mark: |
| Test Case 28 | `from=null`                                                                               | `IllegalArgumentException("from must not be null")` | :white_check_mark: |
| Test Case 29 | `to=null`                                                                                 | `IllegalArgumentException("to must not be null")`   | :white_check_mark: |
| Test Case 30 | `board=null`                                                                              | `IllegalArgumentException("board must not be null")` | :white_check_mark: |
