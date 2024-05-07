package org.example.myversion.server.model.enumerations;

/**
 * Resource enumerates the possible resource a resource or gold card can have or the resource associated with a corner.
 * Includes constants for the Plant Kingdom, Animal Kingdom, Fungi Kingdom, and Insect Kingdom.
 */
public enum Resource implements CornerContent{
    PLANT_KINGDOM("P"),
    ANIMAL_KINGDOM("A"),
    FUNGI_KINGDOM("F"),
    INSECT_KINGDOM("I");

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
