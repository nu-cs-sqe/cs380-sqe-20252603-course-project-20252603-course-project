# BVA Analysis for Rook

`Rook` is a concrete `Piece` subclass. Its constructor fixes the piece type to `PieceType.ROOK`, so the only constructor input boundary is the `PieceColor color` argument. The source under analysis is `src/main/java/domain/piece/Rook.java`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `PieceColor` is a Java reference type, `null` is an invalid pointer boundary for `color`.
  - `makeCopy()` has no parameters, so its input classes come from the existing valid `Rook` state: `color = BLACK` or `color = WHITE`.
  - A `Rook` with `color = null` is not a valid public state once constructor null handling is corrected.
  - `isValidMoveShape(Location from, Location to)` has two nullable `Location` references.
  - For each `Location`, `x` and `y` have board-coordinate interval boundaries: just below board `-1`, minimum `0`, minimum plus one `1`, maximum minus one `6`, maximum `7`, and just above board `8`.
  - Rook movement classes: straight horizontal (dy = 0, dx ≠ 0), straight vertical (dx = 0, dy ≠ 0), same square (both zero), and all non-straight shapes (diagonal, L-shape).
- Step 1, output equivalence classes:
  - The constructor creates a `Rook` whose `type` is always `ROOK` and whose `color` is the constructor argument for valid colors.
  - The constructor throws `IllegalArgumentException` with message `"color must not be null"` when `color = null`.
  - `makeCopy()` returns a distinct `Rook` instance typed as `Piece`; the copy has `type = ROOK` and the same valid color as the original.
  - `isValidMoveShape(from, to)` returns `true` for legal straight-line moves within the board.
  - `isValidMoveShape(from, to)` returns `false` for diagonal moves, same-square, and off-board destinations.
  - `isValidMoveShape(from, to)` rejects `from = null` or `to = null` with `IllegalArgumentException`.
  - `canJump()` returns `false`; a rook cannot leap over other pieces.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - The `color`, `from`, and `to` references also use the `Pointers` boundary.
  - Coordinate values and movement deltas use `Intervals` over the chess-board range `[0, 7]`.
  - Movement-shape values use `Cases`: horizontal, vertical, same-square, diagonal, off-board.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: first `BLACK`, second/last `WHITE`, plus invalid pointer boundary `null`.
  - Board coordinates: `-1`, `0`, `1`, `6`, `7`, and `8`.
  - Legal deltas: `(0, ≠0)` vertical, `(≠0, 0)` horizontal — spanning from one square to the full board.
  - Invalid deltas: `(0, 0)` same square, `(≠0, ≠0)` diagonal, L-shape.
  - Impossible non-enum `PieceColor` values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice across the `PieceColor` case values and the invalid null pointer boundary.
  - Use each-choice for movement-shape boundaries: include one-square and full-span horizontal, one-square and full-span vertical, same-square, diagonal, L-shape, and each off-board edge.
  - Add explicit boundary rows for board edges and null pointers.


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

|               | System under test | Expected output | Implemented? |
|---------------|-------------------|-----------------|--------------|
| Test Case 7   | rook; `from = Location(0, 4)`, `to = Location(7, 4)` | return `true`; full-span horizontal right is a straight-line move | :white_check_mark: |
| Test Case 8   | rook; `from = Location(7, 4)`, `to = Location(0, 4)` | return `true`; full-span horizontal left is a straight-line move | :white_check_mark: |
| Test Case 9   | rook; `from = Location(4, 0)`, `to = Location(4, 7)` | return `true`; full-span vertical up is a straight-line move | :white_check_mark: |
| Test Case 10  | rook; `from = Location(4, 7)`, `to = Location(4, 0)` | return `true`; full-span vertical down is a straight-line move | :white_check_mark: |
| Test Case 11  | rook; `from = Location(4, 4)`, `to = Location(5, 4)` | return `true`; one-square horizontal move | :white_check_mark: |
| Test Case 12  | rook; `from = Location(4, 4)`, `to = Location(4, 5)` | return `true`; one-square vertical move | :white_check_mark: |
| Test Case 13  | rook; `from = Location(0, 0)`, `to = Location(7, 0)` | return `true`; horizontal from minimum corner | :white_check_mark: |
| Test Case 14  | rook; `from = Location(0, 0)`, `to = Location(0, 7)` | return `true`; vertical from minimum corner | :white_check_mark: |
| Test Case 15  | rook; `from = Location(0, 0)`, `to = Location(3, 3)` | return `false`; diagonal move is not a straight line | :white_check_mark: |
| Test Case 16  | rook; `from = Location(4, 4)`, `to = Location(4, 4)` | return `false`; same-square movement is not allowed | :white_check_mark: |
| Test Case 17  | rook; `from = Location(4, 4)`, `to = Location(5, 6)` | return `false`; L-shape move is not a straight line | :white_check_mark: |
| Test Case 18  | rook; `from = Location(7, 4)`, `to = Location(8, 4)` | return `false`; destination just above maximum `x` is off-board | :white_check_mark: |
| Test Case 19  | rook; `from = Location(4, 7)`, `to = Location(4, 8)` | return `false`; destination just above maximum `y` is off-board | :white_check_mark: |
| Test Case 20  | rook; `from = Location(0, 4)`, `to = Location(-1, 4)` | return `false`; destination just below minimum `x` is off-board | :white_check_mark: |
| Test Case 21  | rook; `from = Location(4, 0)`, `to = Location(4, -1)` | return `false`; destination just below minimum `y` is off-board | :white_check_mark: |
| Test Case 22  | rook; `from = null`, `to = Location(4, 4)` | throws `IllegalArgumentException` with message `"from must not be null"` | :white_check_mark: |
| Test Case 23  | rook; `from = Location(4, 4)`, `to = null` | throws `IllegalArgumentException` with message `"to must not be null"` | :white_check_mark: |


### Method under test: `canJump()`

A rook cannot leap over other pieces; it is blocked by any piece in its path.

|               | System under test       | Expected output   | Implemented? |
|---------------|-------------------------|-------------------|--------------|
| Test Case 24  | rook: `Rook(WHITE)`     | return `false`    | :white_check_mark: |
