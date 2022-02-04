package com.example.birdsofafeatherteam14.model.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.birdsofafeatherteam14.model.IStudent;

import java.util.List;

public class StudentWithCourses implements IStudent {

    @Embedded
    public Student student;

    @Relation(parentColumn = "id",
            entityColumn = "student_id",
            entity = Course.class)
            //We changed this
            //projection = {"text"})\
    public List<Course> courses;

    @Override
    public int getId() {
        return this.student.studentId;
    }

    @Override
    public String getName() {
        return this.student.name;
    }

    @Override
    public String getPhoto() {
        return this.student.photo;
    }

    @Override
    public List<Course> getCourses() {
        return this.courses;
    }
}
