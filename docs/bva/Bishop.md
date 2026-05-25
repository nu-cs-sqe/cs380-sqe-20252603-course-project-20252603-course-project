# BVA Analysis for Bishop

`Bishop` is concrete. This file covers the public API declared in `Bishop.java`: the constructor, `makeCopy()`, `isValidMoveShape()`, and `isLegalMove()`. Inherited methods are covered in `docs/bva/Piece.md`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - The constructor input `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `color` is a Java reference type, `null` is also a settable boundary value.
  - `makeCopy()` has no parameters, but its behavior depends on the valid receiver state: a `Bishop` with `color = BLACK` or `color = WHITE`.
  - `isValidMoveShape(from, to)` boundary variables: `from` (null vs. Location), `to` (null vs. valid/invalid Location), and the diagonal constraint: |dx|=|dy|>0 (valid diagonal), |dx|≠|dy| (non-diagonal), dx=dy=0 (same square).
  - `isLegalMove(from, to, board)` adds: `board` (null vs. Piece[][]), path occupation (clear vs. blocked), destination (empty, own piece, opponent piece).
- Step 1, output equivalence classes:
  - Constructor: creates a `Bishop` with `type=BISHOP` and given `color`; rejects null color.
  - `makeCopy()`: returns a distinct `Bishop` with same color.
  - `isValidMoveShape`: `true` for valid diagonal; `false` for non-diagonal, same-square, or off-board destination.
  - `isLegalMove`: `true` for clear diagonal path to empty/opponent square; `false` for blocked path, own-piece destination, or invalid shape.
- Step 2, BVA catalog mapping:
  - `PieceColor`: Cases + Pointers (null).
  - Move deltas: Bounded Integer; key boundary is |dx|=|dy| (diagonal) vs. |dx|≠|dy|. Max delta = 7 (full-board diagonal a1–h8). Min delta = 1 (adjacent diagonal).
  - Path length: Integer min 0 (adjacent), max 6 (full-board diagonal minus endpoints).
  - Destination: Cases (empty, own, opponent) + Pointers.
- Step 3, concrete boundary values selected:
  - `PieceColor`: BLACK, WHITE, null.
  - Shape: diagonal down-right, down-left, up-right, up-left (each-choice over four diagonal directions). Horizontal (dy=0), vertical (dx=0), L-shape (|dx|≠|dy|, both non-zero), same-square.
  - Path: 0 intermediates (adjacent), 6 intermediates (7-square diagonal).
  - Destination: empty (null), own piece, opponent piece.
- Step 4 strategy: each-choice over the single varying boundary variable per method.


### Method under test: `Bishop(PieceColor color)`

|             | System under test                       | Expected output                                                                 | Implemented? |
|-------------|-----------------------------------------|---------------------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `color = BLACK`       | bishop constructs successfully; `getType()` returns `BISHOP`; `getColor()` returns `BLACK` | :white_check_mark: |
| Test Case 2 | constructor args: `color = WHITE`       | bishop constructs successfully; `getType()` returns `BISHOP`; `getColor()` returns `WHITE` | :white_check_mark: |
| Test Case 3 | constructor args: `color = null`        | `Bishop(null)` throws `IllegalArgumentException` with message `"color must not be null"` | :white_check_mark: |


### Method under test: `makeCopy()`

|             | System under test              | Expected output                                                                                         | Implemented? |
|-------------|--------------------------------|---------------------------------------------------------------------------------------------------------|--------------|
| Test Case 4 | bishop: `Bishop(BLACK)`        | return a distinct `Piece`; copied piece is a `Bishop`; copied piece has `type = BISHOP`; copied piece has `color = BLACK` | :white_check_mark: |
| Test Case 5 | bishop: `Bishop(WHITE)`        | return a distinct `Piece`; copied piece is a `Bishop`; copied piece has `type = BISHOP`; copied piece has `color = WHITE` | :white_check_mark: |
| Test Case 6 | bishop: `Bishop(null)`         | `Bishop(null)` throws `IllegalArgumentException` with message `"color must not be null"`; null-color `makeCopy()` receiver state is not constructible | :white_check_mark: |


### Method under test: `isValidMoveShape(Location from, Location to)`

The bishop moves only on diagonals: |dx| must equal |dy| and both must be non-zero. Maximum legal delta is 7. Off-board destination is rejected.

|              | System under test                                                       | Expected output | Implemented? |
|--------------|-------------------------------------------------------------------------|-----------------|--------------|
| Test Case 7  | `from=(4,4)`, `to=(6,6)` — diagonal down-right, delta 2               | `true`          | :white_check_mark: |
| Test Case 8  | `from=(4,4)`, `to=(2,2)` — diagonal up-left, delta 2                  | `true`          | :white_check_mark: |
| Test Case 9  | `from=(4,4)`, `to=(2,6)` — diagonal down-left, delta 2                | `true`          | :white_check_mark: |
| Test Case 10 | `from=(4,4)`, `to=(6,2)` — diagonal up-right, delta 2                 | `true`          | :white_check_mark: |
| Test Case 11 | `from=(0,0)`, `to=(7,7)` — max diagonal, delta 7                      | `true`          | :white_check_mark: |
| Test Case 12 | `from=(3,3)`, `to=(4,4)` — min diagonal, delta 1                      | `true`          | :white_check_mark: |
| Test Case 13 | `from=(4,4)`, `to=(4,6)` — straight vertical (dy≠0, dx=0)             | `false`         | :white_check_mark: |
| Test Case 14 | `from=(4,4)`, `to=(6,4)` — straight horizontal (dx≠0, dy=0)           | `false`         | :white_check_mark: |
| Test Case 15 | `from=(4,4)`, `to=(4,4)` — same square                                | `false`         | :white_check_mark: |
| Test Case 16 | `from=(4,4)`, `to=(6,5)` — L-shape (|dx|≠|dy|, both non-zero)         | `false`         | :white_check_mark: |
| Test Case 17 | `from=(6,6)`, `to=(8,8)` — destination off board                      | `false`         | :white_check_mark: |
| Test Case 18 | `from=(2,2)`, `to=(-1,-1)` — destination off board (below min)        | `false`         | :white_check_mark: |
| Test Case 19 | `from=null`, `to=(5,5)`                                                | `IllegalArgumentException("from must not be null")` | :white_check_mark: |
| Test Case 20 | `from=(4,4)`, `to=null`                                                | `IllegalArgumentException("to must not be null")`   | :white_check_mark: |


### Method under test: `isLegalMove(Location from, Location to, Piece[][] board)`

A bishop move is legal when: the shape is a valid diagonal, no piece occupies any intermediate square, and the destination is empty or holds an opponent piece.

|              | System under test                                                                               | Expected output | Implemented? |
|--------------|-------------------------------------------------------------------------------------------------|-----------------|--------------|
| Test Case 21 | empty board; `from=(0,0)`, `to=(7,7)` — clear diagonal, 6 intermediates                        | `true`          | :white_check_mark: |
| Test Case 22 | opponent piece at `(3,3)`; `from=(0,0)`, `to=(6,6)` — blocked diagonal path                   | `false`         | :white_check_mark: |
| Test Case 23 | own bishop at destination `(7,7)`; `from=(0,0)`, `to=(7,7)`                                    | `false`         | :white_check_mark: |
| Test Case 24 | opponent rook at destination `(7,7)`; `from=(0,0)`, `to=(7,7)` — capture                       | `true`          | :white_check_mark: |
| Test Case 25 | empty board; `from=(4,4)`, `to=(4,6)` — vertical (invalid shape)                               | `false`         | :white_check_mark: |
| Test Case 26 | `from=null`                                                                                     | `IllegalArgumentException("from must not be null")` | :white_check_mark: |
| Test Case 27 | `to=null`                                                                                       | `IllegalArgumentException("to must not be null")`   | :white_check_mark: |
| Test Case 28 | `board=null`                                                                                    | `IllegalArgumentException("board must not be null")` | :white_check_mark: |
