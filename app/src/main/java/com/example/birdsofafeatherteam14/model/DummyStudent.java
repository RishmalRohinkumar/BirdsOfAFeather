package com.example.birdsofafeatherteam14.model;

import java.util.List;

public class DummyStudent implements IStudent{
    private String name;
    private String photo;
    private List<Course> courses;

    DummyStudent(String name, String photo, List<Course> courses) {
        this.name = name;
        this.photo = photo;
        this.courses = courses;
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
    public List<Course> getCourses() {
        return courses;
    }
}
