# Board Setup Design

### Classes

#### Color - Enum
Constants: WHITE, BLACK

#### PieceType - Enum
Constants: KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN

#### Position - data class
Fields:
- row: int (private final)
- col: int (private final)

Methods:
- Position(int row, int col)
- getRow(): int - returns primitive, no reference issues
- getCol(): int - returns primitive, no reference issues
- equals(Object o): boolean

#### Piece
Fields:
- color: Color (private final)
- type: PieceType (private final)

Methods:
- Piece(PieceType type, Color color)
- getColor(): Color - safe, Color is enum
- getPieceType(): PieceType — safe, PieceType is enum

#### Board
Fields:
- squares: Piece[][] (private)

Methods:
- Board()
- initializeBoard(): void
- getPieceAt(Position pos): Piece - safe, Piece fields are private final with immutable types
- isEmpty(Position pos): boolean

#### Game
Fields:
- board: Board (private)
- currentTurn: Color (private)

Methods:
- Game()
- startGame(): void
- getCurrentTurn(): Color - safe reference, Color is enum
- getPieceAt(Position pos): Piece - delegate to Board, avoid exposing Board ref directly