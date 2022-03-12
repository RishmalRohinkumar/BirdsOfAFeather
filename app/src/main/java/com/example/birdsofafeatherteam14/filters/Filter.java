package com.example.birdsofafeatherteam14.filters;

import android.util.Pair;

import com.example.birdsofafeatherteam14.StudentCourseComparator;
import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import java.util.ArrayList;
import java.util.List;

public abstract class Filter {
    protected AppDatabase db;

    public Filter(AppDatabase db){
        this.db = db;
    }

    public List<List<Course>> prepareClassOverlapList(List<Student> students){
        List<List<Course>> classes = new ArrayList<>();
        Student user = db.studentDAO().getCurrentUsers().get(0);
        //List out common classes
        for (Student student : students) {
            StudentCourseComparator comparator = new StudentCourseComparator
                    (db.coursesDAO().getForStudent(user.studentId), db.coursesDAO().getForStudent(student.getId()));
            List<Course> overlapList = comparator.compare();
            classes.add(overlapList);
        }
        return classes;
    }

    public List<Pair<Student, Integer>> mergeStudentsAndCourses(List<Student> students, List<List<Course>> classes){
        List<Pair<Student, Integer>> commonClasses = new ArrayList<>();
        for (int i = 0; i < classes.size(); i++){
            if (classes.get(i).size() != 0){
                commonClasses.add(new Pair<>(students.get(i), classes.get(i).size()));
            }
        }

        return commonClasses;
    }

    protected List<Pair<Student, Integer>> pinWaved(List<Pair<Student,Integer>> list) {
        List<Pair<Student, Integer>> wavedStudents = new ArrayList<>();
        List<Pair<Student, Integer>> notWavedStudents = new ArrayList<>();
        for (Pair<Student, Integer> s : list) {
            if (s.first.wave) {
                wavedStudents.add(new Pair(s.first, s.second));
            } else {
                notWavedStudents.add(new Pair(s.first, s.second));
            }
        }

        list.clear();
        for (Pair<Student, Integer> p : wavedStudents) {
            list.add(p);
        }
        for (Pair<Student, Integer> p : notWavedStudents) {
            list.add(p);
        }
        return list;
    }

    public abstract List<Pair<Student, Integer>> studentFilter(List<Student> students);

}
