package org.example.myversion.server.model.enumerations;

/**
 * Represents two of the parameter to get the points associated with a gold card.
 * The parameter can be empty, the number of covered corners or a special object.
 * This class enumerates the empty and corner cases.
 */
public enum ParameterType implements PointsParameter{
    CORNER,
    EMPTY
}
