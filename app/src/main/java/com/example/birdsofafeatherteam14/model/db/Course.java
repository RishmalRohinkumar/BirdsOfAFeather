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

    @ColumnInfo(name = "course_size")
    public String courseSize;

    public Course(int courseId, int studentId, int courseYear, int courseNumber, String courseName,
                  String courseQuarter, String courseSize) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.courseYear = courseYear;
        this.courseName = courseName;
        this.courseNumber = courseNumber;
        this.courseQuarter = courseQuarter;
        this.courseSize = courseSize;
    }

    public String getCourse() {
        String num = String.valueOf(courseNumber);
        String year = String.valueOf(courseYear);
        return courseName + " " + num + " " + courseQuarter + " " + year + ", " + courseSize;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        return ((o instanceof Course) && this.getCourse().equals( ((Course)o).getCourse()) );
    }

    @Override
    public int hashCode() {
        return this.getCourse().hashCode();
    }
}
