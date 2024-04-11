package org.example.am24is44.model;

public class Corner {
    private boolean visibility;
    private Resource resource;
    private SpecialObject specialObject;
    private boolean covered;

    // Constructor
    public Corner(boolean visibility, Resource resource,SpecialObject specialObject) {
        this.visibility = visibility;
        this.resource = resource;
        this.specialObject = specialObject;
        this.covered = false;
    }

    // Method to get visibility
    public boolean getVisibility() {
        return visibility;
    }

    // Method to get resource
    public Resource getResource() {
        return resource;
    }

    // Method to get special object
    public SpecialObject getSpecialObject() {
        return specialObject;
    }

}
