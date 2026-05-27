# Integration Test Contract Analysis for Board Setup and Snapshot Behavior

The following integration tests define the **external system contract** of the `Board` class. These tests focus on initialization correctness, snapshot safety guarantees, and global game-state consistency.

Unlike unit tests, these tests validate how the `Board` behaves as a **black-box system component** shared across domain, controller, and UI layers.

# Snapshot Contract (`getSnapshot()`)

The `getSnapshot()` method must guarantee:
- A non-null return value
- A fixed `8x8` board structure
- No null row arrays
- A consistent representation of the current board state

This ensures that external consumers (UI/controller/tests) can safely read board state without risking structural inconsistency.

---

# Game State Contract

A newly constructed `Board` must always initialize with:

- `GameState.WHITE_TURN`

This ensures:
- consistent starting behavior
- deterministic game initialization
- alignment between domain logic and UI rendering

---

# Snapshot Safety Contract (Defensive Copy)

The board must protect internal state by ensuring:
- Any array returned from `getSnapshot()` is a **defensive copy**
- Mutating the returned snapshot must NOT affect the internal board state
- Each call to `getSnapshot()` produces an independent structure

This guarantees encapsulation and prevents external mutation of game state.

---

# Step 1–3 Summary

## Step 1 — Input Equivalence Classes
- Board construction (`new Board()`)
- Snapshot retrieval (`getSnapshot()`)
- Snapshot mutation after retrieval
- Initial game state retrieval (`getGameState()`)

---

## Step 2 — Output Equivalence Classes
- Snapshot is non-null and structured as `8x8`
- All rows in snapshot are non-null
- Initial state is `WHITE_TURN`
- Snapshot mutations do not affect internal board state

---

## Step 3 — Boundary Value Analysis Mapping

### Board Dimensions
- Fixed boundaries: `0..7` for both rows and columns
- Snapshot must always return exactly `8x8`

### Structural Boundaries
- Top row: `0`
- Bottom row: `7`
- Middle region: `2..5` (must be empty at initialization)

### Reference Boundaries
- Snapshot must be a **deep copy vs shared reference boundary**
    - Valid: independent arrays
    - Invalid: direct reference to internal `pieces`

---

# Integration Test Specifications

## Method under test: `Board.getSnapshot()`

|             | System under test                      | Expected output                     | Implemented?       |
|-------------|----------------------------------------|-------------------------------------|--------------------|
| Test Case 1 | `getSnapshot()`                        | returns a non-null array            | :white_check_mark: |
| Test Case 2 | `getSnapshot()`                        | returned array has exactly 8 rows   | :white_check_mark: |
| Test Case 3 | `getSnapshot()`                        | each row has exactly 8 columns      | :white_check_mark: |
| Test Case 4 | `getSnapshot()`                        | no row in returned snapshot is null | :white_check_mark: |

---

## Method under test: `Board()` initialization

|             | System under test                      | Expected output      | Implemented?       |
|-------------|----------------------------------------|--------------------------------------|--------------------|
| Test Case 5 | `new Board()`                          | initial game state is `GameState.WHITE_TURN`     | :white_check_mark: |
| Test Case 6 | `new Board()`                          | board initializes into a valid consistent 8x8 starting configuration | :white_check_mark: |

---

## Method under test: Snapshot Isolation

|             | System under test                      | Expected output                                                | Implemented? |
|-------------|----------------------------------------|----------------------------------------------------------------|--------------|
| Test Case 7 | mutate returned snapshot               | internal board state is NOT modified                           | :x:          |
| Test Case 8 | multiple calls to `getSnapshot()`      | each snapshot is an independent copy (no shared references)    | :x:          |