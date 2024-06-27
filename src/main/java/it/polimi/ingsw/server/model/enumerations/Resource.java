package it.polimi.ingsw.server.model.enumerations;

/**
 * Resource enumerates the possible resource a resource or gold card can have or the resource associated with a corner.
 * Includes constants for the Plant Kingdom, Animal Kingdom, Fungi Kingdom, and Insect Kingdom.
 */
public enum Resource implements CornerContent{
    PLANT_KINGDOM("\u001B[92m" + "P" + "\u001B[0m"),
    ANIMAL_KINGDOM("\u001B[94m" + "A" + "\u001B[0m"),
    FUNGI_KINGDOM("\u001B[91m" + "F" + "\u001B[0m"),
    INSECT_KINGDOM("\u001B[95m" + "I" + "\u001B[0m");

    private final String shortName;

    /**
     * Constructor for Resource enum to set the short name.
     *
     * @param shortName the short single-letter name used for TUI representation.
     *
     */
    Resource(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the short name of the resource.
     *
     * @return the short name of the resource.
     */
    public String getShortName() {
        return shortName;
    }
}
