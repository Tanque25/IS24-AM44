package org.example.myversion.server.model.enumerations;

/**
 * Enumerate the corner visibility.
 * If the corner is empty it is visible and doesn't contain any resource or special object.
 * If the corner is full it is not visible.
 */
public enum CornerVisibility implements CornerContent{
    EMPTY("▫"),
    FULL("▪");

    private final String shortName;

    /**
     * Constructor for CornerVisibility enum to set the short name.
     *
     * @param shortName the short single-letter name used for TUI representation.
     */
    CornerVisibility(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the short name of the corner visibility.
     *
     * @return the short name of the corner visibility.
     */
    public String getShortNameWithColor() {
        return shortName + "\u001B[0m"; // Resets the color after the short name
    }

    /**
     * Gets the short name of the corner visibility.
     *
     * @return the short name of the corner visibility.
     */
    public String getShortName() {
        return shortName;
    }
}
