# BVA Analysis for Queen

`Queen` is concrete. This file covers the public API declared in `Queen.java`: the constructor and `makeCopy()`. Inherited methods are covered in `docs/bva/Piece.md`.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - The constructor input `color` has valid `PieceColor` cases `BLACK` and `WHITE`.
  - Because `color` is a Java reference type, `null` is also a settable boundary value.
  - `makeCopy()` has no parameters, but its behavior depends on the valid receiver state: a `Queen` with `color = BLACK` or `color = WHITE`.
- Step 1, output equivalence classes:
  - The constructor creates a `Queen` whose type is fixed to `PieceType.QUEEN` and whose color is the supplied non-null `color`.
  - The constructor rejects `null` color with `IllegalArgumentException`.
  - `makeCopy()` returns a distinct `Queen` object whose type is `PieceType.QUEEN` and whose color matches the original queen.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` is `Cases`.
  - The `color` reference also uses the `Pointers` boundary: null pointer vs pointer to a true object.
  - Returned `Piece` objects from `makeCopy()` use the `Pointers` boundary: pointer to a true object, and specifically a distinct object from the receiver.
  - There are no generated string outputs in the public methods declared by `Queen`, so string-length boundaries do not apply here.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: first `BLACK`, second/last `WHITE`, plus settable pointer boundary `null`.
  - `PieceType` is not caller-controlled in `Queen`; it is always `QUEEN`.
  - Impossible non-enum color values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice over the single varying input/state variable, `color`.
  - Cover every `PieceColor` case and the null-pointer boundary once for the constructor.
  - Cover every valid receiver color state once for `makeCopy()`.
  - All-combinations would produce the same selected cases because there is only one varying boundary variable per method.


### Method under test: `Queen(PieceColor color)`

|             | System under test                      | Expected output                                                               | Implemented? |
|-------------|----------------------------------------|-------------------------------------------------------------------------------|--------------|
| Test Case 1 | constructor args: `color = BLACK`      | queen constructs successfully; `getType()` returns `QUEEN`; `getColor()` returns `BLACK` | :white_check_mark: |
| Test Case 2 | constructor args: `color = WHITE`      | queen constructs successfully; `getType()` returns `QUEEN`; `getColor()` returns `WHITE` | :white_check_mark: |
| Test Case 3 | constructor args: `color = null`       | `Queen(null)` throws `IllegalArgumentException`                                | :white_check_mark: |


### Method under test: `makeCopy()`

|             | System under test            | Expected output                                                                                       | Implemented? |
|-------------|------------------------------|-------------------------------------------------------------------------------------------------------|--------------|
| Test Case 4 | queen: `Queen(BLACK)`        | return a distinct `Piece`; copied piece is a `Queen`; copied piece has `type = QUEEN`; copied piece has `color = BLACK` | :white_check_mark: |
| Test Case 5 | queen: `Queen(WHITE)`        | return a distinct `Piece`; copied piece is a `Queen`; copied piece has `type = QUEEN`; copied piece has `color = WHITE` | :white_check_mark: |
| Test Case 6 | queen: `Queen(null)`         | `Queen(null)` throws `IllegalArgumentException`; null-color `makeCopy()` receiver state is not constructible | :white_check_mark: |
