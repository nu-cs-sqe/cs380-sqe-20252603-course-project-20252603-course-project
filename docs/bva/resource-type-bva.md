# BVA Analysis – `ResourceType`

## Enum under test: `ResourceType`

The `ResourceType` enum represents the five resource types in Catan. Each non-desert terrain hex produces exactly one resource type. The game bank holds 19 cards of each type (95 total).

---

### TC1 – Exactly 5 resource types exist
- **State of the system**: ResourceType enum loaded
- **Expected output**: `ResourceType.values().length == 5`
- **BVA note**: 5 is the only valid count. Boundaries: 4 (too few), 5 (valid), 6 (too many)

### TC2 – BRICK is a valid resource type
- **State of the system**: ResourceType enum loaded
- **Expected output**: `ResourceType.BRICK` exists and is not null
- **BVA note**: Boundary between a defined enum constant and a missing one

### TC3 – LUMBER is a valid resource type
- **State of the system**: ResourceType enum loaded
- **Expected output**: `ResourceType.LUMBER` exists and is not null
- **BVA note**: Boundary between a defined enum constant and a missing one

### TC4 – ORE is a valid resource type
- **State of the system**: ResourceType enum loaded
- **Expected output**: `ResourceType.ORE` exists and is not null
- **BVA note**: Boundary between a defined enum constant and a missing one

### TC5 – GRAIN is a valid resource type
- **State of the system**: ResourceType enum loaded
- **Expected output**: `ResourceType.GRAIN` exists and is not null
- **BVA note**: Boundary between a defined enum constant and a missing one

### TC6 – WOOL is a valid resource type
- **State of the system**: ResourceType enum loaded
- **Expected output**: `ResourceType.WOOL` exists and is not null
- **BVA note**: Boundary between a defined enum constant and a missing one

### TC7 – Each resource type has a unique name
- **State of the system**: ResourceType enum loaded
- **Expected output**: All 5 values of `ResourceType.values()` have distinct `name()` strings
- **BVA note**: Boundary between 5 unique names (valid) and any duplicate (invalid)

### TC8 – Invalid string does not map to a resource type
- **State of the system**: ResourceType enum loaded
- **Expected output**: `ResourceType.valueOf("DIAMOND")` throws `IllegalArgumentException`
- **BVA note**: Boundary between valid resource names (BRICK, LUMBER, ORE, GRAIN, WOOL) and any invalid string