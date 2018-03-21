package com.example.ivan.markmanager.TaskRecyclerPackage;

import com.example.ivan.markmanager.ProjectRecyclerPackage.Project;

/**
 * Created by Ivan on 20.03.2018.
 */

public class Task {
    private String name;
    private String description;
    private String createdAt;
    private String deadline;
    private int priority;
    private int position;
    private Task parent;
    private Project project;
    private int owner;
    private int id;

    public Task(String name,
                String description,
                String createdAt,
                String deadline,
                int priority,
                int position,
                Task parent,
                Project project,
                int owner,
                int id) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.priority = priority;
        this.position = position;
        this.parent = parent;
        this.project = project;
        this.owner = owner;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Task getParent() {
        return parent;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
