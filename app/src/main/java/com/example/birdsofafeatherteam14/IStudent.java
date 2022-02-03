package com.example.birdsofafeatherteam14;

import java.util.List;

public interface IStudent {
    String getName();
    String getPhoto(); // Assuming we are storing the image links as a string?
    List<Course> getCourses();
}
