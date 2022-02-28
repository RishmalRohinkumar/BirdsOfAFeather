package com.example.birdsofafeatherteam14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProfileCoursesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<Course> coursesToAdd;

    Spinner quarter_spinner = findViewById(R.id.enter_quarter);
    Spinner year_spinner = findViewById(R.id.enter_year);
    Spinner size_spinner = findViewById(R.id.enter_size);

    String quarter;
    int year;
    String size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_courses);

        coursesToAdd = new ArrayList<Course>();

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

        ArrayAdapter<CharSequence> quarters_adapter = ArrayAdapter.createFromResource(this,
                R.array.quarters_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> years_adapter = ArrayAdapter.createFromResource(this,
                R.array.years_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> sizes_adapter = ArrayAdapter.createFromResource(this,
                R.array.sizes_array, android.R.layout.simple_spinner_item);

        quarters_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        years_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizes_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        quarter_spinner.setAdapter(quarters_adapter);
        year_spinner.setAdapter(years_adapter);
        size_spinner.setAdapter(sizes_adapter);

        quarter_spinner.setOnItemSelectedListener(this);
        year_spinner.setOnItemSelectedListener(this);
        size_spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.enter_quarter:
                String[] quarters = getResources().getStringArray(R.array.quarters_array);
                quarter = quarters[pos];
                break;
            case R.id.enter_year:
                String[] years = getResources().getStringArray(R.array.years_array);
                year = Integer.parseInt(years[pos]);
                break;
            case R.id.enter_size:
                String[] sizes = getResources().getStringArray(R.array.sizes_array);
                size = sizes[pos];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onEnterButtonClicked(View view) {
        EditText subject_text = findViewById(R.id.enter_subject);
        EditText course_number_text = findViewById(R.id.enter_course_number);

        String subject = subject_text.getText().toString().toUpperCase();
        int course_number = Integer.parseInt(course_number_text.getText().toString());

        TextView error_text = findViewById(R.id.error_textview);

        if (course_number < 0) {
            error_text.setText("That is an invalid Course Number.");
        }

        if (quarter.equals("FA") || quarter.equals("WI") || quarter.equals("SP")||
            quarter.equals("SS1") || quarter.equals("SS2") ||
            quarter.equals("SSS")) {

            // set course/student id later once we are adding to the database
            Course new_course = new Course(0, 0, year, course_number, subject, quarter);
            this.coursesToAdd.add(new_course);
            error_text.setText("Added quarter");

            TextView stored_courses = findViewById(R.id.stored_courses_display);
            stored_courses.setText(stored_courses.getText().toString() + new_course.getCourse() + "\n");
        } else {
            error_text.setText("That is an invalid Quarter.");
        }
    }

    public void onSubmitButtonClicked(View view) {
        // Get the name and image url that were passed into this activity
        Intent extraIntent = getIntent();
        String name = extraIntent.getStringExtra("student_name");
        String imageUrl = extraIntent.getStringExtra("student_url");


        // Set up Database and add the student to it
        AppDatabase db = AppDatabase.singleton(this);
        Student student = new Student(db.studentDAO().count()+1, name, imageUrl);
        db.studentDAO().insert(student);

        // track the id of the courses since we aren't adding them to the database as they are entered,
        // only once the submit button is pressed
        int currentCourseId = db.coursesDAO().count() + 1;

        // Go through courses we want to add and add them to the database
        for (Course course : coursesToAdd) {
            course.courseId = currentCourseId++;
            course.studentId = student.studentId;
            db.coursesDAO().insert(course);
        }
        // Return back to the main activity, with an intent for the id of the student running the
        // app so the main activity can query the database with that id
        Intent intent = new Intent(this, MainActivity.class);
        // Tell android that the Main Activity is already on the stack, so lets go to the main activity
        // and get rid of everything that is on top of it
        // Once we return to the main activity it will be on top of the stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Tell the Main Activity we already initialized a student, and give it the ID so it can find
        // the student in the database
        intent.putExtra("student_id", student.studentId);
        startActivity(intent);
    }
}