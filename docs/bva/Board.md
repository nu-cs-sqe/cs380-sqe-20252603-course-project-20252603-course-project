# BVA Analysis for `Board`

`Board` represents the initial chess board state and currently exposes the constructor `Board()` and `getSnapshot()`. The implementation initializes an 8×8 board with standard starting positions for all chess pieces except that empty squares are represented by `null`.

The BVA below targets the current implementation contract visible in the source.

## Step 1-3 Summary

- Step 1, input equivalence classes:
    - `Board()` has no explicit parameters.
    - The constructor implicitly depends on successful allocation of an `8 x 8` `Piece[][]`.
    - `initializeBoard()` populates:
        - black major pieces on row `0`
        - black pawns on row `1`
        - empty middle rows `2..5`
        - white pawns on row `6`
        - white major pieces on row `7`
    - `getSnapshot()` has no parameters.
    - `getSnapshot()` behavior depends on the current internal board state:
        - occupied square (`Piece != null`)
        - empty square (`null`)
    - `makeCopy()` must be called on every non-null piece during snapshot creation.
    - Snapshot correctness depends on:
        - dimensions preserved
        - piece type preserved
        - piece color preserved
        - deep-copy semantics (new object references)
        - null preservation for empty squares

- Step 1, output equivalence classes:
    - `Board()` creates a valid initialized chess board.
    - `getSnapshot()` returns:
        - a non-null `Piece[][]`
        - dimensions `8 x 8`
        - correctly initialized piece locations
        - nulls in empty squares
        - copies instead of original references
    - Mutating the returned snapshot must not mutate the original board state.

- Step 2, BVA catalog mapping from the BVA catalog:
    - Board dimensions use `Intervals`:
        - minimum index `0`
        - maximum index `7`
        - middle-region indices `2..5`
    - Piece placement categories use `Cases`:
        - pawn
        - rook
        - knight
        - bishop
        - queen
        - king
        - empty square
    - Piece colors use `Cases`:
        - `WHITE`
        - `BLACK`
    - Snapshot object identity uses `Pointers`:
        - same reference vs distinct reference

- Step 3, concrete boundary values selected from the catalog:
    - Row boundaries:
        - `0`
        - `1`
        - `2`
        - `5`
        - `6`
        - `7`
    - Column boundaries:
        - `0`
        - `1`
        - `3`
        - `4`
        - `6`
        - `7`
    - Representative squares:
        - corners `(0,0)`, `(0,7)`, `(7,0)`, `(7,7)`
        - center empty squares `(3,3)`, `(4,4)`
        - king/queen files `(0,3)`, `(0,4)`, `(7,3)`, `(7,4)`
    - Snapshot identity boundaries:
        - copied piece reference is different
        - copied piece state is equal
    - Impossible invalid array dimensions are omitted because they are not externally settable.

- Step 4 strategy:
    - Use each-choice for representative piece placements.
    - Use explicit edge coverage for board corners and edge rows.
    - Use representative middle-board empty squares for null-region validation.
    - Use dedicated deep-copy tests to verify snapshot independence.
    - Mark tests according to implementation completeness.

---

## Method under test: `Board()`

|             | System under test | Expected output | Implemented?       |
|-------------|-------------------|-----------------|--------------------|
| Test Case 1 | `new Board()` | creates non-null board instance | :white_check_mark: |
| Test Case 2 | board initialization | snapshot dimensions are `8 x 8` | :white_check_mark: |
| Test Case 3 | board initialization | black pawns exist at row `1`, cols `0..7` | :white_check_mark: |
| Test Case 4 | board initialization | white pawns exist at row `6`, cols `0..7` | :white_check_mark: |
| Test Case 5 | board initialization | black rooks exist at `(0,0)` and `(0,7)` | :white_check_mark: |
| Test Case 6 | board initialization | white rooks exist at `(7,0)` and `(7,7)` | :white_check_mark: |
| Test Case 7 | board initialization | black knights exist at `(0,1)` and `(0,6)` | :white_check_mark: |
| Test Case 8 | board initialization | white knights exist at `(7,1)` and `(7,6)` | :white_check_mark: |
| Test Case 9 | board initialization | black bishops exist at `(0,2)` and `(0,5)` | :white_check_mark: |
| Test Case 10 | board initialization | white bishops exist at `(7,2)` and `(7,5)` | :white_check_mark: |
| Test Case 11 | board initialization | black queen at `(0,3)` and black king at `(0,4)` | :x:                |
| Test Case 12 | board initialization | white queen at `(7,3)` and white king at `(7,4)` | :x:                |
| Test Case 13 | board initialization | rows `2..5` contain only `null` references | :x:                |

---

## Method under test: `getSnapshot()`

|             | System under test | Expected output | Implemented? |
|-------------|-------------------|-----------------|--------------|
| Test Case 14 | initialized board; call `getSnapshot()` | returns non-null `Piece[][]` | :x:          |
| Test Case 15 | initialized board; call `getSnapshot()` | returned snapshot dimensions are `8 x 8` | :x:          |
| Test Case 16 | snapshot at `(0,0)` | contains `Rook(BLACK)` | :x:          |
| Test Case 17 | snapshot at `(7,4)` | contains `King(WHITE)` | :x:          |
| Test Case 18 | snapshot at `(3,3)` | contains `null` because middle squares are empty | :x:          |
| Test Case 19 | snapshot piece copy at `(0,0)` | snapshot rook is not the same reference as internal rook | :x:          |
| Test Case 20 | snapshot piece copy at `(7,1)` | copied knight preserves `PieceType = KNIGHT` and `PieceColor = WHITE` | :x:          |
| Test Case 21 | modify returned snapshot reference at `(0,0)` | original board state remains unchanged on future snapshots | :x:          |
| Test Case 22 | two successive calls to `getSnapshot()` | corresponding non-null pieces are distinct object references across snapshots | :x:          |