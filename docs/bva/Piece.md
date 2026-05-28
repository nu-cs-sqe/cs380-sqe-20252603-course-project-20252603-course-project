# BVA Analysis for Piece

`Piece` is abstract. The planned tests for this file will use a minimal `TestPiece extends Piece` test double inside `PieceTests` so the constructor, getters, inherited `toString()`, and a controllable `makeCopy()` implementation can be exercised directly.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `type` has valid `PieceType` cases `PAWN`, `ROOK`, `KNIGHT`, `BISHOP`, `QUEEN`, `KING`.
  - `color` has valid `PieceColor` cases `BLACK`, `WHITE`.
  - Because both are Java reference types, `null` is an invalid pointer boundary for each constructor input, not a valid public `Piece` state.
- Step 1, output equivalence classes:
  - The constructor stores non-null `type` and `color` values.
  - The constructor rejects `type = null` with `IllegalArgumentException` and message `"type must not be null"`.
  - The constructor rejects `color = null` with `IllegalArgumentException` and message `"color must not be null"`.
  - `getType()` returns one of the six non-null `PieceType` values.
  - `getColor()` returns non-null `BLACK` or `WHITE`.
  - `makeCopy()` returns a distinct `Piece` whose non-null `type` and `color` match the original piece.
  - `toString()` returns `"COLOR TYPE"` for valid non-null state.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceType` and `PieceColor` are `Cases`.
  - `type` and `color` references also use the `Pointers` boundary: invalid null pointer vs pointer to a true enum object.
  - `toString()` produces a `String`, but the meaningful boundaries here come from the case and pointer state that feeds the formatter.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceType`: first `PAWN`, second `ROOK`, interior `KNIGHT`, `BISHOP`, `QUEEN`, last `KING`, plus invalid pointer boundary `null`.
  - `PieceColor`: first `BLACK`, second/last `WHITE`, plus invalid pointer boundary `null`.
  - Impossible non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice across the independent `type` and `color` case variables for valid inputs.
  - Add separate constructor null-rejection cases for `type` and `color`.
  - For getter outputs, choose valid states so every non-null output class appears at least once.
  - Null-derived getter, copy, and `toString()` rows are not separate public states; mark them `CAN'T SET` through the public constructor and cover them by constructor null rejection.


## BoardView Consumption and Coordinate Convention Review

As part of Issue #31 responsibilities, a review of `BoardView` was conducted to understand:
1. How it consumes `PieceType` and `PieceColor` from the board snapshot. The review found that `BoardView` directly utilizes these values from the board snapshot to map them to their corresponding visual representations (images) for rendering.
2. The coordinate convention used in the UI. The review confirmed that `BoardView` uses a `(col, row)` convention where `col` maps to `x` and `row` maps to `y`. This is consistent with the `Location(x, y)` domain object. The `boardSnapshot` is accessed as `boardSnapshot[y][x]` (or `boardSnapshot[row][col]`), which matches the internal representation.

### Method under test: `Piece(PieceType type, PieceColor color)`

|             | System under test                             | Expected output                                                  | Implemented? |
|-------------|-----------------------------------------------|------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `type = PAWN`, `color = BLACK`   | piece stores `type = PAWN`; piece stores `color = BLACK`         | :white_check_mark: |
| Test Case 2 | constructor args: `type = ROOK`, `color = WHITE`   | piece stores `type = ROOK`; piece stores `color = WHITE`         | :white_check_mark: |
| Test Case 3 | constructor args: `type = KNIGHT`, `color = BLACK` | piece stores `type = KNIGHT`; piece stores `color = BLACK`       | :white_check_mark: |
| Test Case 4 | constructor args: `type = BISHOP`, `color = WHITE` | piece stores `type = BISHOP`; piece stores `color = WHITE`       | :white_check_mark: |
| Test Case 5 | constructor args: `type = QUEEN`, `color = BLACK`  | piece stores `type = QUEEN`; piece stores `color = BLACK`        | :white_check_mark: |
| Test Case 6 | constructor args: `type = KING`, `color = WHITE`   | piece stores `type = KING`; piece stores `color = WHITE`         | :white_check_mark: |
| Test Case 7 | constructor args: `type = null`, `color = BLACK`   | throws `IllegalArgumentException` with message `"type must not be null"` | :white_check_mark: |
| Test Case 8 | constructor args: `type = PAWN`, `color = null`    | throws `IllegalArgumentException` with message `"color must not be null"` | :white_check_mark: |


### Method under test: `getType()`

|              | System under test                                | Expected output | Implemented? |
|--------------|--------------------------------------------------|-----------------|--------------|
| Test Case 9  | piece: `TestPiece(PAWN, BLACK)`                  | return `PAWN`   | :white_check_mark: |
| Test Case 10 | piece: `TestPiece(ROOK, WHITE)`                  | return `ROOK`   | :white_check_mark: |
| Test Case 11 | piece: `TestPiece(KNIGHT, BLACK)`                | return `KNIGHT` | :white_check_mark: |
| Test Case 12 | piece: `TestPiece(BISHOP, WHITE)`                | return `BISHOP` | :white_check_mark: |
| Test Case 13 | piece: `TestPiece(QUEEN, BLACK)`                 | return `QUEEN`  | :white_check_mark: |
| Test Case 14 | piece: `TestPiece(KING, WHITE)`                  | return `KING`   | :white_check_mark: |
| Test Case 15 | attempted piece: `TestPiece(null, BLACK)`        | `CAN'T SET` through the public constructor; null-type getter state is covered by Test Case 7's `IllegalArgumentException` with message `"type must not be null"` | :white_check_mark: |


### Method under test: `getColor()`

|              | System under test                                | Expected output | Implemented? |
|--------------|--------------------------------------------------|-----------------|--------------|
| Test Case 16 | piece: `TestPiece(PAWN, BLACK)`                  | return `BLACK`  | :white_check_mark: |
| Test Case 17 | piece: `TestPiece(ROOK, WHITE)`                  | return `WHITE`  | :white_check_mark: |
| Test Case 18 | attempted piece: `TestPiece(PAWN, null)`         | `CAN'T SET` through the public constructor; null-color getter state is covered by Test Case 8's `IllegalArgumentException` with message `"color must not be null"` | :white_check_mark: |


### Method under test: `makeCopy()`

|              | System under test                                | Expected output                                                                                  | Implemented? |
|--------------|--------------------------------------------------|--------------------------------------------------------------------------------------------------|--------------|
| Test Case 19 | piece: `TestPiece(PAWN, BLACK)`                  | return a distinct `Piece`; copied piece has `type = PAWN`; copied piece has `color = BLACK`     | :white_check_mark: |
| Test Case 20 | piece: `TestPiece(KING, WHITE)`                  | return a distinct `Piece`; copied piece has `type = KING`; copied piece has `color = WHITE`     | :white_check_mark: |
| Test Case 21 | attempted piece: `TestPiece(null, BLACK)`        | `CAN'T SET` through the public constructor; null-type copy receiver state is covered by Test Case 7's `IllegalArgumentException` with message `"type must not be null"` | :white_check_mark: |
| Test Case 22 | attempted piece: `TestPiece(PAWN, null)`         | `CAN'T SET` through the public constructor; null-color copy receiver state is covered by Test Case 8's `IllegalArgumentException` with message `"color must not be null"` | :white_check_mark: |


### Method under test: `toString()`

|              | System under test                                | Expected output                  | Implemented? |
|--------------|--------------------------------------------------|----------------------------------|--------------|
| Test Case 23 | piece: `TestPiece(PAWN, BLACK)`                  | return `"BLACK PAWN"`            | :white_check_mark: |
| Test Case 24 | piece: `TestPiece(ROOK, WHITE)`                  | return `"WHITE ROOK"`            | :white_check_mark: |
| Test Case 25 | piece: `TestPiece(KNIGHT, BLACK)`                | return `"BLACK KNIGHT"`          | :white_check_mark: |
| Test Case 26 | piece: `TestPiece(BISHOP, WHITE)`                | return `"WHITE BISHOP"`          | :white_check_mark: |
| Test Case 27 | piece: `TestPiece(QUEEN, BLACK)`                 | return `"BLACK QUEEN"`           | :white_check_mark: |
| Test Case 28 | piece: `TestPiece(KING, WHITE)`                  | return `"WHITE KING"`            | :white_check_mark: |
| Test Case 29 | attempted piece: `TestPiece(null, BLACK)`        | `CAN'T SET` through the public constructor; this null-type `toString()` receiver state is covered by Test Case 7's constructor null rejection (`IllegalArgumentException` with message `"type must not be null"`) | :white_check_mark: |
| Test Case 30 | attempted piece: `TestPiece(PAWN, null)`         | `CAN'T SET` through the public constructor; this null-color `toString()` receiver state is covered by Test Case 8's constructor null rejection (`IllegalArgumentException` with message `"color must not be null"`) | :white_check_mark: |


### Method under test: `canJump()`

The default implementation in `Piece` returns `false`. Only `Knight` overrides it to return `true`. All other concrete subclasses inherit the `false` default.

|              | System under test                                | Expected output              | Implemented? |
|--------------|--------------------------------------------------|------------------------------|--------------|
| Test Case 31 | piece: `TestPiece(PAWN, BLACK)`                  | return `false`               | :white_check_mark: |


### Method under test: `isSameColor(Piece other)`

|              | System under test                                                      | Expected output                                                            | Implemented? |
|--------------|------------------------------------------------------------------------|----------------------------------------------------------------------------|--------------|
| Test Case 32 | piece: `TestPiece(PAWN, BLACK)`; other: `TestPiece(ROOK, BLACK)`       | return `true`; both pieces share color `BLACK`                             | :white_check_mark: |
| Test Case 33 | piece: `TestPiece(KING, WHITE)`; other: `TestPiece(QUEEN, WHITE)`      | return `true`; both pieces share color `WHITE`                             | :white_check_mark: |
| Test Case 34 | piece: `TestPiece(PAWN, BLACK)`; other: `TestPiece(PAWN, WHITE)`       | return `false`; colors differ (`BLACK` vs `WHITE`)                         | :white_check_mark: |
| Test Case 35 | piece: `TestPiece(PAWN, WHITE)`; other: `TestPiece(PAWN, BLACK)`       | return `false`; colors differ (`WHITE` vs `BLACK`)                         | :white_check_mark: |
| Test Case 36 | piece: `TestPiece(PAWN, BLACK)`; other: `null`                         | throws `IllegalArgumentException` with message `"other must not be null"`  | :white_check_mark: |
