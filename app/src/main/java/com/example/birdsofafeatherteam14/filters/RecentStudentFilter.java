package com.example.birdsofafeatherteam14.filters;

import android.util.Pair;

import com.example.birdsofafeatherteam14.filters.Filter;
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

    @Override
    public List<Pair<Student, Integer>> studentFilter(List<Student> students){
        List<Pair<Student, Integer>> commonClasses = new ArrayList<>();

        List<List<Course>> classes = prepareClassOverlapList(students);


        for (int i = 0; i < classes.size(); i++){
            if (classes.get(i).size() != 0){
                commonClasses.add(new Pair<>(students.get(i), classes.get(i).size()));
            }
        }

        Collections.sort(commonClasses, Comparator.comparing(p -> -p.second));

        List<Pair<Student, Integer>> recentCommonClasses = new ArrayList<>();
        for(int i = db.sessionDAO().count(); i > 0; i--){
            for(Pair<Student, Integer> p: commonClasses){
                if(p.first.getSesssionId() == i) {
                    recentCommonClasses.add(p);
                }
            }
        }

        return recentCommonClasses;

    }
}
