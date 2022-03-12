package com.example.birdsofafeatherteam14.filters;

import static java.lang.Integer.parseInt;

import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.example.birdsofafeatherteam14.CurrentQuarterTracker;
import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuarterStudentFilter extends Filter {


    public QuarterStudentFilter(AppDatabase db) {
        super(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Pair<Student, Integer>> studentFilter(List<Student> students){
        List<List<Course>> classes = prepareClassOverlapList(students);
        CurrentQuarterTracker date = new CurrentQuarterTracker();
        String quarter = date.getQtr();
        String year = date.getYr();

        List<List<Course>> quarterCourseList = new ArrayList<>();
        for (List<Course> courses : classes){
            List<Course> quarterCourses = new ArrayList<>();
            for (Course course : courses){
                if (course.courseQuarter.equals(quarter) && course.courseYear == parseInt(year)) {
                    quarterCourses.add(course);
                }
            }
            quarterCourseList.add(quarterCourses);
        }

        List<Pair<Student, Integer>> commonClasses = mergeStudentsAndCourses(students, quarterCourseList);

        Collections.sort(commonClasses, Comparator.comparing(p -> -p.second));

        return commonClasses;
    }
}
