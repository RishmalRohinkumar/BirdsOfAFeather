package com.example.birdsofafeatherteam14.filters;

import android.util.Pair;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NoneStudentFilter extends Filter {


    public NoneStudentFilter(AppDatabase db) {
        super(db);
    }

    @Override
    public List<Pair<Student, Integer>> studentFilter(List<Student> students){
        List<List<Course>> classes = prepareClassOverlapList(students);

        List<Pair<Student, Integer>> commonClasses = mergeStudentsAndCourses(students, classes);


        Collections.sort(commonClasses, Comparator.comparing(p -> -p.second));

        return commonClasses;
    }
}
