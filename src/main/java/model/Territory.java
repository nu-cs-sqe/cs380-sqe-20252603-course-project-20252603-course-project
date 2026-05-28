package model;

public class Territory {

    private String name;
    private Player owner;
    private int armyCount;
    private Continent continent;

    public Territory() {
    }

    public Territory(
            final String inName,
            final Player inOwner,
            final int inArmyCount,
            final Continent inContinent
    ) {
        setName(inName);
        setOwner(inOwner);
        setArmyCount(inArmyCount);
        setContinent(inContinent);
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String newName) {

        this.name = newName;
    }

    public final Player getOwner() {
        return owner;
    }

    public final void setOwner(final Player newOwner) {
        this.owner = newOwner;
    }

    public final int getArmyCount() {
        return armyCount;
    }

    public final void setArmyCount(final int newArmyCount) {
        if (newArmyCount < 0) {
            throw new IllegalArgumentException("armyCount cannot be negative");
        }
        this.armyCount = newArmyCount;
    }

    public final Continent getContinent() {
        return continent;
    }

    public final void setContinent(final Continent newContinent) {
        this.continent = newContinent;
    }
}
