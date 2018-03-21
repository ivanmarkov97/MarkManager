package com.example.ivan.markmanager.ProjectRecyclerPackage;

/**
 * Created by Ivan on 20.03.2018.
 */

public class Project {
    private String name;
    private String description;
    private String createdAt;
    private int id;

    public Project(String name, String description, String createdAt, int id) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
