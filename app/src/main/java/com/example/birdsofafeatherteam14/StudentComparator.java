package com.example.birdsofafeatherteam14;

import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.StudentWithCourses;

import java.util.ArrayList;
import java.util.List;

public class StudentComparator {
    List<Course> s1_list, s2_list;

    public StudentComparator(StudentWithCourses s1, StudentWithCourses s2){
        this.s1_list = s1.getCourses();
        this.s2_list = s2.getCourses();
    }

    public List<Course> compare(){
        List<Course> overlap_list = new ArrayList<Course>();

        for(Course c1 : s1_list){
            for(Course c2 : s2_list){
                if((c1.courseName.equals(c2.courseName)) && (c1.courseNumber == c2.courseNumber)){
                    s1_list.remove(c1);
                    s2_list.remove(c2);
                    overlap_list.add(c1);
                }
            }
        }

        return overlap_list;
    }
}
