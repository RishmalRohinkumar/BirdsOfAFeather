package com.example.birdsofafeatherteam14;

import static com.example.birdsofafeatherteam14.Utilities.showAlert;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.example.birdsofafeatherteam14.model.DummyStudent;
import com.example.birdsofafeatherteam14.model.IStudent;
import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Stuff for bluetooth
    private MessageListener messageListener;
    private MessageListener realListener;
    private static final String TAG = "BOAF-14";

    public static final int START_MOCK_BLUETOOTH = 99;

    protected RecyclerView studentRecyclerView;
    protected RecyclerView.LayoutManager studentLayoutManager;
    protected StudentViewAdapter studentViewAdapter;

    protected Course course0 = new Course(0, 0, 2022, 110, "CSE", "Winter");
    protected Course course1 = new Course(1, 0, 2022, 105, "CSE", "Winter");
    protected Course course2 = new Course(0, 1, 2022, 110, "CSE", "Winter");

    protected AppDatabase db;

    private int studentId;

    public Course[] courses0 = new Course[]{course0, course1};
    public Course[] courses1 = new Course[]{course2};

    protected IStudent[] data = {
            new DummyStudent(0, "Jane Doe", "photo0", courses0),
            new DummyStudent(1, "John Doe", "photo1", courses1)
    };

    public static final int NO_ID_SET = -999999;


    String profile_pic_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setTitle("Birds of a Feather");

        // Set up bluetooth
        this.realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                // Put stuff in the database
                if (db != null) {
                    String student_data = new String(message.getContent());
                    addStudentCSVStringToDb(student_data);
                    updateStudentViews();
                }
            }

            @Override
            public void onLost(@NonNull Message message) {
                // Does nothing atm
            }
        };

        this.studentId = getIntent().getIntExtra("student_id", NO_ID_SET);
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
            updateStudentViews();
        }
    }


    private void updateStudentViews() {
        if (db != null) {
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

    // receive information from the bluetooth activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == START_MOCK_BLUETOOTH) {
            if(resultCode == Activity.RESULT_OK){
                List<String> mockStudents = data.getStringArrayListExtra("messages");
                if (mockStudents != null && !mockStudents.isEmpty()) {
                    setUpMockBluetooth(mockStudents);
                }
            }
        }
    }

    private void addStudentCSVStringToDb(String str) {
        // All these indices have to do with the format specified in this piazza post:
        // https://piazza.com/class/kx9gvm79v371z5?cid=466
        try {
            String[] splitByNewline = str.split("\n");
            String name = splitByNewline[0].split(",")[0];
            String url = splitByNewline[1].split(",")[0];
            Student student = new Student(db.studentWithCoursesDAO().count()+1, name, url);
            db.studentWithCoursesDAO().insert(student);

            for (int i = 2; i < splitByNewline.length; i++) {
                String[] courseInfo = splitByNewline[i].split(",");
                int courseYear = Integer.parseInt(courseInfo[0]);
                String courseQuarter = courseInfo[1];
                String courseSubject = courseInfo[2];
                int courseNum = Integer.parseInt(courseInfo[3]);
                Course course = new Course(db.coursesDAO().count() + 1, student.studentId,
                        courseYear, courseNum, courseSubject, courseQuarter);
                db.coursesDAO().insert(course);
            }
        } catch (IndexOutOfBoundsException e) {
            showAlert(this, "Error in CSV formatting: " + e.toString());
        }

    }

    public void goToMockBluetoothActivity(View view) {
        Intent intent = new Intent(this, MockBluetoothActivity.class);
        startActivityForResult(intent, START_MOCK_BLUETOOTH);
    }

    public void setUpMockBluetooth(List<String> mockStudents) {
        this.messageListener = new MockMessageListenerClass(this.realListener, mockStudents);
    }
}