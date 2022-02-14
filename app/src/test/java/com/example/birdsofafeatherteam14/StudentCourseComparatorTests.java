package com.example.birdsofafeatherteam14;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.StudentDAO;

import java.util.ArrayList;
import java.util.List;

public class StudentCourseComparatorTests {
    List<Course> s1_courses;
    List<Course> s2_courses;

    @Before
    public void setUp() {
        s1_courses = new ArrayList<Course>();
        s2_courses = new ArrayList<Course>();
    }

    // Test to make sure the comparator works when the two lists are empty
    @Test
    public void testEmptyLists() {
        StudentCourseComparator scc = new StudentCourseComparator(s1_courses, s2_courses);
        assertEquals(true, scc.compare().isEmpty());
    }

    // Test to make sure the comparator returns an empty list when there is no overlap
    @Test
    public void testNoOverlapLists() {
        s1_courses.add(new Course(0, 0, 2020, 110, "CSE", "FA"));
        s1_courses.add(new Course(0, 0, 2022, 120, "CSE", "SP"));
        s2_courses.add(new Course(0, 0, 2022, 120, "PHYS", "SP"));
        StudentCourseComparator scc = new StudentCourseComparator(s1_courses, s2_courses);
        assertEquals(true, scc.compare().isEmpty());
    }

    // Test to make sure that the comparator returns the overlap correctly when there
    // is overlap
    @Test
    public void testOverlapLists() {
        s1_courses.add(new Course(0, 0, 2020, 110, "CSE", "FA"));
        s1_courses.add(new Course(0, 0, 2022, 120, "CSE", "SP"));
        s2_courses.add(new Course(0, 0, 2022, 120, "CSE", "SP"));
        StudentCourseComparator scc = new StudentCourseComparator(s1_courses, s2_courses);
        List<Course> overlap = scc.compare();
        assertEquals(1, overlap.size());
        Course overlapCourse = overlap.get(0);
        assertEquals(120, overlapCourse.courseNumber);
        assertEquals(2022, overlapCourse.courseYear);
        assertEquals("CSE", overlapCourse.courseName);
        assertEquals("SP", overlapCourse.courseQuarter);
    }
}
