package org.example.am24is44.model;

import java.util.Map;

public class StarterCard extends Card{
    private Map<CornerPosition, Corner> backCorners;

    /**
     * StarterCard's constructor
     * @param resource
     * @param cardPoints
     * @param corners
     * @param backCorners
     */
    public StarterCard(Resource[] resource, CardPoints cardPoints, Map<CornerPosition, Corner> corners, Map<CornerPosition, Corner> backCorners) {
        super(resource, cardPoints, corners);
        this.backCorners = backCorners;
    }

    /**
     * Method inherited from the Card class, which indicates if the card is a gold card
     * @return boolean false because a started card isn't a gold card
     */
    @Override
    public boolean isGoldCard() {
        return false;
    }
}
