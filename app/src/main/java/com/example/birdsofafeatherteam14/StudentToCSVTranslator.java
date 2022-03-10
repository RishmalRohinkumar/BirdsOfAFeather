package com.example.birdsofafeatherteam14;

import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentToCSVTranslator {
    private Student student;
    private List<Course> courses;

    StudentToCSVTranslator(Student student, List<Course> courses) {
        this.student = student;
        this.courses = courses;
    }

    public String getCSV() {
        String uuid = this.student.uuid;
        String name = this.student.getName();
        String pic = this.student.getPhoto();
        List<String> courseStrings = new ArrayList<String>();
        for (Course c : courses) {
            String s = c.courseYear + "," + c.courseQuarter + "," + c.courseName + "," + c.courseNumber + "," + c.courseSize;
            courseStrings.add(s);
        }

        String csv = uuid + ",,,,\n" + name + ",,,,\n" +  pic + ",,,,\n";
        for (String s : courseStrings) {
            csv += s + "\n";
        }

        return csv;
    }
}
