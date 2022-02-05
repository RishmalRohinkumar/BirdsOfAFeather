package com.example.birdsofafeatherteam14.model;

import com.example.birdsofafeatherteam14.model.db.Course;

import java.util.Arrays;
import java.util.List;

public class DummyStudent implements IStudent{
    private String name;
    private String photo;
    private List<Course> courses;
    private int id;

    public DummyStudent(int id, String name, String photo, Course[] courses) {
        this.name = name;
        this.photo = photo;
        this.courses = Arrays.asList(courses);
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
