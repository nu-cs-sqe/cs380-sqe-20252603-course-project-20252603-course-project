# BVA Analysis for `GameStatsView`

`GameStatsView` is a Swing UI component responsible for displaying player information and current turn state in a chess game. It extends `JPanel` and constructs a vertical layout containing labels for player names, team assignments, and the current active player.

The BVA below targets the constructor behavior and UI rendering contract visible in the source.

---

## Step 1-3 Summary

- Step 1, input equivalence classes:
    - `GameStatsView(String player1Name, String player2Name)` has two input parameters:
        - `player1Name`
        - `player2Name`
    - Each input belongs to the following equivalence classes:
        - valid non-null non-empty string (e.g., `"Alice"`)
        - empty string `""`
        - `null` reference (invalid but possible runtime boundary in Java)
    - Constructor implicitly depends on Swing component initialization:
        - `JPanel` superclass initialization
        - `BoxLayout` setup on `Y_AXIS`
        - label creation and attachment in correct order
    - UI state dependencies:
        - Player 1 is always assigned to `"White"`
        - Player 2 is always assigned to `"Black"`
        - `currentPlayerLabel` is initialized to Player 1 at construction time

- Step 1, output equivalence classes:
    - `GameStatsView` creates a valid `JPanel` subclass instance
    - Panel contains exactly four UI components:
        - Player info header label
        - Player 1 label
        - Player 2 label
        - Current player label
    - Labels must correctly reflect:
        - injected player names
        - fixed team assignments
        - initial turn state (Player 1)
    - UI properties must be correctly set:
        - layout is vertical `BoxLayout (Y_AXIS)`
        - background color is `(104, 76, 150)`
        - panel is opaque
    - `currentPlayerLabel` must be accessible and initialized

---

- Step 2, BVA catalog mapping from the BVA catalog:
    - Input strings use `Cases`:
        - normal name string (e.g., `"Alice"`)
        - empty string `""`
        - `null` reference
    - UI component count uses `Intervals`:
        - minimum meaningful components = 4
        - less than 4 = invalid construction state
    - Label content correctness uses `Cases`:
        - correct player name insertion
        - correct team assignment (White/Black fixed mapping)
        - correct current player initialization (Player 1)
    - Swing layout/state uses `Cases`:
        - `BoxLayout.Y_AXIS`
        - correct background color
        - opacity enabled
    - Object references use `Pointers`:
        - label reference exists vs null label reference

---

- Step 3, concrete boundary values selected from the catalog:
    - Player names:
        - `"Alice"` (typical valid case)
        - `"Bob"` (typical valid case)
        - `""` (empty boundary case)
        - `null` (invalid boundary case)
    - Component structure boundaries:
        - exactly 4 components added in correct order
    - Layout boundary:
        - `BoxLayout.Y_AXIS` must be enforced
    - Color boundary:
        - `(104, 76, 150)` must be exact RGB match
    - Label content boundaries:
        - Player 1 → White
        - Player 2 → Black
        - Current player → Player 1 name at initialization

---

- Step 4 strategy:
    - Use each-choice coverage for constructor inputs (player names)
    - Use structural assertions for component count and ordering
    - Use content-based assertions for label correctness
    - Use state-based assertions for layout and styling
    - Keep tests deterministic (no reliance on UI rendering engine behavior)
    - Avoid testing Swing internals beyond exposed state (`getComponent`, `getText`)

---

## Method under test: `GameStatsView(String player1Name, String player2Name)`

|             | System under test | Expected output | Implemented?       |
|-------------|-------------------|-----------------|--------------------|
| Test Case 1 | `new GameStatsView("Alice", "Bob")` | panel is created successfully | :white_check_mark: |
| Test Case 2 | constructor call | layout is `BoxLayout` with `Y_AXIS` orientation | :white_check_mark: |
| Test Case 3 | constructor call | panel contains exactly 4 components | :white_check_mark: |
| Test Case 4 | constructor call | player 1 label contains `"Alice"` and `"White"` | :white_check_mark: |
| Test Case 5 | constructor call | player 2 label contains `"Bob"` and `"Black"` | :white_check_mark: |
| Test Case 6 | constructor call | current player label initially displays player 1 name | :white_check_mark: |
| Test Case 7 | constructor call | background color is `(104, 76, 150)` | :white_check_mark: |
| Test Case 8 | constructor call | panel is opaque | :white_check_mark: |
| Test Case 9 | constructor with empty string inputs | labels still render structurally (no crash) | :x:                |
| Test Case 10 | constructor with `null` input | label displays `"null"` or handles safely without crash | :x:                |
| Test Case 11 | component structure | labels are added in correct order (info, p1, p2, current) | :x:                |