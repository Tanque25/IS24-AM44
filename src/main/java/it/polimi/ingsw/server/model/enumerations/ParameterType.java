package it.polimi.ingsw.server.model.enumerations;

/**
 * Represents two of the parameter to get the points associated with a gold card.
 * The parameter can be empty, the number of covered corners or a special object.
 * This class enumerates the empty and corner cases.
 */
public enum ParameterType implements PointsParameter{
    CORNER("C"),
    EMPTY("―");

    private final String shortName;

    /**
     * Constructor for ParameterType enum to set the short name.
     *
     * @param shortName the short single-letter name used for TUI representation.
     */
    ParameterType(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the short name of the parameter type.
     *
     * @return the short name of the parameter type.
     */
    public String getShortName() {
        return shortName;
    }
}
