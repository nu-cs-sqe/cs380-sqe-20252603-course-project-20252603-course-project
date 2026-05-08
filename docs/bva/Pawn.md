# BVA Analysis for Pawn

`Pawn` is concrete. The current source exposes the constructor and `makeCopy()`. The provided full-domain design also requires `isValidMoveShape(Location from, Location to)` and inherited movement state such as `hasMoved`; this BVA treats that design as the target movement contract. Board coordinates use `Location(x, y)` where `x` is the column, `y` is the row, and valid board coordinates are `0..7`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `color` has valid `PieceColor` cases `WHITE` and `BLACK`.
  - Because `PieceColor` is a Java reference type, `null` is also a settable invalid boundary for `color`.
  - `makeCopy()` has no parameters, but its relevant input state is the pawn's valid stored `color`, movement state, and fixed `type = PAWN`.
  - `isValidMoveShape(Location from, Location to)` has two nullable `Location` references.
  - For each `Location`, `x` and `y` have board-coordinate interval boundaries: just below board `-1`, minimum `0`, minimum plus one `1`, maximum minus one `6`, maximum `7`, and just above board `8`.
  - Pawn movement deltas depend on `color`: white moves toward smaller `y` values; black moves toward larger `y` values.
  - Pawn move-shape classes include one-square forward, two-square initial forward, one-square diagonal capture candidate, same-square/no movement, sideways movement, backward movement, overreach, and off-board source or destination.
  - The two-square forward class also depends on movement state: not moved vs already moved.
- Step 1, output equivalence classes:
  - `Pawn(PieceColor color)` returns a constructed `Pawn` with inherited `type = PAWN` and inherited `color` equal to the non-null constructor argument.
  - The constructor rejects `null` color with `IllegalArgumentException` and message `"color must not be null"`.
  - `makeCopy()` returns a distinct `Pawn` whose inherited `type = PAWN`, inherited `color`, and relevant movement state match the original pawn.
  - `isValidMoveShape(from, to)` returns `true` for legal pawn shapes only.
  - `isValidMoveShape(from, to)` returns `false` for wrong direction, wrong distance, sideways movement, same-square movement, or off-board source/destination.
  - `isValidMoveShape(from, to)` rejects `from = null` or `to = null` with `IllegalArgumentException`.
  - Pawn capture occupancy is not fully decided by `isValidMoveShape`; a diagonal forward shape is only a capture candidate. The board-level move flow must require an opponent piece at the destination before allowing a complete capture.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - `color`, `from`, and `to` references use `Pointers`: null pointer vs pointer to a true object.
  - Coordinate values and movement deltas use `Intervals` over the chess-board range `[0, 7]`.
  - Movement-state values use `Boolean`/`Cases`: not moved vs moved.
  - Method results use `Boolean` cases: legal move shape vs illegal move shape.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: `WHITE`, `BLACK`, plus settable pointer boundary `null`.
  - Board coordinates: `-1`, `0`, `1`, `6`, `7`, and `8`.
  - White forward deltas: `dy = -1` for one-square forward, `dy = -2` for initial two-square forward, `dy = +1` for backward invalid.
  - Black forward deltas: `dy = +1` for one-square forward, `dy = +2` for initial two-square forward, `dy = -1` for backward invalid.
  - Diagonal capture-candidate deltas: `abs(dx) = 1` and `dy` in the color's forward direction.
  - Invalid shape deltas: `dx = 0, dy = 0`; `abs(dx) = 1, dy = 0`; `abs(dy) = 3`; diagonal backward.
  - Impossible non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice for constructor and copy color boundaries.
  - Use each-choice for movement-shape boundaries because all-combinations across color, from, to, board edge, and movement state would be redundant and much larger than needed.
  - Add explicit boundary rows for the pawn-specific interaction points: color direction, first-move two-square movement, diagonal capture candidates, edge-of-board rejection, and null pointers.
  - Mark design-target movement tests as `:x:` because `Pawn.isValidMoveShape(...)` is not implemented in the current source yet.


### Method under test: `Pawn(PieceColor color)`

|             | System under test                    | Expected output                                                     | Implemented? |
|-------------|--------------------------------------|---------------------------------------------------------------------|--------------|
| Test Case 1 | constructor arg: `color = BLACK`     | pawn constructs successfully; pawn has `type = PAWN`; pawn has `color = BLACK` | :white_check_mark: |
| Test Case 2 | constructor arg: `color = WHITE`     | pawn constructs successfully; pawn has `type = PAWN`; pawn has `color = WHITE` | :white_check_mark: |
| Test Case 3 | constructor arg: `color = null`      | constructor throws `IllegalArgumentException` with message `"color must not be null"` | :white_check_mark: |
| Test Case 4 | design-target constructor arg: `color = WHITE` | pawn starts with `type = PAWN`, `color = WHITE`, and not-moved state for initial two-square movement | implemented in Test Case 12 |
| Test Case 5 | design-target constructor arg: `color = BLACK` | pawn starts with `type = PAWN`, `color = BLACK`, and not-moved state for initial two-square movement | implemented in Test Case 13 |


### Method under test: `makeCopy()`

|             | System under test             | Expected output                                                                                  | Implemented? |
|-------------|-------------------------------|--------------------------------------------------------------------------------------------------|--------------|
| Test Case 6 | pawn: `Pawn(BLACK)`           | return a distinct `Pawn`; copied pawn has `type = PAWN`; copied pawn has `color = BLACK`        | :white_check_mark: |
| Test Case 7 | pawn: `Pawn(WHITE)`           | return a distinct `Pawn`; copied pawn has `type = PAWN`; copied pawn has `color = WHITE`        | :white_check_mark: |
| Test Case 8 | pawn: `Pawn(null)`            | `Pawn(null)` throws `IllegalArgumentException` with message `"color must not be null"`; null-color `makeCopy()` receiver state is not constructible | :white_check_mark: |
| Test Case 9 | design-target pawn after `changeToMoved()` | return a distinct `Pawn` with the same color, `type = PAWN`, and moved state preserved | :x: |


### Method under test: `isValidMoveShape(Location from, Location to)`

|              | System under test | Expected output | Implemented? |
|--------------|-------------------|-----------------|--------------|
| Test Case 10 | white pawn not moved; `from = Location(4, 6)`, `to = Location(4, 5)` | return `true`; minimum normal forward distance for white is one row toward smaller `y` | :white_check_mark: |
| Test Case 11 | black pawn not moved; `from = Location(4, 1)`, `to = Location(4, 2)` | return `true`; minimum normal forward distance for black is one row toward larger `y` | :white_check_mark: |
| Test Case 12 | white pawn not moved; `from = Location(4, 6)`, `to = Location(4, 4)` | return `true`; two-square initial movement is legal from the white starting row when the pawn has not moved | :white_check_mark: |
| Test Case 13 | black pawn not moved; `from = Location(4, 1)`, `to = Location(4, 3)` | return `true`; two-square initial movement is legal from the black starting row when the pawn has not moved | :white_check_mark: |
| Test Case 14 | white pawn after `changeToMoved()`; `from = Location(4, 5)`, `to = Location(4, 3)` | return `false`; two-square movement is not legal after the pawn has moved | :x: |
| Test Case 15 | black pawn after `changeToMoved()`; `from = Location(4, 2)`, `to = Location(4, 4)` | return `false`; two-square movement is not legal after the pawn has moved | :x: |
| Test Case 16 | white pawn not moved but not on starting row; `from = Location(4, 5)`, `to = Location(4, 3)` | return `false`; two-square movement is legal only from the white starting row `y = 6` | :white_check_mark: |
| Test Case 17 | black pawn not moved but not on starting row; `from = Location(4, 2)`, `to = Location(4, 4)` | return `false`; two-square movement is legal only from the black starting row `y = 1` | :white_check_mark: |
| Test Case 18 | white pawn; `from = Location(4, 6)`, `to = Location(3, 5)` | return `true`; left diagonal forward one square is a legal capture candidate for white | :white_check_mark: |
| Test Case 19 | white pawn; `from = Location(4, 6)`, `to = Location(5, 5)` | return `true`; right diagonal forward one square is a legal capture candidate for white | :white_check_mark: |
| Test Case 20 | black pawn; `from = Location(4, 1)`, `to = Location(3, 2)` | return `true`; left diagonal forward one square is a legal capture candidate for black | :white_check_mark: |
| Test Case 21 | black pawn; `from = Location(4, 1)`, `to = Location(5, 2)` | return `true`; right diagonal forward one square is a legal capture candidate for black | :white_check_mark: |
| Test Case 22 | white pawn; `from = Location(4, 6)`, `to = Location(4, 7)` | return `false`; white cannot move backward toward larger `y` | :white_check_mark: |
| Test Case 23 | black pawn; `from = Location(4, 1)`, `to = Location(4, 0)` | return `false`; black cannot move backward toward smaller `y` | :white_check_mark: |
| Test Case 24 | white pawn; `from = Location(4, 6)`, `to = Location(5, 6)` | return `false`; horizontal movement with `dy = 0` is illegal | :white_check_mark: |
| Test Case 25 | black pawn; `from = Location(4, 1)`, `to = Location(5, 1)` | return `false`; horizontal movement with `dy = 0` is illegal | :white_check_mark: |
| Test Case 26 | white pawn; `from = Location(4, 6)`, `to = Location(4, 3)` | return `false`; forward overreach beyond the two-square initial boundary is illegal | :white_check_mark: |
| Test Case 27 | black pawn; `from = Location(4, 1)`, `to = Location(4, 4)` | return `false`; forward overreach beyond the two-square initial boundary is illegal | :white_check_mark: |
| Test Case 28 | white pawn at left edge; `from = Location(0, 6)`, `to = Location(0, 5)` | return `true`; a one-square forward move remains legal at board edge `x = 0` | :white_check_mark: |
| Test Case 29 | white pawn at left edge; `from = Location(0, 6)`, `to = Location(-1, 5)` | return `false`; destination just left of the board is outside `[0, 7]` | :x: |
| Test Case 30 | black pawn at right edge; `from = Location(7, 1)`, `to = Location(8, 2)` | return `false`; destination just right of the board is outside `[0, 7]` | :x: |
| Test Case 31 | white pawn; `from = Location(4, 6)`, `to = Location(4, 6)` | return `false`; same-square movement has no legal pawn move shape | :x: |
| Test Case 32 | white pawn; `from = null`, `to = Location(4, 5)` | throws `IllegalArgumentException` with message `"from must not be null"` | :x: |
| Test Case 33 | white pawn; `from = Location(4, 6)`, `to = null` | throws `IllegalArgumentException` with message `"to must not be null"` | :x: |
