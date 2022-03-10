package com.example.birdsofafeatherteam14;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.*;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;
import com.squareup.picasso.Picasso;

public class ViewUserActivity extends AppCompatActivity {
    private Student student;
    private AppDatabase db;

    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private CoursesViewAdapter coursesViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        // Get the student id that we need to display about
        Intent intent = getIntent();
        int studentId = intent.getIntExtra("student_id",0);

        SharedPreferences sharedPreferences = getSharedPreferences("BOAF_PREFERENCES", MODE_PRIVATE);
        // get current session info
        int sessionId = sharedPreferences.getInt("currentSession", -1);

        // Get the relevant course and student information from the database
        db = AppDatabase.singleton(this);
        student = db.studentDAO().get(studentId, sessionId);
        List<Course> courses = db.coursesDAO().getForStudent(studentId);

        // get the current student id
        int currStudentId = db.studentDAO().getCurrentUsers().get(0).studentId;

        List<Course> currStudentCourses = db.coursesDAO().getForStudent(currStudentId);
        String url = student.getPhoto();

        // Use the student course comparator so we only display the overlap courses
        StudentCourseComparator comparator = new StudentCourseComparator(currStudentCourses, courses);
        List<Course> overlapList = comparator.compare();

        // Set up the recycler view to show our database contents.
        coursesRecyclerView = findViewById(R.id.courses_view);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        // this courses list should only be the overlap courses
        coursesViewAdapter = new CoursesViewAdapter(overlapList);
        coursesRecyclerView.setAdapter(coursesViewAdapter);

        // Load the image of the other student into the ImageView using Picasso
        TextView name_view = (TextView) findViewById(R.id.other_student_name);
        ImageView pic = findViewById(R.id.other_Student_picture);
        name_view.setText(student.getName());
        Picasso.get().load(url).resize(175,175).into(pic);

        // link star shape checkbox to favourite
        CheckBox favourite = (CheckBox)findViewById(R.id.starViewUser);
        Boolean fav_state = favourite.isChecked();
        db.studentDAO().update(fav_state,studentId);


    }

    // Send us back to the main activity
    public void clickGoBackOnViewOtherStudentActivity(View view) {
        finish();
    }
}