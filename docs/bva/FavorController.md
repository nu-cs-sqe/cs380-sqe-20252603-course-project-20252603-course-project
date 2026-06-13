### Method under test: `executeCardAction()`

inputs: Game game, Player user, Optional<Player> target
- LIST: user.getHand()
- LIST: target.getHand()
- CARD: card target chooses, from UI

output: Optional.empty() (Optional<List<Card>>)
- LIST: user.getHand()
- LIST: target.getHand()

- **TC1: executeCardAction_targetOneCard_cardGiven** ( :white-check-mark: )
    - **State of the system**: user.getHand(): [], target.getHand(): [ATTACK], CARD: ATTACK
    - **Expected output**: user.getHand(): [ATTACK], target.getHand(): []

- **TC2: executeCardAction_targetTwoCards_cardGiven** ( :white-check-mark: )
    - **State of the system**: user.getHand(): [], target.getHand(): [ATTACK, SKIP], CARD: ATTACK
    - **Expected output**: user.getHand(): [ATTACK], target.getHand(): [SKIP]

- **TC3: executeCardAction_targetDuplicateCards_cardGiven** ( :white-check-mark: )
    - **State of the system**: user.getHand(): [], target.getHand(): [ATTACK, ATTACK], CARD: ATTACK
    - **Expected output**: user.getHand(): [ATTACK], target.getHand(): [ATTACK]

- **TC4: executeCardAction_userDuplicateCards_cardGiven** ( :white-check-mark: )
    - **State of the system**: user.getHand(): [ATTACK], target.getHand(): [ATTACK], CARD: ATTACK
    - **Expected output**: user.getHand(): [ATTACK, ATTACK], target.getHand(): []

- **TC5: executeCardAction_targetFirstCard_cardGiven** ( :white-check-mark: )
    - **State of the system**: user.getHand(): [], target.getHand(): [SKIP, ATTACK, DEFUSE], CARD: SKIP
    - **Expected output**: user.getHand(): [SKIP], target.getHand(): [ATTACK, DEFUSE]

- **TC6: executeCardAction_targetLastCard_cardGiven** ( :white-check-mark: )
    - **State of the system**: user.getHand(): [], target.getHand(): [SKIP, ATTACK, DEFUSE], CARD: DEFUSE
    - **Expected output**: user.getHand(): [DEFUSE], target.getHand(): [SKIP, ATTACK]

- **TC7: executeCardAction_userTwoCards_cardGiven** ( :white-check-mark: )
    - **State of the system**: user.getHand(): [DEFUSE, SKIP], target.getHand(): [ATTACK], CARD: ATTACK
    - **Expected output**: user.getHand(): [DEFUSE, SKIP, ATTACK], target.getHand(): []

- **TC8: executeCardAction_cardNotInTarget_illegalArgumentException** ( :white-check-mark: )
    - **State of the system**: user.getHand(): [], target.getHand(): [ATTACK], CARD: DEFUSE
    - **Expected output**: illegalArgumentException, user.getHand(): [], target.getHand(): [ATTACK]


