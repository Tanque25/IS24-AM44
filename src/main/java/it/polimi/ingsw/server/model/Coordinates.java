package it.polimi.ingsw.server.model;

/**
 * Represents the coordinates of a position on the board.
 * Coordinates are defined by their x and y values.
 */
public class Coordinates {
    private int x;
    private int y;

    /**
     * Constructs a Coordinates object with the specified x and y values.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the x-coordinate of the position.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate of the position.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }
}
