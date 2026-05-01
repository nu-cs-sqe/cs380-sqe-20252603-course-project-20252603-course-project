# BVA Analysis for Piece

`Piece` is abstract. The planned tests for this file will use a minimal `TestPiece extends Piece` test double inside `PieceTests` so the constructor, getters, inherited `toString()`, and a controllable `makeCopy()` implementation can be exercised directly.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `type` has valid `PieceType` cases `PAWN`, `ROOK`, `KNIGHT`, `BISHOP`, `QUEEN`, `KING`.
  - `color` has valid `PieceColor` cases `BLACK`, `WHITE`.
  - Because both are Java reference types, `null` is also a settable invalid boundary for each input.
- Step 1, output equivalence classes:
  - `getType()` returns one of the six `PieceType` values or `null`.
  - `getColor()` returns `BLACK`, `WHITE`, or `null`.
  - `makeCopy()` returns a distinct `Piece` whose `type` and `color` match the original piece.
  - `toString()` returns `"COLOR TYPE"` for valid non-null state, and throws `NullPointerException` if `type` or `color` is `null`.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceType` and `PieceColor` are `Cases`.
  - `type` and `color` references also use the `Pointers` boundary: null pointer vs pointer to a true object.
  - `toString()` produces a `String`, but the meaningful boundaries here come from the case and pointer state that feeds the formatter.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceType`: first `PAWN`, second `ROOK`, interior `KNIGHT`, `BISHOP`, `QUEEN`, last `KING`, plus settable pointer boundary `null`.
  - `PieceColor`: first `BLACK`, second/last `WHITE`, plus settable pointer boundary `null`.
  - Impossible non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice across the independent `type` and `color` case variables for valid inputs.
  - Add separate null-pointer cases for `type` and `color`.
  - For getter outputs, choose states so every output class appears at least once.


### Method under test: `Piece(PieceType type, PieceColor color)`

|             | System under test                             | Expected output                                                  | Implemented? |
|-------------|-----------------------------------------------|------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `type = PAWN`, `color = BLACK`   | piece stores `type = PAWN`; piece stores `color = BLACK`         | :x: |
| Test Case 2 | constructor args: `type = ROOK`, `color = WHITE`   | piece stores `type = ROOK`; piece stores `color = WHITE`         | :x: |
| Test Case 3 | constructor args: `type = KNIGHT`, `color = BLACK` | piece stores `type = KNIGHT`; piece stores `color = BLACK`       | :x: |
| Test Case 4 | constructor args: `type = BISHOP`, `color = WHITE` | piece stores `type = BISHOP`; piece stores `color = WHITE`       | :x: |
| Test Case 5 | constructor args: `type = QUEEN`, `color = BLACK`  | piece stores `type = QUEEN`; piece stores `color = BLACK`        | :x: |
| Test Case 6 | constructor args: `type = KING`, `color = WHITE`   | piece stores `type = KING`; piece stores `color = WHITE`         | :x: |
| Test Case 7 | constructor args: `type = null`, `color = BLACK`   | piece constructs successfully; piece stores `type = null`        | :x: |
| Test Case 8 | constructor args: `type = PAWN`, `color = null`    | piece constructs successfully; piece stores `color = null`       | :x: |


### Method under test: `getType()`

|              | System under test                                | Expected output | Implemented? |
|--------------|--------------------------------------------------|-----------------|--------------|
| Test Case 9  | piece: `TestPiece(PAWN, BLACK)`                  | return `PAWN`   | :x: |
| Test Case 10 | piece: `TestPiece(ROOK, WHITE)`                  | return `ROOK`   | :x: |
| Test Case 11 | piece: `TestPiece(KNIGHT, BLACK)`                | return `KNIGHT` | :x: |
| Test Case 12 | piece: `TestPiece(BISHOP, WHITE)`                | return `BISHOP` | :x: |
| Test Case 13 | piece: `TestPiece(QUEEN, BLACK)`                 | return `QUEEN`  | :x: |
| Test Case 14 | piece: `TestPiece(KING, WHITE)`                  | return `KING`   | :x: |
| Test Case 15 | piece: `TestPiece(null, BLACK)`                  | return `null`   | :x: |


### Method under test: `getColor()`

|              | System under test                                | Expected output | Implemented? |
|--------------|--------------------------------------------------|-----------------|--------------|
| Test Case 16 | piece: `TestPiece(PAWN, BLACK)`                  | return `BLACK`  | :x: |
| Test Case 17 | piece: `TestPiece(ROOK, WHITE)`                  | return `WHITE`  | :x: |
| Test Case 18 | piece: `TestPiece(PAWN, null)`                   | return `null`   | :x: |


### Method under test: `makeCopy()`

|              | System under test                                | Expected output                                                                                  | Implemented? |
|--------------|--------------------------------------------------|--------------------------------------------------------------------------------------------------|--------------|
| Test Case 19 | piece: `TestPiece(PAWN, BLACK)`                  | return a distinct `Piece`; copied piece has `type = PAWN`; copied piece has `color = BLACK`     | :x: |
| Test Case 20 | piece: `TestPiece(KING, WHITE)`                  | return a distinct `Piece`; copied piece has `type = KING`; copied piece has `color = WHITE`     | :x: |
| Test Case 21 | piece: `TestPiece(null, BLACK)`                  | return a distinct `Piece`; copied piece has `type = null`; copied piece has `color = BLACK`     | :x: |
| Test Case 22 | piece: `TestPiece(PAWN, null)`                   | return a distinct `Piece`; copied piece has `type = PAWN`; copied piece has `color = null`      | :x: |


### Method under test: `toString()`

|              | System under test                                | Expected output                  | Implemented? |
|--------------|--------------------------------------------------|----------------------------------|--------------|
| Test Case 23 | piece: `TestPiece(PAWN, BLACK)`                  | return `"BLACK PAWN"`            | :x: |
| Test Case 24 | piece: `TestPiece(ROOK, WHITE)`                  | return `"WHITE ROOK"`            | :x: |
| Test Case 25 | piece: `TestPiece(KNIGHT, BLACK)`                | return `"BLACK KNIGHT"`          | :x: |
| Test Case 26 | piece: `TestPiece(BISHOP, WHITE)`                | return `"WHITE BISHOP"`          | :x: |
| Test Case 27 | piece: `TestPiece(QUEEN, BLACK)`                 | return `"BLACK QUEEN"`           | :x: |
| Test Case 28 | piece: `TestPiece(KING, WHITE)`                  | return `"WHITE KING"`            | :x: |
| Test Case 29 | piece: `TestPiece(null, BLACK)`                  | `NullPointerException`           | :x: |
| Test Case 30 | piece: `TestPiece(PAWN, null)`                   | `NullPointerException`           | :x: |
