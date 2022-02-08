package com.example.birdsofafeatherteam14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.birdsofafeatherteam14.model.IStudent;
import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import java.util.ArrayList;
import java.util.List;

public class ProfileCoursesActivity extends AppCompatActivity {
    private AppDatabase db;
    private Student student;

    private List<Course> coursesToAdd;
    // track the id of the courses since we aren't adding them to the database as they are entered,
    // only once the submit button is pressed
    private int currentCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_courses);

        coursesToAdd = new ArrayList<Course>();

        // Get the name and image url that were passed into this activity
        Intent intent = getIntent();
        String name = intent.getStringExtra("student_name");
        String imageUrl = intent.getStringExtra("student_url");

        // Set up Database and add the student to it
        db = AppDatabase.singleton(this);
        student = new Student(db.studentWithCoursesDAO().count()+1, name, imageUrl);
        db.studentWithCoursesDAO().insert(student);

        currentCourseId = db.coursesDAO().count() + 1;
        /*
        Old code where we were already populating the current courses for a student that might already
        be in the database. However, I think we're only supposed to create a new profile on first time
        running the app, so for now I think it's safe to assume that this task is only going to be
        run on first time set up. Mainly keeping this here to explain my thought process.
        ----------------------------------------------------------------------------------------------

        List<Course> courses = db.coursesDAO().getForStudent(studentId);
        TextView stored_courses = findViewById(R.id.stored_courses_display);

        for(int i = 0; i < courses.size(); i++) {
            stored_courses.setText(stored_courses.getText() + courses.get(i).getCourse() + "\n");
        }
        */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void onEnterButtonClicked(View view) {
        EditText subject_text = findViewById(R.id.enter_subject);
        EditText course_number_text = findViewById(R.id.enter_course_number);
        EditText quarter_text = findViewById(R.id.enter_quarter);
        EditText year_text = findViewById(R.id.enter_year);

        String subject = subject_text.getText().toString().toUpperCase();
        int course_number = Integer.parseInt(course_number_text.getText().toString());
        String quarter = quarter_text.getText().toString();
        int year = Integer.parseInt(year_text.getText().toString());

        TextView error_text = findViewById(R.id.error_textview);

        if (course_number <= 0) {
            error_text.setText("That is an invalid Course Number.");
        }

        if (quarter.equals("Fall") || quarter.equals("Winter") || quarter.equals("Spring")||
            quarter.equals("Summer Session I") || quarter.equals("Summer Session II") ||
            quarter.equals("Special Summer Session")) {

            // Student Id, always the same, take the current Course Id and then iterate it for the next course to be added
            Course new_course = new Course(currentCourseId++, student.studentId, year, course_number, subject, quarter);
            this.coursesToAdd.add(new_course);
            error_text.setText("Added quarter");

            TextView stored_courses = findViewById(R.id.stored_courses_display);
            stored_courses.setText(stored_courses.getText().toString() + new_course.getCourse() + "\n");
        } else {
            error_text.setText("That is an invalid Quarter.");
        }
    }

    public void onBackButtonClicked(View view) {

    }

    public void onSubmitButtonClicked(View view) {
        // Go through courses we want to add and add them to the database
        for (Course course : coursesToAdd) {
            db.coursesDAO().insert(course);
        }
        // Return back to the main activity, with an intent for the id of the student running the
        // app so the main activity can query the database with that id
        Intent intent = new Intent(this, MainActivity.class);
        // Tell android that the Main Activity is already on the stack, so lets go to the main activity
        // and get rid of everything that is ontop of it
        // Once we return to the main activity it will be on top of the stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Tell the Main Activity we already initialized a student, and give it the ID so it can find
        // the student in the database
        intent.putExtra("student_id", this.student.studentId);
        startActivity(intent);
    }
}