# BVA Analysis for King

`King` is a concrete `Piece` subclass. The current source exposes the constructor and `makeCopy()`. The provided full-domain design also requires `isValidMoveShape(Location from, Location to)`; this BVA treats that design as the target movement contract. Board coordinates use `Location(x, y)` where `x` is the column, `y` is the row, and valid board coordinates are `0..7`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `color` has valid `PieceColor` cases `WHITE` and `BLACK`.
  - Because `PieceColor` is a Java reference type, `null` is also a settable pointer boundary for the constructor.
  - `makeCopy()` has no parameters, so its input boundary is the valid receiver state: a `King` whose stored color is `WHITE` or `BLACK`.
  - `isValidMoveShape(Location from, Location to)` has two nullable `Location` references.
  - For each `Location`, `x` and `y` have board-coordinate interval boundaries: just below board `-1`, minimum `0`, minimum plus one `1`, maximum minus one `6`, maximum `7`, and just above board `8`.
  - King movement deltas use the interval `[-1, 1]` for `dx` and `dy`, excluding `dx = 0` and `dy = 0` at the same time.
  - King move-shape classes include one-square orthogonal movement, one-square diagonal movement, same-square/no movement, horizontal overreach, vertical overreach, diagonal overreach, and off-board source or destination.
- Step 1, output equivalence classes:
  - `King(PieceColor color)` constructs a `King` with `type = KING` and stored `color` equal to the non-null constructor argument.
  - The constructor rejects `null` color with `IllegalArgumentException` and message `"color must not be null"`.
  - `makeCopy()` returns a distinct `Piece` object that is a `King`, has `type = KING`, and has the same stored color as the original.
  - `isValidMoveShape(from, to)` returns `true` only when the destination is exactly one square away horizontally, vertically, or diagonally and both locations are on the board.
  - `isValidMoveShape(from, to)` returns `false` for same-square movement, any move with `abs(dx) > 1` or `abs(dy) > 1`, and off-board source or destination.
  - `isValidMoveShape(from, to)` rejects `from = null` or `to = null` with `IllegalArgumentException`.
  - Destination occupancy, capture, check, and checkmate are board-level concerns. The king shape method only decides movement shape.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - `color`, `from`, and `to` references use `Pointers`: null pointer vs pointer to a true object.
  - Coordinate values and king deltas use `Intervals`.
  - Method results use `Boolean` cases: legal move shape vs illegal move shape.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: `WHITE`, `BLACK`, plus settable pointer boundary `null`.
  - Board coordinates: `-1`, `0`, `1`, `6`, `7`, and `8`.
  - Legal deltas: `(0, 1)`, `(1, 0)`, `(1, 1)`, `(-1, 0)`, `(0, -1)`, and `(-1, -1)` as representative one-square boundaries.
  - Invalid deltas: `(0, 0)`, `(2, 0)`, `(0, 2)`, and `(2, 2)`.
  - `PieceType.KING` is fixed by `King` and is not a variable input for this class.
  - Impossible non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice because constructor/copy have one independent input/state variable, `color`.
  - Use each-choice for movement-shape boundaries to cover each edge of the legal one-square interval without enumerating every board square.
  - Add explicit boundary rows for same square, overreach, board edge, off-board values, and null pointers.
  - Mark design-target movement tests as `:x:` because `King.isValidMoveShape(...)` is not implemented in the current source yet.


### Method under test: `King(PieceColor color)`

|             | System under test                           | Expected output                                                        | Implemented? |
|-------------|---------------------------------------------|------------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `color = BLACK`           | king constructs successfully; stores `type = KING`; stores `color = BLACK` | :white_check_mark: |
| Test Case 2 | constructor args: `color = WHITE`           | king constructs successfully; stores `type = KING`; stores `color = WHITE` | :white_check_mark: |
| Test Case 3 | constructor args: `color = null`            | throws `IllegalArgumentException` with message `"color must not be null"` | :white_check_mark: |
| Test Case 4 | design-target constructor arg: `color = WHITE` | king starts with `type = KING`, `color = WHITE`, and not-moved state | :x: |
| Test Case 5 | design-target constructor arg: `color = BLACK` | king starts with `type = KING`, `color = BLACK`, and not-moved state | :x: |


### Method under test: `makeCopy()`

|             | System under test                           | Expected output                                                                                       | Implemented? |
|-------------|---------------------------------------------|-------------------------------------------------------------------------------------------------------|--------------|
| Test Case 6 | king: `King(BLACK)`                         | return a distinct `Piece`; copied piece is a `King`; copied piece has `type = KING`; copied piece has `color = BLACK` | :white_check_mark: |
| Test Case 7 | king: `King(WHITE)`                         | return a distinct `Piece`; copied piece is a `King`; copied piece has `type = KING`; copied piece has `color = WHITE` | :white_check_mark: |
| Test Case 8 | king: `King(null)`                          | `King(null)` throws `IllegalArgumentException` with message `"color must not be null"`; null-color `makeCopy()` receiver state is not constructible | :white_check_mark: |


### Method under test: `isValidMoveShape(Location from, Location to)`

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 9  | king; `from = Location(4, 4)`, `to = Location(4, 3)` | return `true`; one-square vertical movement is legal | :white_check_mark: |
| Test Case 10 | king; `from = Location(4, 4)`, `to = Location(4, 5)` | return `true`; one-square vertical movement in the opposite direction is legal | :white_check_mark: |
| Test Case 11 | king; `from = Location(4, 4)`, `to = Location(5, 4)` | return `true`; one-square horizontal movement is legal | :white_check_mark: |
| Test Case 12 | king; `from = Location(4, 4)`, `to = Location(3, 4)` | return `true`; one-square horizontal movement in the opposite direction is legal | :white_check_mark: |
| Test Case 13 | king; `from = Location(4, 4)`, `to = Location(5, 5)` | return `true`; one-square diagonal movement is legal | :white_check_mark: |
| Test Case 14 | king; `from = Location(4, 4)`, `to = Location(3, 3)` | return `true`; one-square diagonal movement in the opposite direction is legal | :white_check_mark: |
| Test Case 15 | king; `from = Location(4, 4)`, `to = Location(4, 4)` | return `false`; same-square movement is not a move | :white_check_mark: |
| Test Case 16 | king; `from = Location(4, 4)`, `to = Location(6, 4)` | return `false`; horizontal overreach with `abs(dx) = 2` is illegal | :white_check_mark: |
| Test Case 17 | king; `from = Location(4, 4)`, `to = Location(4, 6)` | return `false`; vertical overreach with `abs(dy) = 2` is illegal | :white_check_mark: |
| Test Case 18 | king; `from = Location(4, 4)`, `to = Location(6, 6)` | return `false`; diagonal overreach with `abs(dx) = 2` and `abs(dy) = 2` is illegal | :white_check_mark: |
| Test Case 19 | king at minimum corner; `from = Location(0, 0)`, `to = Location(1, 0)` | return `true`; one-square movement from the board edge into the board is legal | :white_check_mark: |
| Test Case 20 | king at minimum corner; `from = Location(0, 0)`, `to = Location(-1, 0)` | return `false`; destination just below minimum `x` is off-board | :white_check_mark: |
| Test Case 21 | king at maximum corner; `from = Location(7, 7)`, `to = Location(8, 7)` | return `false`; destination just above maximum `x` is off-board | :white_check_mark: |
| Test Case 22 | king at maximum corner; `from = Location(7, 7)`, `to = Location(7, 8)` | return `false`; destination just above maximum `y` is off-board | :white_check_mark: |
| Test Case 23 | king; `from = null`, `to = Location(4, 5)` | throws `IllegalArgumentException` with message `"from must not be null"` | :white_check_mark: |
| Test Case 24 | king; `from = Location(4, 4)`, `to = null` | throws `IllegalArgumentException` with message `"to must not be null"` | :x: |
