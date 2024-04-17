package org.example.am24is44.model;

public class Corner {
    private boolean visibility;
    private Resource resource;
    private SpecialObject specialObject;
    private boolean covered;

    /**
     * Corner's constructor
     * @param visibility true if the corner is visible
     * @param resource
     * @param specialObject
     */
    public Corner(boolean visibility, Resource resource,SpecialObject specialObject) {
        this.visibility = visibility;
        this.resource = resource;
        this.specialObject = specialObject;
        this.covered = false;
    }

    /**
     * Method to get visibility
     * @return visibility
     */
    public boolean getVisibility() {
        return visibility;
    }

    /**
     * Method to get resource
     * @return resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Method to get special object
     * @return specialObject
     */
    public SpecialObject getSpecialObject() {
        return specialObject;
    }

}
