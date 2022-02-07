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
    private IStudent student;

    private List<Course> coursesToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_courses);

        coursesToAdd = new ArrayList<Course>();

        Intent intent = getIntent();
        int studentId = intent.getIntExtra("student_id", 0);

        db = AppDatabase.singleton(this);
        student = db.studentWithCoursesDAO().get(studentId);
        List<Course> courses = db.coursesDAO().getForStudent(studentId);
        TextView stored_courses = findViewById(R.id.stored_courses_display);

        for(int i = 0; i < courses.size(); i++) {
            stored_courses.setText(stored_courses.getText() + courses.get(i).getCourse() + "\n");
        }
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

            Course new_course = new Course(0, 0, year, course_number, subject, quarter);
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
        // NEED TO GO THROUGH coursesToAdd AND SET THE IDS APPROPRIATELY BEFORE ADDING TO DATABASE
        // THIS FUNCTION DOESN'T REALLY DO ANYTHING YET
        int newCourseId = db.coursesDAO().count() + 1;
        int studentId = student.getId();
        //db.coursesDAO().insert(new_course);

        // AT END, go to next activity after adding to database
    }
}