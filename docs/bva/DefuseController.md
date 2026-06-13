### Method under test: executeCardAction()

inputs: Game game, Player user, Optional<Player> target
output: IllegalStateException

- **TC1: executeCardAction_defusePlayed_throwIllegalStateException ** ( :white_check_mark: )
    - **State of the system**: any game, user, target
    - **Expected output**: IllegalStateException