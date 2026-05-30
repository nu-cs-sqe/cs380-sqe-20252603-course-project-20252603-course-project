# i18n Resource Bundle Design

## Key Naming Convention

Keys follow the pattern `<screen>.<elementDescription>` using camelCase for both parts.

| Segment | Values |
|---------|--------|
| `<screen>` | `welcome`, `mainWindow`, `game` |
| `<elementDescription>` | camelCase description of the UI element (e.g. `title`, `startButton`) |

**Examples:** `welcome.startButton`, `game.currentPlayer`, `mainWindow.title`

New keys must use an existing screen prefix or introduce a new prefix agreed on by the team.

## Required Key List (Week 1)

| Key | English Value | Owning UI Class |
|-----|--------------|-----------------|
| `game.currentPlayer` | `Current Player:` | `GameStatsView` |
| `mainWindow.title` | `Chess` | `MainView` |
| `welcome.missingNameMessage` | `Please enter a name for both players.` | `WelcomeView` |
| `welcome.missingNameTitle` | `Missing Name` | `WelcomeView` |
| `welcome.player1Label` | `Player 1 name:` | `WelcomeView` |
| `welcome.player2Label` | `Player 2 name:` | `WelcomeView` |
| `welcome.startButton` | `Start Game` | `WelcomeView` |
| `welcome.title` | `Chess — Welcome` | `WelcomeView` |
| `welcome.languageLabel` | `Select Language:` | `WelcomeView` |
| `language.english` | `English` | `WelcomeView` |
| `language.spanish` | `Spanish` | `WelcomeView` |

All three bundle files (`MessagesBundle.properties`, `MessagesBundle_en_US.properties`,
`MessagesBundle_es_US.properties`) contain every key in this table.

## Locale Fallback Behavior

Java `ResourceBundle.getBundle("MessagesBundle", locale)` searches for bundle files in
the following order (most specific to least specific):

1. `MessagesBundle_<language>_<country>.properties` (e.g. `MessagesBundle_en_US.properties`)
2. `MessagesBundle_<language>.properties` (e.g. `MessagesBundle_en.properties`)
3. `MessagesBundle.properties` (the default — ultimate fallback)

If a key is absent from the locale-specific file, Java automatically supplies it from the
next less-specific file in the chain. The default `MessagesBundle.properties` is the
ultimate fallback: if a key is missing there, a `MissingResourceException` is thrown at
runtime.

**Policy:** Every required key must be present in `MessagesBundle.properties`. Locale-
specific bundles may omit keys and rely on fallback, but having explicit values in each
file is preferred to avoid silent fallback to a different language.
