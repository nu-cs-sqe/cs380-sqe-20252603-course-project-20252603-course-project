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

    public Player(int id, String name, PlayerColor color, int remainingArmiesToPlace, List<Territory> controlledTerritories) {
        setId(id);
        setName(name);
        setColor(color);
        setRemainingArmiesToPlace(remainingArmiesToPlace);
        setControlledTerritories(controlledTerritories);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("id cannot be negative");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        this.name = name;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public int getRemainingArmiesToPlace() {
        return remainingArmiesToPlace;
    }

    public void setRemainingArmiesToPlace(int remainingArmiesToPlace) {
        if (remainingArmiesToPlace < 0) {
            throw new IllegalArgumentException("remainingArmiesToPlace cannot be negative");
        }
        this.remainingArmiesToPlace = remainingArmiesToPlace;
    }

    public List<Territory> getControlledTerritories() {
        return List.copyOf(controlledTerritories);
    }

    public void setControlledTerritories(List<Territory> territories) {
        this.controlledTerritories = new ArrayList<>(territories);
    }

    public int getControlledTerritoryCount() {
        return controlledTerritories.size();
    }

    public void addControlledTerritory(Territory territory) {
        if (!controlledTerritories.contains(territory)) {
            controlledTerritories.add(territory);
        }
    }

    public void removeControlledTerritory(Territory territory) {
        controlledTerritories.remove(territory);
    }

}
