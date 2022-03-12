package com.example.birdsofafeatherteam14.filters;

import android.util.Pair;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SmallStudentFilter extends Filter {


    public SmallStudentFilter(AppDatabase db) {
        super(db);
    }

    @Override
    public List<Pair<Student, Integer>> studentFilter(List<Student> students){
        List<List<Course>> classes = prepareClassOverlapList(students);

        List<Double> weights = new ArrayList<>();
        for (List<Course> courses : classes){
            double weight = 0;
            for (Course course : courses) {
                switch (course.courseSize){
                    case "Tiny":
                        weight += 1.00;
                        break;
                    case "Small":
                        weight += 0.33;
                        break;
                    case "Medium":
                        weight += 0.18;
                        break;
                    case "Large":
                        weight += 0.10;
                        break;
                    case "Huge":
                        weight += 0.06;
                        break;
                    case "Gigantic":
                        weight += 0.03;
                        break;
                }
            }
            if (weight > 0) weights.add(weight);
        }

        List<Pair<Student, Integer>> commonClasses = mergeStudentsAndCourses(students, classes);

        List<Pair<Pair<Student, Integer>, Double>> weightList = new ArrayList<>();
        for (int i = 0; i < weights.size(); i++){
            weightList.add(new Pair<>(commonClasses.get(i), weights.get(i)));
        }

        Collections.sort(weightList, Comparator.comparing(p -> -p.second));

        commonClasses = new ArrayList<>();
        for(Pair<Pair<Student, Integer>, Double> pair : weightList){
            commonClasses.add(pair.first);
        }

        return pinWaved(commonClasses);
    }
}
