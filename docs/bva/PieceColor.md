# BVA Analysis for PieceColor

`PieceColor` is a Java enum with compiler-generated public enum behavior. The planned tests cover the declared constants plus the generated `values()` and `valueOf(String)` methods.

## Step 1-3 Summary

- Step 1, input equivalence classes:
  - The enum constants have no method input; the public cases are `BLACK` and `WHITE`.
  - `values()` has no method input.
  - `valueOf(String name)` accepts exact valid names `"BLACK"` and `"WHITE"`.
  - Because `name` is a Java reference, `null` is a settable invalid boundary.
  - Invalid generated strings are names tied to the enum input state, such as wrong case, extra whitespace, and a missing enum case; they are not string-length boundaries.
- Step 1, output equivalence classes:
  - Constant access returns the singleton enum objects `PieceColor.BLACK` and `PieceColor.WHITE`.
  - `values()` returns a new `PieceColor[]` containing exactly `BLACK` then `WHITE`.
  - `valueOf(String)` returns `BLACK` or `WHITE` for exact valid names.
  - `valueOf(String)` throws `NullPointerException` for `null` and `IllegalArgumentException` for non-null invalid names.
- Step 2, BVA catalog mapping from the BVA catalog:
  - `PieceColor` enum constants and enum outputs use the `Cases` category.
  - The `valueOf(String name)` reference also uses the `Pointers` category: null pointer vs pointer to a real `String` object.
  - Generated strings for invalid `valueOf` inputs are tied to the allowed enum names, not to minimum or maximum string length boundaries.
- Step 3, concrete boundary values selected from the catalog:
  - `PieceColor`: first case `BLACK`, second/last case `WHITE`.
  - `valueOf` valid names: `"BLACK"` and `"WHITE"`.
  - `valueOf` pointer boundary: `null`.
  - `valueOf` invalid generated names: `"black"` for wrong case, `" BLACK "` for extra whitespace, and `"RED"` for a missing enum case.
  - Impossible non-enum values are omitted because they are `CAN'T SET` in Java.
- Step 4 strategy:
  - Use each-choice because the enum has one independent case variable and one nullable `String` input for `valueOf`.
  - Include every enum case at least once for constants, `values()`, and valid `valueOf` behavior.
  - Add separate invalid pointer and invalid generated-name cases for `valueOf`.


### Method under test: enum constants

|             | System under test                    | Expected output                                            | Implemented? |
|-------------|--------------------------------------|------------------------------------------------------------|--------------|
| Test Case 1 | access constant `PieceColor.BLACK`   | returns singleton enum constant `BLACK`; name is `"BLACK"` | :x: |
| Test Case 2 | access constant `PieceColor.WHITE`   | returns singleton enum constant `WHITE`; name is `"WHITE"` | :x: |


### Method under test: `values()`

|             | System under test                         | Expected output                                                          | Implemented? |
|-------------|-------------------------------------------|--------------------------------------------------------------------------|--------------|
| Test Case 3 | call `PieceColor.values()`                | returns array of length 2: index 0 is `BLACK`, index 1 is `WHITE`        | :x: |
| Test Case 4 | call `PieceColor.values()` twice          | returns distinct array objects; both arrays contain `BLACK` then `WHITE` | :x: |


### Method under test: `valueOf(String name)`

|             | System under test                         | Expected output                | Implemented? |
|-------------|-------------------------------------------|--------------------------------|--------------|
| Test Case 5 | call `PieceColor.valueOf("BLACK")`        | return `BLACK`                 | :x: |
| Test Case 6 | call `PieceColor.valueOf("WHITE")`        | return `WHITE`                 | :x: |
| Test Case 7 | call `PieceColor.valueOf(null)`           | `NullPointerException`         | :x: |
| Test Case 8 | call `PieceColor.valueOf("black")`        | `IllegalArgumentException`     | :x: |
| Test Case 9 | call `PieceColor.valueOf(" BLACK ")`      | `IllegalArgumentException`     | :x: |
| Test Case 10 | call `PieceColor.valueOf("RED")`         | `IllegalArgumentException`     | :x: |
