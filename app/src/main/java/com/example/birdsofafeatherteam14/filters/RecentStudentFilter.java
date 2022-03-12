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

public class RecentStudentFilter extends Filter {


    public RecentStudentFilter(AppDatabase db) {
        super(db);
    }

    private int dateWeight(String quarter, int year){
        int weight = 4*year;

        switch(quarter) {
            case "WI":
                weight += 1;
                break;
            case "SP":
                weight += 2;
                break;
            case "SS1":
                weight += 3;
                break;
            case "SS2":
                weight += 3;
                break;
            case "FA":
                weight += 4;
                break;
        }

        return weight;
    }

    private int rememberVal(int age){
        int val = 0;

        switch(age) {
            case 0:
            case 1:
                val += 5;
                break;
            case 2:
                val += 4;
                break;
            case 3:
                val += 3;
                break;
            case 4:
                val += 2;
                break;
            default:
                val += 1;
        }

        return val;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<Pair<Student, Integer>> studentFilter(List<Student> students){
        List<List<Course>> classes = prepareClassOverlapList(students);

        CurrentQuarterTracker date = new CurrentQuarterTracker();
        int currDateWeight = dateWeight(date.getQtr(), parseInt(date.getYr()));

        List<Integer> classWeights = new ArrayList<>();

        for (List<Course> courses : classes){
            int remember = 0;
            for (Course course : courses){
                int studentDateWeight = dateWeight(course.courseQuarter, course.courseYear);
                int age = currDateWeight-studentDateWeight;
                remember += rememberVal(age);
            }
            if (remember > 0) classWeights.add(remember);
        }

        List<Pair<Student, Integer>> commonClasses = mergeStudentsAndCourses(students, classes);

        List<Pair<Pair<Student, Integer>, Integer>> weightList = new ArrayList<>();
        for (int i = 0; i < classWeights.size(); i++){
            weightList.add(new Pair<>(commonClasses.get(i), classWeights.get(i)));
        }

        Collections.sort(weightList, Comparator.comparing(p -> -p.second));

        List<Pair<Student, Integer>> recentCommonClasses = new ArrayList<>();

        commonClasses = new ArrayList<>();
        for(Pair<Pair<Student, Integer>, Integer> pair : weightList){
            commonClasses.add(pair.first);
        }

        return commonClasses;

    }
}
