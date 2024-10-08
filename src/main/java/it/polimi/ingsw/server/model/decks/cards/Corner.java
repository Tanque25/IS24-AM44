package it.polimi.ingsw.server.model.decks.cards;

import it.polimi.ingsw.server.model.enumerations.CornerContent;

/**
 * Represents a corner of a card.
 * Each corner can have content, such as a resource or special object, and can be covered or uncovered.
 */
public class Corner {
    private final CornerContent cornerContent;
    private boolean covered;

    /**
     * Constructs a corner with the specified content and initializes it as uncovered.
     *
     * @param cornerContent the content of the corner, which can be a resource, special object, or empty/full visibility.
     */
    public Corner(CornerContent cornerContent) {
        this.cornerContent = cornerContent;
        this.covered = false;
    }

    /**
     * Retrieves the content of the corner.
     *
     * @return the content of the corner.
     */
    public CornerContent getCornerContent() {
        return cornerContent;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    /**
     * Checks if the corner is covered.
     *
     * @return true if the corner is covered, false otherwise.
     */
    public boolean isCovered() {
        return covered;
    }
}
