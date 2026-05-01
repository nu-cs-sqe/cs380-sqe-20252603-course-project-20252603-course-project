# BVA Analysis for Bishop

`Bishop` is concrete. This file covers the public API declared in `Bishop.java`: the constructor and `makeCopy()`. Inherited methods are covered in `docs/bva/Piece.md`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - The constructor input `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `color` is a Java reference type, `null` is also a settable boundary value.
  - `makeCopy()` has no parameters, but its behavior depends on the receiver state: a `Bishop` with `color = BLACK`, `color = WHITE`, or `color = null`.
- Step 1, output equivalence classes:
  - The constructor creates a `Bishop` whose type is fixed to `PieceType.BISHOP` and whose color is the supplied `color`.
  - `makeCopy()` returns a distinct `Bishop` object whose type is `PieceType.BISHOP` and whose color matches the original bishop, including `null`.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - The `color` reference also uses the `Pointers` boundary: null pointer vs pointer to a true object.
  - Returned `Piece` objects from `makeCopy()` use the `Pointers` boundary: pointer to a true object, and specifically a distinct object from the receiver.
  - There are no string outputs in the public methods declared by `Bishop`.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: first `BLACK`, second/last `WHITE`, plus settable pointer boundary `null`.
  - `PieceType` is not caller-controlled in `Bishop`; it is always `BISHOP`.
  - Impossible non-enum color values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice over the single varying input/state variable, `color`.
  - Cover every `PieceColor` case and the null-pointer boundary once for the constructor.
  - Cover every receiver color state once for `makeCopy()`.
  - All-combinations would produce the same selected cases because there is only one varying boundary variable per method.


### Method under test: `Bishop(PieceColor color)`

|             | System under test                       | Expected output                                                                 | Implemented? |
|-------------|-----------------------------------------|---------------------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `color = BLACK`       | bishop constructs successfully; `getType()` returns `BISHOP`; `getColor()` returns `BLACK` | :white_check_mark: |
| Test Case 2 | constructor args: `color = WHITE`       | bishop constructs successfully; `getType()` returns `BISHOP`; `getColor()` returns `WHITE` | :x: |
| Test Case 3 | constructor args: `color = null`        | bishop constructs successfully; `getType()` returns `BISHOP`; `getColor()` returns `null`  | :x: |


### Method under test: `makeCopy()`

|             | System under test              | Expected output                                                                                         | Implemented? |
|-------------|--------------------------------|---------------------------------------------------------------------------------------------------------|--------------|
| Test Case 4 | bishop: `Bishop(BLACK)`        | return a distinct `Piece`; copied piece is a `Bishop`; copied piece has `type = BISHOP`; copied piece has `color = BLACK` | :x: |
| Test Case 5 | bishop: `Bishop(WHITE)`        | return a distinct `Piece`; copied piece is a `Bishop`; copied piece has `type = BISHOP`; copied piece has `color = WHITE` | :x: |
| Test Case 6 | bishop: `Bishop(null)`         | return a distinct `Piece`; copied piece is a `Bishop`; copied piece has `type = BISHOP`; copied piece has `color = null`  | :x: |
