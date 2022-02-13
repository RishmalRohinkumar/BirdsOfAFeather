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

    protected AppDatabase db;

    private static final int NO_ID_SET = -999999;


    String profile_pic_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setTitle("Birds of a Feather");

        int studentId = getIntent().getIntExtra("student_id", NO_ID_SET);
        // In the future, maybe store the ID of the current user somewhere else in the database
        // we can store which is the user student between instances of the app
        if (studentId == NO_ID_SET) {
            // The current student hasn't been created, so we should go through the profile
            // creation process. Once we've looped back around to the main activity, then
            // an extra with the id of this student will be passed through, which we can use
            // in the else clause
            Intent intent = new Intent(this, ProfileNameActivity.class);
            startActivity(intent);
        } else {
            // We have a student Id, so we should set up the database
            db = AppDatabase.singleton(getApplicationContext());
            List<? extends IStudent> students = db.studentWithCoursesDAO().getAll();

            studentRecyclerView = findViewById(R.id.students_view);

            studentLayoutManager = new LinearLayoutManager(this);
            studentRecyclerView.setLayoutManager(studentLayoutManager);

            studentViewAdapter = new StudentViewAdapter(students);
            studentRecyclerView.setAdapter(studentViewAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // This is important for any test cases involving the main activity.
        // Need to close the database so all of the tests running one after each other
        // don't fuck up the database
        if (db != null) {
            db.close();
        }
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