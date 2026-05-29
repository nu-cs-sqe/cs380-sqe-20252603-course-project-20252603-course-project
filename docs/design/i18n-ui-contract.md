# I18n UI Flow and Locale Passing Contract

## Language Selector Placement

The language selector appears at the beginning of the game flow within the `WelcomeView` screen.

- **Location**: Displayed at the top of the `WelcomeView` panel, before the player-name input fields.
- **Timing**: It is visible as soon as the application starts.
- **Default Selection**: If no selection is made, the application defaults to the first supported locale (typically English/US) or the system default if supported locales are missing.
- **Dynamic Update**: Changing the selection in the combo box immediately updates the UI strings in the `WelcomeView` to the selected language.

## Locale Passing Contract

The selected language (and its associated `ResourceBundle`) is passed from the welcome flow into the rest of the application using constructor parameters.

- **Flow**: `WelcomeView` -> `MainView` -> `GameStatsView`.
- **Mechanism**:
    1. When the user clicks "Start Game" in `WelcomeView`, the current `ResourceBundle` (reflecting the selected locale) is passed to the `MainView` constructor.
    2. `MainView` stores this `ResourceBundle` and uses it to localize its own components (e.g., window title).
    3. `MainView` further passes the same `ResourceBundle` instance to the `GameStatsView` constructor.
    4. `GameStatsView` uses the `ResourceBundle` to localize player labels and status messages.

This ensures that the entire game session remains in the language selected by the user at the start.
