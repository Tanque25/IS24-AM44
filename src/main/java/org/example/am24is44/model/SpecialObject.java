package org.example.am24is44.model;

public enum SpecialObject implements MergeEnumInterface {
    QUILL,
    INKWELL,
    MANUSCRIPT,
    CORNER;


    /**
     * Method inherited from the MergeEnumInterface, which indicates if the object is a special object
     * @return boolean true because this object is considered a special object
     */
    @Override
    public boolean isSpecialObj() {
        return true;
    }
}
