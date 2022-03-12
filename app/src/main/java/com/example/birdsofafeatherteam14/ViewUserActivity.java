package com.example.birdsofafeatherteam14;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.content.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.squareup.picasso.Picasso;

public class ViewUserActivity extends AppCompatActivity{
    public Student student;
    private AppDatabase db;
    private int studentId;

    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private CoursesViewAdapter coursesViewAdapter;

    private WaveMessageTranslator wmt;

    private boolean published;

    private Message msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        // Get the student id that we need to display about
        Intent intent = getIntent();
        studentId = intent.getIntExtra("student_id",0);

        SharedPreferences sharedPreferences = getSharedPreferences("BOAF_PREFERENCES", MODE_PRIVATE);

        // Get the relevant course and student information from the database
        db = AppDatabase.singleton(this);
        student = db.studentDAO().get(studentId);
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

        CheckBox favourite = (CheckBox) findViewById(R.id.starViewUser);
        favourite.setChecked(student.getFavourite());

        // Make the wave button say "Wave Back" if the user has already waved to us
        Button waveButton = findViewById(R.id.wave_button);
        if (student.wave) {
            waveButton.setText("Wave Back");
        } else {
            waveButton.setText("Wave");
        }

        this.wmt = new WaveMessageTranslator(AppDatabase.singleton(this).studentDAO().getCurrentUsers().get(0));
        this.published = false;
        this.msg = wmt.createMessage(this.student);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.published) {
            Log.i(MainActivity.TAG, "Unpublished wave message");
            Nearby.getMessagesClient(this).unpublish(msg);
        }
    }

    // link star shape checkbox to favourite and show toast
    public void onFavViewClick(View view) {
        IFavoriteClickMediator mediator = new FavoriteClickMediator(db);
        this.student = mediator.mediateFavoriteToggle(this, this.findViewById(R.id.starViewUser), this.student);
    }

    // Send us back to the main activity
    public void clickGoBackOnViewOtherStudentActivity(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void onWaveButtonClicked(View view) {
        Log.i(MainActivity.TAG, "Wave message Published");
        Nearby.getMessagesClient(this).publish(msg);
        this.published = true;
        Toast toast = Toast.makeText(this, "Wave sent!", Toast.LENGTH_SHORT);
        toast.show();
    }
}