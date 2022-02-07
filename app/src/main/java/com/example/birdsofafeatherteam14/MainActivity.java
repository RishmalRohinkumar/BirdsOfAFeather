package com.example.birdsofafeatherteam14;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


import com.example.birdsofafeatherteam14.model.DummyStudent;
import com.example.birdsofafeatherteam14.model.IStudent;
import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView studentRecyclerView;
    protected RecyclerView.LayoutManager studentLayoutManager;
    protected StudentViewAdapter studentViewAdapter;

    protected Course course0 = new Course(0, 0, 2022, 110, "CSE", "Winter");
    protected Course course1 = new Course(1, 0, 2022, 105, "CSE", "Winter");
    protected Course course2 = new Course(0, 1, 2022, 110, "CSE", "Winter");

    public Course[] courses0 = new Course[]{course0, course1};
    public Course[] courses1 = new Course[]{course2};

    protected IStudent[] data = {
            new DummyStudent(0, "Jane Doe", "photo0", courses0),
            new DummyStudent(1, "John Doe", "photo1", courses1)
    };


    String profile_pic_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setTitle("Birds of a Feather");

        AppDatabase db = AppDatabase.singleton(getApplicationContext());
        List<? extends IStudent> students = db.studentWithCoursesDAO().getAll();

        studentRecyclerView = findViewById(R.id.students_view);

        studentLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(studentLayoutManager);

        studentViewAdapter = new StudentViewAdapter(students);
        studentRecyclerView.setAdapter(studentViewAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                this.profile_pic_url = data.getStringExtra("url");
            }

        }
    }
}