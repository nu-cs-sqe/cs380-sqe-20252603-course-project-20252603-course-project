# BVA Analysis for Rook

`Rook` is a concrete `Piece` subclass. Its constructor fixes the piece type to `PieceType.ROOK`, so the only constructor input boundary is the `PieceColor color` argument. The source under analysis is `src/main/java/domain/piece/Rook.java`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `PieceColor` is a Java reference type, `null` is an invalid pointer boundary for `color`.
  - `makeCopy()` has no parameters, so its input classes come from the existing valid `Rook` state: `color = BLACK` or `color = WHITE`.
  - A `Rook` with `color = null` is not a valid public state once constructor null handling is corrected.
- Step 1, output equivalence classes:
  - The constructor creates a `Rook` whose `type` is always `ROOK` and whose `color` is the constructor argument for valid colors.
  - The constructor throws `IllegalArgumentException` with message `"color must not be null"` when `color = null`.
  - `makeCopy()` returns a distinct `Rook` instance typed as `Piece`; the copy has `type = ROOK` and the same valid color as the original.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - The `color` reference also uses the `Pointers` boundary: null pointer vs pointer to a true object.
  - `Rook` has no generated string output in its own public constructor or `makeCopy()` method, so string-length boundaries do not apply here.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: first `BLACK`, second/last `WHITE`, plus invalid pointer boundary `null`.
  - Impossible non-enum `PieceColor` values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice across the `PieceColor` case values and the invalid null pointer boundary.
  - Repeat valid state boundaries for `makeCopy()` because it derives its output from the original rook's stored color.
  - Treat the null-color `makeCopy()` state as `CAN'T SET` through the public constructor after the constructor null check is implemented.


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
