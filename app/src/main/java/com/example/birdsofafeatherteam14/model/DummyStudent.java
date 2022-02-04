package com.example.birdsofafeatherteam14.model;

import com.example.birdsofafeatherteam14.model.db.Course;

import java.util.List;

public class DummyStudent implements IStudent{
    private String name;
    private String photo;
    private List<Course> courses;
    private int id;

    DummyStudent(int id, String name, String photo, List<Course> courses) {
        this.name = name;
        this.photo = photo;
        this.courses = courses;
        this.id = id;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    @Override
    public int getId() { return id; }

    @Override
    public List<Course> getCourses() {
        return courses;
    }
}
