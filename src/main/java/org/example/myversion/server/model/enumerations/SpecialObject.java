package org.example.myversion.server.model.enumerations;

/**
 * SpecialObject enumerates the possible special objects that can be associated with a corner.
 * Includes constants for Quill, Inkwell, Manuscript, and Corner.
 */
public enum SpecialObject implements CornerContent, PointsParameter {
    QUILL,
    INKWELL,
    MANUSCRIPT
}
