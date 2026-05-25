# BVA Analysis for WelcomeView

`WelcomeView` is a `JFrame` subclass that collects player names and fires off the game. It accepts
a `Locale` at construction time, loads the matching `MessagesBundle` resource bundle, and renders
every user-visible string from that bundle. When the player clicks "Start Game", it forwards the
same `Locale` to `MainView`.

---

## Language-Selector Placement Contract

**Which screen:** The language is selected before or at the moment `WelcomeView` is constructed.
The `Locale` is supplied as a constructor argument, so the UI is already rendered in the chosen
language when the welcome screen first appears.

**Timing relative to player-name entry:** The language choice is resolved *before* the player-name
fields are shown. In the current implementation `Main.main()` passes `Locale.getDefault()` to
`WelcomeView`. A future language-selector widget would collect the locale and then construct
`WelcomeView(selectedLocale)`, ensuring the name-entry fields are labelled in the right language
from the first paint.

**If no selection is made:** The application defaults to `Locale.getDefault()` (the JVM system
locale). If no `MessagesBundle_<lang>_<country>.properties` file matches that locale, Java's
`ResourceBundle` walks the fallback chain and ultimately loads the root bundle
(`MessagesBundle.properties`), which contains English strings.

---

## Locale-Passing Contract

The selected `Locale` travels through the UI layer as an explicit constructor parameter — no
global or static state is used:

1. `WelcomeView(Locale locale)` — receives the locale on construction; loads the bundle; stores
   the locale for forwarding.
2. On "Start Game" → `new MainView(player1Name, player2Name, locale)` — locale forwarded.
3. `MainView` constructor → `new GameStatsView(player1Name, player2Name, locale)` — locale
   forwarded to the stats panel.
4. `GameStatsView` stores the locale for use when it renders live game statistics.

---

## Step 1–3 Summary

- **Step 1, input equivalence classes:** The `Locale` parameter has four partitions: *null*
  (invalid — below any usable value), *Locale.ROOT* (explicit default, no language/country),
  *supported locale* (en_US or es_US — exact bundle file exists), *unsupported locale* (any other
  — triggers ResourceBundle fallback to root bundle).
- **Step 2, BVA catalog mapping:** `Locale` maps to a **Discrete Named Set with null sentinel**.
  There is no continuous numeric range; boundaries are the edges of the supported-locale set plus
  the null guard.
- **Step 3, concrete boundary values:** `null` (below min), `Locale.ROOT` (min / canonical
  default), `Locale.US` (min+1 of supported set), `new Locale("es","US")` (max of supported set),
  `new Locale("fr","FR")` (above max — outside supported set).
- **Step 4 strategy:** **Each-choice** — the four partitions are independent, so one test case per
  partition is sufficient. The null case doubles as the guard against misuse.

---

### Constructor under test: `WelcomeView(Locale locale)`

| ID  | State of the System | Expected Output | Implemented? |
|-----|---------------------|-----------------|--------------|
| TC1 | `locale = Locale.US` — supported en_US bundle exists | Constructor completes; `getBundle().getLocale()` equals `Locale.US` | :white_check_mark: |
| TC2 | `locale = new Locale("es","US")` — supported es_US bundle exists | Constructor completes; `getBundle().getLocale()` equals `new Locale("es","US")` | :white_check_mark: |
| TC3 | `locale = Locale.ROOT` — explicit default, no language tag | Constructor completes; `getBundle().getLocale()` equals `Locale.ROOT` | :white_check_mark: |
| TC4 | `locale = new Locale("fr","FR")` — unsupported, no matching bundle | Constructor completes; `getBundle().getLocale()` equals `Locale.ROOT` (fallback) | :white_check_mark: |
| TC5 | `locale = null` — null input below domain minimum | `NullPointerException` thrown before UI is constructed | :white_check_mark: |
