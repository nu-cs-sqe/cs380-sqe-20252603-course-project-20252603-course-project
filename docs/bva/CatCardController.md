# BVA Analysis for CatCardController class

### Method under test: executeCardAction()

inputs: GameController gameController, Player initiator, Optional<Player> target

cardsPlayed (2 or 3) is injected at construction time via new CatCardController(int cardsPlayed, CatCardControllerView controllerView). 
requestedCard is obtained inside the controller via controllerView.getRequestedCardType() for the 3-card play only.
- BOOLEAN: target.isPresent()
- BOOLEAN: target.get().equals(initiator)
- BOOLEAN: gameController.getGame().getAlivePlayers().contains(target.get())
- INTEGER: cardsPlayed (injected at construction — 2 or 3)
- BOOLEAN: controllerView.getRequestedCardType().isPresent() (3-card play only)
- INTEGER: target.get().getHandSize()
- BOOLEAN: target.get().hasCard(requestedType) (3-card play only)

output: Optional<List<Card>> (Optional.empty() or Optional.of(List.of(stolenCard)))
- BOOLEAN: initiator.getHandSize() increases by 1 on successful steal
- BOOLEAN: target.getHandSize() decreases by 1 on successful steal

Category 1: Invalid Plays (Defensive Guards)

- **TC1: executeCardAction_targetMissing_noSteal ** ( x )
    - **State of the system**: target is Optional.empty().
    - **Expected output**: Returns Optional.empty(). No hand changes.

- **TC2: executeCardAction_selfTargeting_noSteal ** ( x )
    - **State of the system**: target.isPresent() is true. target.get().equals(initiator) is true.
    - **Expected output**: Returns Optional.empty(). No hand changes.

- **TC3: executeCardAction_targetNotAlive_noSteal ** ( x )
    - **State of the system**: target.isPresent() is true. target.get() is not in gameController.getGame().getAlivePlayers().
    - **Expected output**: Returns Optional.empty(). No hand changes.

Category 2: The 2-Card Play (Random Steal)
Controller instantiated as new CatCardController(2, controllerView).

- **TC4: executeCardAction_twoCardsTargetEmptyHand_noSteal ** ( x )
    - **State of the system**: target is valid. target.get().getHandSize() is 0.
    - **Expected output**: Returns Optional.empty(). No hand changes.

- **TC5: executeCardAction_twoCardsTargetOneCard_stealsOnlyCard ** ( x )
    - **State of the system**: target is valid. target.get().getHandSize() is 1. (boundary)
    - **Expected output**: Returns Optional.of([stolenCard]). initiator.getHandSize() increases by 1. target.getHandSize() is 0.

- **TC6: executeCardAction_twoCardsNominal_stealsRandomCard ** ( x )
    - **State of the system**: target is valid. target.get().getHandSize() is > 1.
    - **Expected output**: Returns Optional.of([stolenCard]). initiator.getHandSize() increases by 1. target.getHandSize() decreases by 1.

Category 3: The 3-Card Play (Chosen Steal)
Controller instantiated as new CatCardController(3, controllerView).

- **TC7: executeCardAction_threeCardsMissingRequestedCard_noSteal ** ( x )
    - **State of the system**: target is valid. controllerView.getRequestedCardType() returns Optional.empty().
    - **Expected output**: Returns Optional.empty(). No hand changes.

- **TC8: executeCardAction_threeCardsTargetDoesNotHaveCard_whiff ** ( x )
    - **State of the system**: target is valid. controllerView.getRequestedCardType() returns a valid CardType. target.get().hasCard(requestedType) is false.
    - **Expected output**: Returns Optional.empty(). No hand changes.

- **TC9: executeCardAction_threeCardsTargetHasCard_stealsChosenCard ** ( x )
    - **State of the system**: target is valid. controllerView.getRequestedCardType() returns a valid CardType. target.get().hasCard(requestedType) is true.
    - **Expected output**: Returns Optional.of([stolenCard]) where stolenCard.getType() matches requestedType. initiator.getHandSize() increases by 1. target.getHandSize() decreases by 1.