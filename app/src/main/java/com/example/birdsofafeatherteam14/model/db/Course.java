package com.example.birdsofafeatherteam14.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public int courseId;

    @ColumnInfo(name = "student_id")
    public int studentId;

    @ColumnInfo(name = "course_quarter")
    public String courseQuarter;

    @ColumnInfo(name = "course_name")
    public String courseName;

    @ColumnInfo(name = "course_number")
    public int courseNumber;

    @ColumnInfo(name = "course_year")
    public int courseYear;

    public Course(int courseId, int studentId, int courseYear, int courseNumber, String courseName, String courseQuarter) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.courseYear = courseYear;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.courseQuarter = courseQuarter;
    }


}
