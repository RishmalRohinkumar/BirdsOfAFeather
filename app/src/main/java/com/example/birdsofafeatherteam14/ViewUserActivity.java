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

         Intent intent = getIntent();
         int studentId = intent.getIntExtra("student_id",0);

         SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
         int currStudentId = sharedPreferences.getInt("currentStudentId", 0);
         // need to check sharedPreferences for "current_student_id"
         // use this information to only display the overlap courses

         db = AppDatabase.singleton(this);
         student = db.studentDAO().get(studentId);
         List<Course> courses = db.coursesDAO().getForStudent(studentId);
         List<Course> currStudentCourses = db.coursesDAO().getForStudent(currStudentId);
         String url = student.getPhoto();

         StudentCourseComparator comparator = new StudentCourseComparator(currStudentCourses, courses);
         List<Course> overlapList = comparator.compare();

         // Set up the recycler view to show our database contents.
         coursesRecyclerView = findViewById(R.id.courses_view);
         coursesLayoutManager = new LinearLayoutManager(this);
         coursesRecyclerView.setLayoutManager(coursesLayoutManager);

         // this courses list should only be the overlap courses
         coursesViewAdapter = new CoursesViewAdapter(overlapList);
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