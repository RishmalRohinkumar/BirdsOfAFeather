package com.example.birdsofafeatherteam14;

import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import java.util.ArrayList;
import java.util.List;

// Deals with taking a students information and transforming it into CSV
public class StudentToCSVTranslator {
    private Student student;
    private List<Course> courses;

    StudentToCSVTranslator(Student student, List<Course> courses) {
        this.student = student;
        this.courses = courses;
    }

    // Converts the class's student/course information into a csv string
    public String getCSV() {
        // Parse the data members into strings
        String uuid = this.student.uuid;
        String name = this.student.getName();
        String pic = this.student.getPhoto();
        List<String> courseStrings = new ArrayList<String>();
        for (Course c : courses) {
            String s = c.courseYear + "," + c.courseQuarter + "," + c.courseName + "," + c.courseNumber + "," + c.courseSize;
            courseStrings.add(s);
        }

        // Put the parsed strings together in the correct format
        String csv = uuid + ",,,,\n" + name + ",,,,\n" +  pic + ",,,,\n";
        for (int i = 0; i < courseStrings.size(); i++) {
            String s = courseStrings.get(i);
            if(i == courseStrings.size()-1){
                csv += s;
                continue;
            }
            csv += s + "\n";

        }

        return csv;
    }
}
