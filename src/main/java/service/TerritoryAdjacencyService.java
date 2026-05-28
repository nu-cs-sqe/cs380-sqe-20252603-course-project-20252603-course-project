package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.TerritoryAdjacencyCatalog;
import domain.TerritoryCatalog;
import model.Continent;
import model.Territory;

public class TerritoryAdjacencyService {

    private final Map<String, Territory> territoryByName = new HashMap<>();

    public final void initializeTerritoryAdjacency(
            final List<Territory> territories
    ) {

        territoryByName.clear();
        for (Territory territory : territories) {

            String name = territory.getName();
            if (territoryByName.containsKey(name)) {
                throw new IllegalArgumentException(
                        "duplicate territory name: " + name
                );
            }
            territoryByName.put(name, territory);
        }
    }

    public final boolean areAdjacent(
            final Territory territoryA,
            final Territory territoryB) {
        List<String> adjacentNames
                = TerritoryAdjacencyCatalog.ADJACENT_MAP.get(
                        territoryA.getName()
                );
        return adjacentNames.contains(territoryB.getName());
    }

    public final List<Territory> getAdjacentTerritories(
            final Territory territory
    ) {
        List<String> adjacentNames
                = TerritoryAdjacencyCatalog.ADJACENT_MAP.get(
                        territory.getName()
                );

        List<Territory> adjacentTerritories = new ArrayList<>();
        for (String adjacentName : adjacentNames) {
            Territory adjacentTerritory = territoryByName.get(adjacentName);
            adjacentTerritories.add(adjacentTerritory);
        }
        return List.copyOf(adjacentTerritories);
    }

    public final List<Territory> createAllTerritories() {
        List<Territory> territories = new ArrayList<>();
        for (Map.Entry<Continent, List<String>> entry
                : TerritoryCatalog.TERRITORIES_BY_CONTINENT.entrySet()) {
            Continent continent = entry.getKey();
            for (String name : entry.getValue()) {
                territories.add(new Territory(name, null, 0, continent));
            }
        }
        return List.copyOf(territories);
    }

    public final Territory findByName(
            final List<Territory> territories,
            final String name
    ) {

        for (Territory territory : territories) {
            if (territory.getName().equals(name)) {
                return territory;
            }
        }
        throw new IllegalArgumentException("territory not found: " + name);
    }
}
