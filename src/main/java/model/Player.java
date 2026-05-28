package model;

import java.util.ArrayList;
import java.util.List;

import service.PlayerColor;

public class Player {

    private int id;
    private String name;
    private PlayerColor color;
    private int remainingArmiesToPlace;
    private List<Territory> controlledTerritories;

    public Player(
            final int inId,
            final String inName,
            final PlayerColor inColor,
            final int inRemainingArmiesToPlace,
            final List<Territory> inControlledTerritories
    ) {
        setId(inId);
        setName(inName);
        setColor(inColor);
        setRemainingArmiesToPlace(inRemainingArmiesToPlace);
        setControlledTerritories(inControlledTerritories);
    }

    public final int getId() {
        return id;
    }

    public final void setId(final int newId) {
        if (newId < 0) {
            throw new IllegalArgumentException("id cannot be negative");
        }
        this.id = newId;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String newName) {
        if (newName.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        this.name = newName;
    }

    public final PlayerColor getColor() {
        return color;
    }

    public final void setColor(final PlayerColor newColor) {
        this.color = newColor;
    }

    public final int getRemainingArmiesToPlace() {
        return remainingArmiesToPlace;
    }

    public final void setRemainingArmiesToPlace(
            final int newRemainingArmiesToPlace) {
        if (newRemainingArmiesToPlace < 0) {
            throw new IllegalArgumentException(
                    "remainingArmiesToPlace cannot be negative"
            );
        }
        this.remainingArmiesToPlace = newRemainingArmiesToPlace;
    }

    public final List<Territory> getControlledTerritories() {
        return List.copyOf(controlledTerritories);
    }

    public final void setControlledTerritories(
            final List<Territory> newTrritories
    ) {
        this.controlledTerritories = new ArrayList<>(newTrritories);
    }

    public final int getControlledTerritoryCount() {
        return controlledTerritories.size();
    }

    public final void addControlledTerritory(final Territory newTerritory) {
        if (!controlledTerritories.contains(newTerritory)) {
            controlledTerritories.add(newTerritory);
        }
    }

    public final void removeControlledTerritory(
            final Territory newTerritory
    ) {
        controlledTerritories.remove(newTerritory);
    }

}
