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

    private int dateWeight(String quarter, String year){
        int weight = 5*parseInt(year);

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
                val += 5;
                break;
            case 1:
                val += 4;
                break;
            case 2:
                val += 3;
                break;
            case 3:
                val += 2;
                break;
            case 4:
                val += 1;
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
        List<Pair<Student, Integer>> commonClasses = mergeStudentsAndCourses(students, classes);
        Collections.sort(commonClasses, Comparator.comparing(p -> -p.second));

        CurrentQuarterTracker date = new CurrentQuarterTracker();
        String quarter = date.getQtr();
        String year = date.getYr();
        int currDateWeight = dateWeight(quarter,year);

        List<Pair<Student, Integer>> classWeights = new ArrayList<>();

        for (int i = 0; i < classes.size(); i++){
            List<Course> coursesOfStu = classes.get(i);
            int remember = 0;
            for(int j = 0; j < coursesOfStu.size(); j++) {
                int studentDateWeight = dateWeight(coursesOfStu.get(i).courseQuarter, ""+coursesOfStu.get(i).courseYear);
                int age = currDateWeight-studentDateWeight;
                remember += rememberVal(age);
            }
            classWeights.add(new Pair<Student, Integer>(students.get(i), remember));
        }

        Collections.sort(classWeights, Comparator.comparing(p -> -p.second));

        List<Pair<Student, Integer>> recentCommonClasses = new ArrayList<>();

        for(int i = 0; i < classWeights.size(); i++){
            for(int j = 0; j < commonClasses.size(); j++){
                if(classWeights.get(i).first == commonClasses.get(j).first){
                    recentCommonClasses.add(commonClasses.get(j));
                    break;
                }
            }
        }


        return recentCommonClasses;

    }
}
