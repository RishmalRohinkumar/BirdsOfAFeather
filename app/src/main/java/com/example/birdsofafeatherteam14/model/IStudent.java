package com.example.birdsofafeatherteam14.model;

import com.example.birdsofafeatherteam14.model.db.Course;

import java.util.List;

public interface IStudent {
    String getName();
    String getPhoto(); // Assuming we are storing the image links as a string?
    int getId();
    List<Course> getCourses();
}
