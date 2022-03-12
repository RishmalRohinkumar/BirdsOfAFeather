package com.example.birdsofafeatherteam14;

import static org.junit.Assert.assertEquals;

import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StudentToCSVTranslatorTests {


    // Test student data from canvas
    @Test
    public void testCanvasCSV() {
        // https://canvas.ucsd.edu/courses/32700/assignments/451849
        // make student with same info as the

        Student student = new Student(1, 1, "Bill", "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0", "a4ca50b6-941b-11ec-b909-0242ac120002", true);
        Course course1 = new Course(1, 1, 2021, 210, "CSE", "FA", "Small");
        Course course2 = new Course(2, 1, 2022, 110, "CSE", "WI", "Large");
        List<Course> courses = new ArrayList<Course>();

        courses.add(course1);
        courses.add(course2);
        StudentToCSVTranslator translator = new StudentToCSVTranslator(student, courses);
        String csvFormat = translator.getCSV();

        assertEquals("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\n" +
                "Bill,,,,\n" +
                "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n" +
                "2021,FA,CSE,210,Small\n" +
                "2022,WI,CSE,110,Large", csvFormat);

    }

    //Another Similar Test with Altered Values for the courses
    //Jinuk 3-10-21:17:00:57 PST
    @Test
    public void testCanvas2CSV() {
        Student student = new Student(1, 1, "Frank", "none", "a4ca50b6-941b-11ec-b909-0242ac120002",true );
        Course course1 = new Course(1, 1, 2021, 101, "CSE", "SP", "Large");
        Course course2 = new Course(2, 1, 2022, 110, "CSE", "WI", "Large");
        Course course3 = new Course(3,1,2020, 30, "CSE", "FA", "Small");
        List<Course> courses = new ArrayList<Course>();

        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        StudentToCSVTranslator translator = new StudentToCSVTranslator(student, courses);
        String csvFormat = translator.getCSV();

        assertEquals("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\n" +
                "Frank,,,,\n" +
                "none,,,,\n" +
                "2021,SP,CSE,101,Large\n" +
                "2022,WI,CSE,110,Large\n" +
                "2020,FA,CSE,30,Small", csvFormat);
    }
}
