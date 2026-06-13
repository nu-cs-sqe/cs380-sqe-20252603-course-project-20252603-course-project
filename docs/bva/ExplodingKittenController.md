### Method under test: executeCardAction()

inputs: Game game, Player initiator, Optional<Player> target
- BOOLEAN: initiator.hasDefuse()
- LIST: initiator.getHand()

output: Optional.empty() (Optional<List<Card>>)
- BOOLEAN: initiator.isAlive
- LIST: initiator.getHand()

- **TC1: executeCardAction_noDefuseEmptyHand_playerKilled ** ( :white_check_mark: )
    - **State of the system**: hand: []
    - **Expected output**: initiator.isAlive(): false, initiator.getHand(): []

- **TC2: executeCardAction_oneDefuseOneCard_playerLivesEmptyHand ** ( :white_check_mark: )
    - **State of the system**: hand: [DEFUSE]
    - **Expected output**: initiator.isAlive(): true, initiator.getHand(): []

- **TC3: executeCardAction_twoDefuses_playerLivesOneDefuse ** ( :white_check_mark: )
    - **State of the system**: hand: [DEFUSE, DEFUSE]
    - **Expected output**: initiator.isAlive(): true, initiator.getHand(): [DEFUSE]

- **TC4: executeCardAction_lastCardDefuseTwoCards_playerLivesOneCard ** ( :white_check_mark: )
    - **State of the system**: hand: [ATTACK, DEFUSE]
    - **Expected output**: initiator.isAlive(): true, initiator.getHand(): [ATTACK]

- **TC5: executeCardAction_firstCardDefuseThreeCards_playerLivesOneCard ** ( :white_check_mark: )
    - **State of the system**: hand: [DEFUSE, ATTACK, SKIP]
    - **Expected output**: initiator.isAlive(): true, initiator.getHand(): [ATTACK, SKIP]