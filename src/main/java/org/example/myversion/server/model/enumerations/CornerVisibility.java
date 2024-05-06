package org.example.myversion.server.model.enumerations;

/**
 * Enumerate the corner visibility.
 * If the corner is empty it is visible and doesn't contain any resource or special object.
 * If the corner is full it is not visible.
 */
public enum CornerVisibility implements CornerContent{
    EMPTY,
    FULL
}
