package com.example.birdsofafeatherteam14;

import com.example.birdsofafeatherteam14.model.db.Student;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FilterTests {
    List<Student> students = new ArrayList<>();

    @Before
    public void setup(){
        students.add(new Student(1, 1, "Bill", "", "A", false));
        students.add(new Student(2, 1, "Zach", "", "B", false));
        students.add(new Student(3, 1, "Tom", "", "C", false));
        students.add(new Student(4, 1, "Jim", "", "D", false));
        students.add(new Student(5, 1, "Matt", "", "E", false));
        students.add(new Student(6, 1, "Nathan", "", "F", false));
        students.add(new Student(7, 1, "Tyler", "", "G", false));
        students.add(new Student(8, 1, "Bob", "", "H", false));
        students.add(new Student(9, 1, "Jack", "", "I", false));
        students.add(new Student(10, 1, "John", "", "J", false));
    }

    @Test
    public void testNoFilter(){

    }
}
