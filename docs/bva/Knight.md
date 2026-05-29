# BVA Analysis for Knight

`Knight` fixes the inherited `PieceType` to `KNIGHT`. The current source exposes the constructor and `makeCopy()`. The provided full-domain design also requires `isValidMoveShape(Location from, Location to)`; this BVA treats that design as the target movement contract. Board coordinates use `Location(x, y)` where `x` is the column, `y` is the row, and valid board coordinates are `0..7`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `color` has valid `PieceColor` cases `WHITE` and `BLACK`.
  - Because `PieceColor` is a Java reference type, `null` is also a settable pointer boundary for `color`.
  - `makeCopy()` has no parameters; its input classes are the valid existing `Knight` receiver states with `color = WHITE` or `color = BLACK`.
  - `isValidMoveShape(Location from, Location to)` has two nullable `Location` references.
  - For each `Location`, `x` and `y` have board-coordinate interval boundaries: just below board `-1`, minimum `0`, minimum plus one `1`, maximum minus one `6`, maximum `7`, and just above board `8`.
  - Knight movement deltas are legal only for the two L-shape cases: `abs(dx) = 1, abs(dy) = 2` or `abs(dx) = 2, abs(dy) = 1`.
  - Knight invalid move-shape classes include same-square/no movement, straight movement, diagonal movement, square-like `2x2`, overreach, and off-board source or destination.
- Step 1, output equivalence classes:
  - The constructor creates a `Knight` with `type = KNIGHT` and the supplied non-null `color`.
  - The constructor rejects `null` color with `IllegalArgumentException` and message `"color must not be null"`.
  - `makeCopy()` returns a distinct `Piece` object whose runtime state is a `Knight` with `type = KNIGHT` and the same `color` as the original.
  - `isValidMoveShape(from, to)` returns `true` for legal L-shape moves within the board.
  - `isValidMoveShape(from, to)` returns `false` for all non-L-shape moves and off-board source or destination.
  - `isValidMoveShape(from, to)` rejects `from = null` or `to = null` with `IllegalArgumentException`.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - `color`, `from`, and `to` references use `Pointers`: null pointer vs pointer to a true object.
  - Coordinate values and knight deltas use `Intervals`.
  - The L-shape categories use `Cases`: `(1, 2)`, `(2, 1)`, and representative invalid alternatives.
  - `isValidMoveShape(...)` outputs use `Boolean` cases.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: `WHITE`, `BLACK`, plus settable pointer boundary `null`.
  - Board coordinates: `-1`, `0`, `1`, `6`, `7`, and `8`.
  - Legal deltas: `(1, 2)`, `(-1, 2)`, `(1, -2)`, `(-1, -2)`, `(2, 1)`, `(-2, 1)`, `(2, -1)`, and `(-2, -1)`.
  - Invalid deltas: `(0, 0)`, `(0, 2)`, `(1, 1)`, `(2, 2)`, and `(3, 1)`.
  - `PieceType.KNIGHT` is fixed by `Knight` and is not a variable input for this class.
  - Impossible non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice because constructor/copy have one independent input/state variable, `color`.
  - Use each-choice for movement shape so every L-shape orientation group and every important invalid boundary is represented without enumerating every board square.
  - Add explicit boundary rows for board edges, off-board values, and null pointers.
  - Mark design-target movement tests as `:x:` because `Knight.isValidMoveShape(...)` is not implemented in the current source yet.


### Method under test: `Knight(PieceColor color)`

|             | System under test                     | Expected output                                                     | Implemented? |
|-------------|---------------------------------------|---------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `color = BLACK`     | knight stores `type = KNIGHT`; knight stores `color = BLACK`        | :white_check_mark: |
| Test Case 2 | constructor args: `color = WHITE`     | knight stores `type = KNIGHT`; knight stores `color = WHITE`        | :white_check_mark: |
| Test Case 3 | constructor args: `color = null`      | `Knight(null)` throws `IllegalArgumentException` with message `"color must not be null"` | :white_check_mark: |


### Method under test: `makeCopy()`

|             | System under test                 | Expected output                                                                                         | Implemented? |
|-------------|-----------------------------------|---------------------------------------------------------------------------------------------------------|--------------|
| Test Case 4 | knight: `Knight(BLACK)`           | return a distinct `Piece`; copied piece has `type = KNIGHT`; copied piece has `color = BLACK`           | :white_check_mark: |
| Test Case 5 | knight: `Knight(WHITE)`           | return a distinct `Piece`; copied piece has `type = KNIGHT`; copied piece has `color = WHITE`           | :white_check_mark: |
| Test Case 6 | knight: `Knight(null)`            | `Knight(null)` throws `IllegalArgumentException` with message `"color must not be null"`; null-color `makeCopy()` receiver state is not constructible | :white_check_mark: |


### Method under test: `isValidMoveShape(Location from, Location to)`

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 7  | knight; `from = Location(4, 4)`, `to = Location(5, 6)` | return `true`; `abs(dx) = 1` and `abs(dy) = 2` is legal | :white_check_mark: |
| Test Case 8  | knight; `from = Location(4, 4)`, `to = Location(3, 6)` | return `true`; `abs(dx) = 1` and `abs(dy) = 2` is legal in the opposite horizontal direction | :white_check_mark: |
| Test Case 9  | knight; `from = Location(4, 4)`, `to = Location(5, 2)` | return `true`; `abs(dx) = 1` and `abs(dy) = 2` is legal in the opposite vertical direction | :white_check_mark: |
| Test Case 10 | knight; `from = Location(4, 4)`, `to = Location(6, 5)` | return `true`; `abs(dx) = 2` and `abs(dy) = 1` is legal | :white_check_mark: |
| Test Case 11 | knight; `from = Location(4, 4)`, `to = Location(2, 5)` | return `true`; `abs(dx) = 2` and `abs(dy) = 1` is legal in the opposite horizontal direction | :white_check_mark: |
| Test Case 12 | knight; `from = Location(4, 4)`, `to = Location(6, 3)` | return `true`; `abs(dx) = 2` and `abs(dy) = 1` is legal in the opposite vertical direction | :white_check_mark: |
| Test Case 13 | knight; `from = Location(4, 4)`, `to = Location(4, 4)` | return `false`; same-square movement is not a knight move | :white_check_mark: |
| Test Case 14 | knight; `from = Location(4, 4)`, `to = Location(4, 6)` | return `false`; straight vertical movement with `dx = 0` is illegal | :white_check_mark: |
| Test Case 15 | knight; `from = Location(4, 4)`, `to = Location(5, 5)` | return `false`; one-square diagonal movement is illegal | :white_check_mark: |
| Test Case 16 | knight; `from = Location(4, 4)`, `to = Location(6, 6)` | return `false`; `2x2` movement is not an L-shape | :white_check_mark: |
| Test Case 17 | knight; `from = Location(4, 4)`, `to = Location(7, 5)` | return `false`; `abs(dx) = 3` overreaches the L-shape boundary | :white_check_mark: |
| Test Case 18 | knight at minimum corner; `from = Location(0, 0)`, `to = Location(1, 2)` | return `true`; legal L-shape movement from a board edge into the board is allowed | :white_check_mark: |
| Test Case 19 | knight at minimum corner; `from = Location(0, 0)`, `to = Location(-1, 2)` | return `false`; destination just below minimum `x` is off-board | :white_check_mark: |
| Test Case 20 | knight at minimum corner; `from = Location(0, 0)`, `to = Location(2, -1)` | return `false`; destination just below minimum `y` is off-board | :white_check_mark: |
| Test Case 21 | knight at maximum corner; `from = Location(7, 7)`, `to = Location(8, 5)` | return `false`; destination just above maximum `x` is off-board | :white_check_mark: |
| Test Case 22 | knight at maximum corner; `from = Location(7, 7)`, `to = Location(5, 8)` | return `false`; destination just above maximum `y` is off-board | :white_check_mark: |
| Test Case 23 | knight; `from = null`, `to = Location(5, 6)` | throws `IllegalArgumentException` with message `"from must not be null"` | :white_check_mark: |
| Test Case 24 | knight; `from = Location(4, 4)`, `to = null` | throws `IllegalArgumentException` with message `"to must not be null"` | :white_check_mark: |


### Method under test: `canJump()`

Knight is the only piece that can leap over other pieces on the board.

|              | System under test                   | Expected output   | Implemented? |
|--------------|-------------------------------------|-------------------|--------------|
| Test Case 25 | knight: `Knight(WHITE)`             | return `true`     | :white_check_mark: |
