package org.example.myversion.server.model.enumerations;

/**
 * SpecialObject enumerates the possible special objects that can be associated with a corner.
 * Includes constants for Quill, Inkwell, Manuscript, and Corner.
 */
public enum SpecialObject implements CornerContent, PointsParameter {
    QUILL("Q"),
    INKWELL("I"),
    MANUSCRIPT("M");

    private final String shortName;

    /**
     * Constructor for SpecialObject enum to set the short name.
     *
     * @param shortName the short single-letter name used for TUI representation.
     */
    SpecialObject(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the short name of the special object.
     *
     * @return the short name of the special object.
     */
    public String getShortName() {
        return shortName;
    }
}
