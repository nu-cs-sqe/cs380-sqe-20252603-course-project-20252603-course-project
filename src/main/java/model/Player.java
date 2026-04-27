package model;

public class Player {

    private final String name;
    private final Color color;

    public Player(String name, Color color) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Player name must not be null or blank");
        if (color == null)
            throw new IllegalArgumentException("Color must not be null");
        this.name = name;
        this.color = color;
    }

    public String getName() { return name; }
    public Color getColor() { return color; }
}