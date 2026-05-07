# BVA Analysis for Location

`Location` is a simple value object that stores a pair of `(x, y)` integer board coordinates as `final` fields. It performs no validation — any `int` is accepted and returned as-is. The real domain constraint (columns 0–7, rows 0–7 on a standard 8×8 chess board) comes from the chess context, not from `Location` itself. BVA boundaries are therefore drawn around that chess-board range.

## Step 1–3 Summary

- **Step 1, input equivalence classes:** For each of `x` and `y`: below-valid-range (< 0), within-valid-range (0..7), above-valid-range (> 7).
- **Step 1, output equivalence classes:** `getX()` and `getY()` are pure getters — the output class mirrors the stored value, so the same three partitions apply to each output.
- **Step 2, BVA catalog mapping:** Both `x` and `y` map to the **Bounded Integer** category with domain [0, 7].
- **Step 3, concrete boundary values selected:** −1 (below min), 0 (min), 1 (min+1), 6 (max−1), 7 (max), 8 (above max) — applied independently to each axis.
- **Step 4 strategy:** **Each-choice** — because `x` and `y` are independent inputs, there is no need to enumerate all pairs; one dedicated sweep per axis is sufficient. `y` is held at a neutral mid-range value (3) while sweeping `x`, and vice versa.

---

### Method under test: `getX()`

Each test constructs a `Location` with the listed `x` (and `y = 3`) and calls `getX()`.

|              | State of the System                  | Expected output | Implemented? |
|--------------|--------------------------------------|-----------------|--------------|
| Test Case 1  | `Location(0, 3)` — x at min (0)      | `0`             | :x:          |
| Test Case 2  | `Location(7, 3)` — x at max (7)      | `7`             | :x:          |
| Test Case 3  | `Location(1, 3)` — x at min+1 (1)   | `1`             | :x:          |
| Test Case 4  | `Location(6, 3)` — x at max−1 (6)   | `6`             | :x:          |
| Test Case 5  | `Location(-1, 3)` — x just below min | `-1`            | :x:          |
| Test Case 6  | `Location(8, 3)` — x just above max  | `8`             | :x:          |

---

### Method under test: `getY()`

Each test constructs a `Location` with the listed `y` (and `x = 3`) and calls `getY()`.

|               | State of the System                    | Expected output | Implemented? |
|---------------|----------------------------------------|-----------------|--------------|
| Test Case 7   | `Location(3, 0)` — y at min (0)        | `0`             | :x:          |
| Test Case 8   | `Location(3, 7)` — y at max (7)        | `7`             | :x:          |
| Test Case 9   | `Location(3, 1)` — y at min+1 (1)     | `1`             | :x:          |
| Test Case 10  | `Location(3, 6)` — y at max−1 (6)     | `6`             | :x:          |
| Test Case 11  | `Location(3, -1)` — y just below min   | `-1`            | :x:          |
| Test Case 12  | `Location(3, 8)` — y just above max    | `8`             | :x:          |
