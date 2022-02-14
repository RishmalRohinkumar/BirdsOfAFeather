package com.example.birdsofafeatherteam14;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentCourseComparator {
    List<Course> s1_list, s2_list;

    public StudentCourseComparator(List<Course> s1, List<Course> s2){
        this.s1_list = s1;
        this.s2_list = s2;
    }

    public List<Course> compare(){
        s1_list.retainAll(s2_list);
        return s1_list;
    }
}
