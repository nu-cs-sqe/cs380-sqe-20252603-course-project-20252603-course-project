# BVA Analysis for Queen

`Queen` is concrete. This file covers the public API declared in `Queen.java`: the constructor, `makeCopy()`, and `isValidMoveShape()`. Inherited methods are covered in `docs/bva/Piece.md`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - The constructor input `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `color` is a Java reference type, `null` is also a settable boundary value.
  - `makeCopy()` has no parameters, but its behavior depends on the valid receiver state: a `Queen` with `color = BLACK` or `color = WHITE`.
  - `isValidMoveShape(from, to)` boundary variables: `from` (null vs. Location), `to` (null vs. Location), and the queen's shape constraint: rank (dy=0, dx≠0), file (dx=0, dy≠0), diagonal (|dx|=|dy|>0), or invalid (none of these).
- Step 1, output equivalence classes:
  - Constructor: creates a `Queen` with `type=QUEEN` and given `color`; rejects null color.
  - `makeCopy()`: returns a distinct `Queen` with same color.
  - `isValidMoveShape`: `true` for rank, file, or diagonal; `false` for L-shape or same-square. Bounds checking is Board's responsibility.
- Step 2, BVA catalog mapping:
  - `PieceColor`: Cases + Pointers (null).
  - Move shape: Cases — rank (dy=0), file (dx=0), diagonal (|dx|=|dy|), invalid (L-shape, same-square).
- Step 3, concrete boundary values selected:
  - `PieceColor`: BLACK, WHITE, null.
  - Shape: rank right, rank left, file down, file up, diagonal down-right, diagonal up-left, diagonal down-left; L-shape (invalid); same-square.
- Step 4 strategy: each-choice over the single varying boundary variable per method; for `isValidMoveShape` each shape category is exercised once.


### Method under test: `Queen(PieceColor color)`

|             | System under test                      | Expected output                                                               | Implemented? |
|-------------|----------------------------------------|-------------------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `color = BLACK`      | queen constructs successfully; `getType()` returns `QUEEN`; `getColor()` returns `BLACK` | :white_check_mark: |
| Test Case 2 | constructor args: `color = WHITE`      | queen constructs successfully; `getType()` returns `QUEEN`; `getColor()` returns `WHITE` | :white_check_mark: |
| Test Case 3 | constructor args: `color = null`       | `Queen(null)` throws `IllegalArgumentException` with message `"color must not be null"` | :white_check_mark: |


### Method under test: `makeCopy()`

|             | System under test            | Expected output                                                                                       | Implemented? |
|-------------|------------------------------|-------------------------------------------------------------------------------------------------------|--------------|
| Test Case 4 | queen: `Queen(BLACK)`        | return a distinct `Piece`; copied piece is a `Queen`; copied piece has `type = QUEEN`; copied piece has `color = BLACK` | :white_check_mark: |
| Test Case 5 | queen: `Queen(WHITE)`        | return a distinct `Piece`; copied piece is a `Queen`; copied piece has `type = QUEEN`; copied piece has `color = WHITE` | :white_check_mark: |
| Test Case 6 | queen: `Queen(null)`         | `Queen(null)` throws `IllegalArgumentException` with message `"color must not be null"`; null-color `makeCopy()` receiver state is not constructible | :white_check_mark: |


### Method under test: `isValidMoveShape(Location from, Location to)`

The queen combines rook and bishop movement: rank (dy=0, dx≠0), file (dx=0, dy≠0), or diagonal (|dx|=|dy|>0). Maximum legal delta is 7. Same-square is rejected. Bounds checking is Board's responsibility; `isValidMoveShape` validates shape only.

|              | System under test                                                       | Expected output | Implemented? |
|--------------|-------------------------------------------------------------------------|-----------------|--------------|
| Test Case 7  | `from=(0,4)`, `to=(7,4)` — rank right, max delta dx=7                  | `true`          | :white_check_mark: |
| Test Case 8  | `from=(7,4)`, `to=(0,4)` — rank left, max delta dx=7                   | `true`          | :white_check_mark: |
| Test Case 9  | `from=(4,0)`, `to=(4,7)` — file down, max delta dy=7                   | `true`          | :white_check_mark: |
| Test Case 10 | `from=(4,7)`, `to=(4,0)` — file up, max delta dy=7                     | `true`          | :white_check_mark: |
| Test Case 11 | `from=(0,0)`, `to=(7,7)` — diagonal down-right, max delta 7            | `true`          | :white_check_mark: |
| Test Case 12 | `from=(7,7)`, `to=(0,0)` — diagonal up-left, max delta 7               | `true`          | :white_check_mark: |
| Test Case 13 | `from=(7,0)`, `to=(0,7)` — diagonal down-left, max delta 7             | `true`          | :white_check_mark: |
| Test Case 14 | `from=(4,4)`, `to=(4,4)` — same square (dx=0, dy=0)                    | `false`         | :white_check_mark: |
| Test Case 15 | `from=(4,4)`, `to=(6,5)` — L-shape (|dx|≠|dy|, dy≠0, dx≠0)            | `false`         | :white_check_mark: |
| Test Case 16 | `from=null`, `to=(4,4)`                                                 | `IllegalArgumentException("from must not be null")` | :white_check_mark: |
| Test Case 17 | `from=(4,4)`, `to=null`                                                 | `IllegalArgumentException("to must not be null")`   | :white_check_mark: |
