package org.example.am24is44.model;

public enum SpecialObject implements MergeEnumInterface {
    QUILL,
    INKWELL,
    MANUSCRIPT;

    @Override
    public boolean isSpecialObj() {
        return true;
    }
}
