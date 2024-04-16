package org.example.am24is44.model;

public enum Resource implements MergeEnumInterface{
    PLANT_KINGDOM,
    ANIMAL_KINGDOM,
    FUNGI_KINGDOM,
    INSECT_KINGDOM,
    ;

    /**
     * @return false
     */
    @Override
    public boolean isSpecialObj() {
        return false;
    }
}
