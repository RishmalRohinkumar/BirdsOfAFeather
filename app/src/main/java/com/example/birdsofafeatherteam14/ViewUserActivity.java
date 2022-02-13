package com.example.birdsofafeatherteam14;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.*;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

import com.example.birdsofafeatherteam14.model.IStudent;
import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.squareup.picasso.Picasso;

public class ViewUserActivity extends AppCompatActivity {
    private IStudent student;
    private AppDatabase db;

    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private CoursesViewAdapter coursesViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

         Intent intent = getIntent();
         int studentId = intent.getIntExtra("student_id",0);

         db = AppDatabase.singleton(this);
         student = db.studentWithCoursesDAO().get(studentId);
         List<Course> courses = db.coursesDAO().getForStudent(studentId);
         String url = student.getPhoto();

         // Set up the recycler view to show our database contents.
         coursesRecyclerView = findViewById(R.id.courses_view);
         coursesLayoutManager = new LinearLayoutManager(this);
         coursesRecyclerView.setLayoutManager(coursesLayoutManager);

         coursesViewAdapter = new CoursesViewAdapter(courses);
         coursesRecyclerView.setAdapter(coursesViewAdapter);

        TextView name_view = (TextView) findViewById(R.id.other_student_name);
        ImageView pic = findViewById(R.id.other_Student_picture);
        name_view.setText(student.getName());
        Picasso.get().load(url).resize(175,175).into(pic);



    }

    public void clickGoBackOnViewOtherStudentActivity(View view) {
        finish();
    }
}