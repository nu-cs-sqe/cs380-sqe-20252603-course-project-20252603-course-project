# BVA Analysis for `Main`

`Main` is the application entry-point class responsible for launching the chess UI. It exposes only the static method `main(String[] args)` and delegates Swing UI initialization onto the Swing Event Dispatch Thread (EDT) using `SwingUtilities.invokeLater(...)`.

The implementation currently creates a `WelcomeView` instance and makes it visible.

The BVA below targets the current UI-launching contract visible in the source.

---

## Step 1-3 Summary

- Step 1, input equivalence classes:
    - `main(String[] args)` accepts:
        - non-null `String[]`
        - empty array `{}`
        - populated array (unused arguments)
        - `null` reference
    - Current implementation does not inspect or parse command-line arguments.
    - `main(...)` behavior depends on:
        - successful scheduling onto the Swing EDT
        - successful construction of `WelcomeView`
        - successful visibility activation via `setVisible(true)`
    - UI-launch responsibilities include:
        - application startup
        - EDT-safe Swing initialization
        - initial window visibility

- Step 1, output equivalence classes:
    - `main(...)` successfully schedules UI startup on the EDT.
    - `WelcomeView` is instantiated exactly once during startup.
    - `WelcomeView` becomes visible.
    - `main(...)` does not throw exceptions for:
        - empty args
        - populated args
        - `null` args
    - Swing launch flow remains asynchronous through `invokeLater(...)`.

---

- Step 2, BVA catalog mapping from the BVA catalog:
    - `args` input uses `Cases`:
        - empty array
        - populated array
        - null reference
    - Swing visibility state uses `Boolean`:
        - visible
        - not visible
    - Object creation uses `Pointers`:
        - valid `WelcomeView` reference
        - failed/null construction state
    - Thread-launch behavior uses `Cases`:
        - EDT scheduling
        - direct execution (invalid design alternative)

---

- Step 3, concrete boundary values selected from the catalog:
    - `args` boundaries:
        - `new String[]{}`
        - `new String[]{"test"}`
        - `null`
    - Visibility boundaries:
        - `true`
        - implicit default `false`
    - UI startup boundary:
        - single `WelcomeView` creation
    - Threading boundary:
        - execution delegated through `SwingUtilities.invokeLater(...)`

---

- Step 4 strategy:
    - Use each-choice coverage for command-line argument cases.
    - Use behavioral assertions for visibility activation.
    - Use constructor-launch verification for UI startup correctness.
    - Avoid testing Swing internal rendering behavior.
    - Treat EDT scheduling as a launch responsibility rather than visual rendering responsibility.

---

## Method under test: `main(String[] args)`

|             | System under test | Expected output | Implemented? |
|-------------|-------------------|-----------------|--------------|
| Test Case 1 | `main(new String[]{})` | application launches without exception | :x:          |
| Test Case 2 | `main(new String[]{"test"})` | application launches without exception even when args are unused | :x:          |
| Test Case 3 | `main(null)` | application launches without exception | :x:          |
| Test Case 4 | application startup | `WelcomeView` instance is created | :x:          |
| Test Case 5 | application startup | `WelcomeView` becomes visible via `setVisible(true)` | :x:          |
| Test Case 6 | application startup | UI launch is delegated through `SwingUtilities.invokeLater(...)` | :x:          |