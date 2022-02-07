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

import java.util.List;

public class ProfileCoursesActivity extends AppCompatActivity {
    private AppDatabase db;
    private IStudent student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_courses);

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

        if (quarter != "Fall" || quarter != "Winter" || quarter != "Spring" ||
            quarter != "Summer Session I" || quarter != "Summer Session II" ||
            quarter != "Special Summer Session") {
            error_text.setText("That is an invalid Quarter.");
        }

        int newCourseId = db.coursesDAO().count() + 1;
        int studentId = student.getId();
        Course new_course = new Course(newCourseId, studentId, year, course_number, subject, quarter);
        db.coursesDAO().insert(new_course);

        TextView stored_courses = findViewById(R.id.stored_courses_display);
        stored_courses.setText(stored_courses.getText() + new_course.getCourse() + "\n");
    }

    public void onBackButtonClicked(View view) {

    }

    public void onSubmitButtonClicked(View view) {

    }
}