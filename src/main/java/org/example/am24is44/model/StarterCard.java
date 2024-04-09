package org.example.am24is44.model;

import java.util.Map;

public class StarterCard extends Card{
    private Map<CornerPosition, Corner> backCorners;

    public StarterCard(Resource[] resource, CardPoints cardPoints, Map<CornerPosition, Corner> corners, Map<CornerPosition, Corner> backCorners) {
        super(resource, cardPoints, corners);
        this.backCorners = backCorners;
    }
}
