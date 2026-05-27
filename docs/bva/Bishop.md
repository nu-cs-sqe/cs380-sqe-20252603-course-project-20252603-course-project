# BVA Analysis for Bishop

`Bishop` is concrete. This file covers the public API declared in `Bishop.java`: the constructor, `makeCopy()`, and `isValidMoveShape()`. Inherited methods are covered in `docs/bva/Piece.md`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - The constructor input `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `color` is a Java reference type, `null` is also a settable boundary value.
  - `makeCopy()` has no parameters, but its behavior depends on the valid receiver state: a `Bishop` with `color = BLACK` or `color = WHITE`.
  - `isValidMoveShape(from, to)` boundary variables: `from` (null vs. Location), `to` (null vs. Location), and the diagonal constraint: |dx|=|dy|>0 (valid diagonal), |dx|≠|dy| (non-diagonal), dx=dy=0 (same square).
- Step 1, output equivalence classes:
  - Constructor: creates a `Bishop` with `type=BISHOP` and given `color`; rejects null color.
  - `makeCopy()`: returns a distinct `Bishop` with same color.
  - `isValidMoveShape`: `true` for valid diagonal; `false` for non-diagonal or same-square.
- Step 2, BVA catalog mapping:
  - `PieceColor`: Cases + Pointers (null).
  - Move deltas: Bounded Integer; key boundary is |dx|=|dy| (diagonal) vs. |dx|≠|dy|. Max delta = 7 (full-board diagonal a1–h8). Min delta = 1 (adjacent diagonal).
- Step 3, concrete boundary values selected:
  - `PieceColor`: BLACK, WHITE, null.
  - Shape: diagonal down-right, down-left, up-right, up-left (each-choice over four diagonal directions). Horizontal (dy=0), vertical (dx=0), L-shape (|dx|≠|dy|, both non-zero), same-square.
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

The bishop moves only on diagonals: |dx| must equal |dy| and both must be non-zero. Maximum legal delta is 7. Bounds checking is Board's responsibility; `isValidMoveShape` validates shape only.

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
| Test Case 17 | `from=null`, `to=(5,5)`                                                | `IllegalArgumentException("from must not be null")` | :white_check_mark: |
| Test Case 18 | `from=(4,4)`, `to=null`                                                | `IllegalArgumentException("to must not be null")`   | :white_check_mark: |
