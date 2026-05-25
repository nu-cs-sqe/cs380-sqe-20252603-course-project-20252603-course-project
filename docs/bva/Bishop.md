# BVA Analysis for Bishop

`Bishop` is concrete. This file covers the public API declared in `Bishop.java`: the constructor and `makeCopy()`. Inherited methods are covered in `docs/bva/Piece.md`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - The constructor input `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `color` is a Java reference type, `null` is also a settable boundary value.
  - `makeCopy()` has no parameters, but its behavior depends on the valid receiver state: a `Bishop` with `color = BLACK` or `color = WHITE`.
  - `isValidMoveShape(Location from, Location to)` has two nullable `Location` references.
  - For each `Location`, `x` and `y` have board-coordinate interval boundaries: just below board `-1`, minimum `0`, minimum plus one `1`, maximum minus one `6`, maximum `7`, and just above board `8`.
  - Bishop movement classes: diagonal (abs(dx) == abs(dy), dx ≠ 0), straight horizontal, straight vertical, same square, and off-board destination.
- Step 1, output equivalence classes:
  - The constructor creates a `Bishop` whose type is fixed to `PieceType.BISHOP` and whose color is the supplied non-null `color`.
  - The constructor rejects `null` color with `IllegalArgumentException` and message `"color must not be null"`.
  - `makeCopy()` returns a distinct `Bishop` object whose type is `PieceType.BISHOP` and whose color matches the original bishop.
  - `isValidMoveShape(from, to)` returns `true` for legal diagonal moves within the board.
  - `isValidMoveShape(from, to)` returns `false` for non-diagonal moves, same-square, and off-board destinations.
  - `isValidMoveShape(from, to)` rejects `from = null` or `to = null` with `IllegalArgumentException`.
  - `canJump()` returns `false`; a bishop cannot leap over other pieces.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - The `color`, `from`, and `to` references also use the `Pointers` boundary.
  - Coordinate values use `Intervals` over the chess-board range `[0, 7]`.
  - Movement-shape values use `Cases`: diagonal up-right, up-left, down-right, down-left, same-square, straight horizontal, straight vertical, off-board.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: first `BLACK`, second/last `WHITE`, plus settable pointer boundary `null`.
  - `PieceType` is not caller-controlled in `Bishop`; it is always `BISHOP`.
  - Board coordinates: `-1`, `0`, `1`, `6`, `7`, and `8`.
  - Legal diagonal deltas: `(3, 3)`, `(-3, 3)`, `(3, -3)`, `(-3, -3)`, and one-square `(1, 1)`, and full-board `(7, 7)`.
  - Invalid deltas: `(3, 0)` horizontal, `(0, 3)` vertical, `(0, 0)` same-square, `(1, 2)` L-shape.
  - Impossible non-enum color values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice over the single varying input/state variable, `color`.
  - Cover every `PieceColor` case and the null-pointer boundary once for the constructor.
  - Cover every valid receiver color state once for `makeCopy()`.
  - Use each-choice for movement-shape boundaries: include one representative from each diagonal direction, one-square and full-span diagonal, same-square, each straight direction, L-shape, and each off-board edge.
  - Add explicit boundary rows for board edges and null pointers.


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

|               | System under test | Expected output | Implemented? |
|---------------|-------------------|-----------------|--------------|
| Test Case 7   | bishop; `from = Location(0, 0)`, `to = Location(3, 3)` | return `true`; diagonal up-right move | :white_check_mark: |
| Test Case 8   | bishop; `from = Location(3, 0)`, `to = Location(0, 3)` | return `true`; diagonal up-left move | :white_check_mark: |
| Test Case 9   | bishop; `from = Location(0, 3)`, `to = Location(3, 0)` | return `true`; diagonal down-right move | :white_check_mark: |
| Test Case 10  | bishop; `from = Location(3, 3)`, `to = Location(0, 0)` | return `true`; diagonal down-left move | :white_check_mark: |
| Test Case 11  | bishop; `from = Location(4, 4)`, `to = Location(5, 5)` | return `true`; one-square diagonal move | :white_check_mark: |
| Test Case 12  | bishop; `from = Location(0, 0)`, `to = Location(7, 7)` | return `true`; full-board diagonal move | :white_check_mark: |
| Test Case 13  | bishop; `from = Location(0, 0)`, `to = Location(3, 0)` | return `false`; straight horizontal move is not diagonal | :white_check_mark: |
| Test Case 14  | bishop; `from = Location(0, 0)`, `to = Location(0, 3)` | return `false`; straight vertical move is not diagonal | :white_check_mark: |
| Test Case 15  | bishop; `from = Location(4, 4)`, `to = Location(4, 4)` | return `false`; same-square movement is not allowed | :white_check_mark: |
| Test Case 16  | bishop; `from = Location(4, 4)`, `to = Location(5, 6)` | return `false`; L-shape move is not diagonal | :white_check_mark: |
| Test Case 17  | bishop; `from = Location(7, 0)`, `to = Location(8, 1)` | return `false`; destination just above maximum `x` is off-board | :white_check_mark: |
| Test Case 18  | bishop; `from = Location(0, 7)`, `to = Location(1, 8)` | return `false`; destination just above maximum `y` is off-board | :white_check_mark: |
| Test Case 19  | bishop; `from = Location(0, 3)`, `to = Location(-1, 2)` | return `false`; destination just below minimum `x` is off-board | :white_check_mark: |
| Test Case 20  | bishop; `from = Location(3, 0)`, `to = Location(2, -1)` | return `false`; destination just below minimum `y` is off-board | :white_check_mark: |
| Test Case 21  | bishop; `from = null`, `to = Location(4, 4)` | throws `IllegalArgumentException` with message `"from must not be null"` | :white_check_mark: |
| Test Case 22  | bishop; `from = Location(4, 4)`, `to = null` | throws `IllegalArgumentException` with message `"to must not be null"` | :white_check_mark: |


### Method under test: `canJump()`

A bishop cannot leap over other pieces; it is blocked by any piece in its diagonal path.

|               | System under test         | Expected output   | Implemented? |
|---------------|---------------------------|-------------------|--------------|
| Test Case 23  | bishop: `Bishop(WHITE)`   | return `false`    | :white_check_mark: |
