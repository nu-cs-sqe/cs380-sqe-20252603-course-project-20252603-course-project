# BVA analysis for Game

### Method under test: `createGame(int playerCount)`
#### Suite A: Invalid Boundaries (Expected: `IllegalArgumentException`)
- **TC1: One Below Minimum Players** ( ☑️ )
  - **State of the system**: `playerCount = 1`
  - **Expected output**: throws `IllegalArgumentException`

- **TC2: One Above Maximum Players** (  ☑️  )
  - **State of the system**: `playerCount = 6`
  - **Expected output**: throws `IllegalArgumentException`

#### Suite B: Valid Boundaries (Expected: `Game` object created)
- **TC3: Minimum Players** ( ☑️ )
  - **State of the system**: `playerCount = 2`
  - **Expected output**: `Game` object created with 2 players in `totalPlayers` and `alivePlayers`

- **TC4: Maximum Players** ( ☑️ )
  - **State of the system**: `playerCount = 5`
  - **Expected output**: `Game` object created with 5 players in `totalPlayers` and `alivePlayers`
  
### Method under test: `setup()`
#### Suite A: Valid Setup Configurations
- **TC5: Setup With Minimum Players** (  ☑️  )
  - **State of the system**: game created with 2 players; default deck
  - **Expected output**: each player has 7 cards (6 normal + 1 DEFUSE); deck contains 1 EXPLODING_KITTEN

- **TC6: Setup With Maximum Players** ( ☑️ )
  - **State of the system**: game created with 5 players; default deck
  - **Expected output**: each player has 7 cards (6 normal + 1 DEFUSE); deck contains 4 EXPLODING_KITTENs

### Method under test: `draw(Player player, Deck deck)`
#### Suite A: Valid Deck Sizes
- **TC7: Draw From Deck With One Card** ( ☑️ )
  - **State of the system**: `deck` contains exactly 1 card; `player` has 0 cards
  - **Expected output**: player receives the card; deck is now empty

- **TC8: Draw From Deck With Many Cards** ( ☑️  )
  - **State of the system**: `deck` contains 20 cards; `player` has 0 cards
  - **Expected output**: player receives only the top card; deck size decreases by 1

#### Suite B: Invalid States
- **TC9: Draw From Empty Deck** ( ☑️ )
  - **State of the system**: `deck` contains 0 cards; `player` has 0 cards
  - **Expected output**: throws an exception

### Method under test: `getTotalPlayerCount()`
#### Suite A: Boundary Checks
- **TC10: Minimum Total Players** ( ☑️ )
  - **State of the system**: game created with 2 players
  - **Expected output**: `getTotalPlayerCount() == 2`

- **TC11: Maximum Total Players** ( ☑️ )
  - **State of the system**: game created with 5 players
  - **Expected output**: `getTotalPlayerCount() == 5`

### Method under test: `getAlivePlayerCount()`
#### Suite A: Elimination Boundaries
- **TC12: All Players Alive (No Players Eliminated)** (  ☑️ )
  - **State of the system**: no players eliminated; game created with 2 players
  - **Expected output**: `getAlivePlayerCount() == 2`

- **TC13: One Player Eliminated** (  ☑️  )
  - **State of the system**: one player removed from `alivePlayers`; game created with 2 players
  - **Expected output**: `getAlivePlayerCount() == 1`

- **TC14: Last Player Remaining** ( ☑️ )
  - **State of the system**: all but one player eliminated; game created with 5 players
  - **Expected output**: `getAlivePlayerCount() == 1`

### Method under test: `getTotalPlayers()`
#### Suite A: Boundary Checks
- **TC15: Returns Correct Players When Minimum Players** ( ☑️  )
  - **State of the system**: game created with 2 players
  - **Expected output**: returned list contains exactly the 2 players created at construction

- **TC16: Returns Correct Players When Maximum Players** ( ☑️  )
  - **State of the system**: game created with 5 players
  - **Expected output**: returned list contains exactly the 5 players created at construction

### Method under test: `getAlivePlayers()`
#### Suite A: List Size Boundaries
- **TC17: Returns All Players When None Eliminated** ( ☑️ )
  - **State of the system**: game created with 5 players; no eliminations
  - **Expected output**: returned list contains all 5 players

- **TC18: Returns Reduced List After Elimination** ( ☑️ )
  - **State of the system**: game created with 5 players; one player removed from `alivePlayers`
  - **Expected output**: returned list contains the 4 remaining players

#### Suite B: Encapsulation Checks
- **TC19: Returns Unmodifiable List** ( ☑️ )
  - **State of the system**: game created with 5 players
  - **Expected output**: attempting to `.clear()` the returned list throws `UnsupportedOperationException`

### Method under test: `removeAlivePlayer(Player player)`
- **TC20: Remove Player From List With Many Players** ( ☑️ )
  - **State of the system**: game created with 5 players; specific player passed to removal method
  - **Expected output**: `.contains(player)` returns `false` and alive count is 4

- **TC21: Remove Player From List With One Player** ( ☑️ )
  - **State of the system**: game created with 2 players; one player already eliminated; one player remaining in `alivePlayers`
  - **Expected output**: `alivePlayers` is empty

- **TC22: Remove Player From Empty List** ( ☑️ )
  - **State of the system**: all players already eliminated; `alivePlayers` is empty
  - **Expected output**: throws an exception

- **TC23: Remove Player Not In Alive List** (  ☑️ )
  - **State of the system**: game created with 2 players; target player already eliminated
  - **Expected output**: throws an exception
  
### Method under test: `addAlivePlayer(Player player)`
- **TC24: Add Player Back To List With No Alive Players** ( ☑️ )
  - **State of the system**: game created with 2 players; both players eliminated; `alivePlayers` is empty
  - **Expected output**: `alivePlayers` contains 1 player

- **TC25: Add Player Back To List With Some Alive Players** ( ☑️ )
  - **State of the system**: game created with 5 players; one player eliminated; 4 players in `alivePlayers`
  - **Expected output**: `alivePlayers` contains 5 players

- **TC26: Add Player Not In Game** ( ☑️ )
  - **State of the system**: game created with 2 players; player being added was never in `totalPlayers`
  - **Expected output**: throws `IllegalArgumentException`

- **TC27: Add Player Already Alive** ( ☑️ )
  - **State of the system**: game created with 2 players; no eliminations; target player is already in `alivePlayers`
  - **Expected output**: throws `IllegalArgumentException`

### Method under test: `setAlivePlayersOrder(List<Player> newOrder)`
#### Suite A: Valid Order Updates (Boundary Checks)
- **TC28: Valid List Lower Bound** ( ☑️ )
  - **State of the system**: game created with 2 players; new list provided containing the same 2 players in a reversed order
  - **Expected output**: `alivePlayers` successfully updates to match the new order

- **TC29: Valid List Upper Bound** ( ☑️ )
  - **State of the system**: game created with 5 players; new list provided containing the same 5 players in a reversed order
  - **Expected output**: `alivePlayers` successfully updates to match the new order

#### Suite B: Invalid Order Updates (Expected: `IllegalArgumentException`)

- **TC30: List is Smaller Than Alive Players** ( ☑️ )
  - **State of the system**: game created with 4 players; `newOrder` contains 3 players
  - **Expected output**: throws `IllegalArgumentException`

- **TC31: List is Larger Than Alive Players** ( ☑️ )
  - **State of the system**: game created with 4 players; `newOrder` contains 5 players
  - **Expected output**: throws `IllegalArgumentException`