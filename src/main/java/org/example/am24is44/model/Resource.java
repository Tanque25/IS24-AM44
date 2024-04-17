package org.example.am24is44.model;

public enum Resource implements MergeEnumInterface{
    PLANT_KINGDOM,
    ANIMAL_KINGDOM,
    FUNGI_KINGDOM,
    INSECT_KINGDOM,
    ;

    /**
     * Method inherited from the MergeEnumInterface, which indicates if the object is a special object
     * @return false because this object is considered a Resource and not a specialObject
     */
    @Override
    public boolean isSpecialObj() {
        return false;
    }
}
