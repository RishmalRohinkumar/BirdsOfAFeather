package com.example.birdsofafeatherteam14;

import static com.example.birdsofafeatherteam14.Utilities.showAlert;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;
import com.example.birdsofafeatherteam14.model.db.StudentDAO;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.ArrayList;
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

    protected AppDatabase db;

    private int studentId;

    public static final int NO_ID_SET = -999999;

    private static boolean searching = false; // the search button toggles this between true and false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "In onCreate() of MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setTitle("Birds of a Feather");

        setUpRealBluetooth();

        setCurrentStudentID();

        // In the future, maybe store the ID of the current user somewhere else in the database
        // we can store which is the user student between instances of the app
        if (studentId == NO_ID_SET) {
            Log.i(TAG, "No student ID set: executing profile sequence");
            // The current student hasn't been created, so we should go through the profile
            // creation process. Once we've looped back around to the main activity, then
            // an extra with the id of this student will be passed through, which we can use
            // in the else clause
            Intent intent = new Intent(this, ProfileNameActivity.class);
            startActivity(intent);
        } else {
            Log.i(TAG, "student ID is set: setting up the database.");
            // We have a student Id, so we should set up the database
            db = AppDatabase.singleton(getApplicationContext());
        }
    }

    // Updates the recycler views with the other students that have overlapping classes
    // should only be called when the start button is clicked
    private void updateStudentViews() {
        Log.i(TAG, "updateStudentViews() called");

        if (db != null) {
            Log.i(TAG, "Database not null in updateStudentViews()");

            List<? extends Student> students = db.studentDAO().getAll();

            Log.i(TAG, "Received list of students from the database of size: " + students.size());

            List<Integer> classes= new ArrayList<Integer>();

            Student user = db.studentDAO().get(studentId);

            Log.i(TAG, "Going through other students and selecting the common courses");
            //Remove user from student list
            students.remove(user);

            //List out common classes
            for(Student student : students){
                StudentCourseComparator comparator = new StudentCourseComparator
                        (db.coursesDAO().getForStudent(studentId), db.coursesDAO().getForStudent(student.getId()));
                List<Course> overlapList = comparator.compare();
                classes.add(overlapList.size());
            }

            // These lists are the final versions which will be passed into the recycler views
            // and ultimately displayed on the screen. They have been curated to only include
            // students that the current student has common classes with. The commonClasses<Integer>
            // list is so that the recycler view knows how many common classes the current user student
            // and the student in question have, so it can display that information to the screen.
            List<Student> commonStudents = new ArrayList<Student>();
            List<Integer> commonClasses = new ArrayList<Integer>();

            for (int i = 0; i < classes.size(); i++){
                if (classes.get(i) != 0){
                    commonClasses.add(classes.get(i));
                    commonStudents.add(students.get(i));
                }
            }

            Log.i(TAG, "Common courses found: starting to set up RecyclerViews");
            studentRecyclerView = findViewById(R.id.students_view);

            studentLayoutManager = new LinearLayoutManager(this);
            studentRecyclerView.setLayoutManager(studentLayoutManager);

            studentViewAdapter = new StudentViewAdapter(commonStudents, commonClasses);
            studentRecyclerView.setAdapter(studentViewAdapter);
        } else {
            Log.i(TAG, "Database null in updateStudentViews()");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "in onDestroy() of MainActivity");
        // This is important for any test cases involving the main activity.
        // Need to close the database so all of the tests running one after each other
        // don't fuck up the database
        if (db != null) {
            db.close();
        }
    }

    // receives information from the bluetooth activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "In onActivityResult() of MainActivity");

        if (requestCode == START_MOCK_BLUETOOTH) {
            if(resultCode == Activity.RESULT_OK){
                Log.i(TAG, "Received ok result from MockBlueToothActivity");
                // We have a result from the mock bluetooth activity if we're in here
                List<String> mockStudents = data.getStringArrayListExtra("messages");
                if (mockStudents != null && !mockStudents.isEmpty()) {
                    Log.i(TAG, "Received valid mock Students from Mock Bluetooth Activity");
                    setUpMockBluetooth(mockStudents);
                } else {
                    Log.i(TAG, "Received empty or invalid mock Students from Mock Bluetooth Activity");
                }
            }
        }
    }

    // Deals with converting the CSV string from the Mock Bluetooth Task
    // and adds the corresponded students and courses to the databse
    private void addStudentCSVStringToDb(String str) {
        // All these indices have to do with the format specified in this piazza post:
        // https://piazza.com/class/kx9gvm79v371z5?cid=466
        Log.i(TAG, "in addStudentCSVStringToDb");

        final int YEAR_INDEX = 0;
        final int QUARTER_INDEX = 1;
        final int SUBJECT_INDEX = 2;
        final int NUMBER_INDEX = 3;

        try {
            String[] splitByNewline = str.split("\n");
            String name = splitByNewline[0].split(",")[0];
            String url = splitByNewline[1].split(",")[0];
            Student student = new Student(db.studentDAO().count()+1, name, url);

            List<Course> courses = new ArrayList<Course>();
            int currCourseId = db.coursesDAO().count() + 1;
            for (int i = 2; i < splitByNewline.length; i++) {
                String[] courseInfo = splitByNewline[i].split(",");
                int courseYear = Integer.parseInt(courseInfo[YEAR_INDEX]);
                String courseQuarter = courseInfo[QUARTER_INDEX];
                String courseSubject = courseInfo[SUBJECT_INDEX];
                int courseNum = Integer.parseInt(courseInfo[NUMBER_INDEX]);
                // POST INCREMENT SO IT'S ONE GREATER FOR THE NEXT COURSE
                // DOING IT THIS WAY BECAUSE WE ARE NOT ADDING COURSES TO THE DB
                // IN THIS LOOP, WE DO IT AT THE END SO THE .count FUNCTION WILL
                // KEEP RETURNING THE SAME VALUE
                Course course = new Course(currCourseId++, student.studentId,
                        courseYear, courseNum, courseSubject, courseQuarter);
                courses.add(course);
            }

            // only add to db if all good and nothing went wrong earlier

            db.studentDAO().insert(student);
            Log.i(TAG, "Successfully Added a student from MockBluetooth to the database");
            for (Course c : courses) {
                db.coursesDAO().insert(c);
                Log.i(TAG, "Successfully Added a course from MockBluetooth to the database");
            }

        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            showAlert(this,
                    "Error in CSV formatting. Please make sure there is only one newline between each row. If you can't get it to work, then please ask Tyler.");
            Log.i(TAG, "Error in Mock Bluetooth result");
        }

    }

    // Sends us to the mock bluetooth activity and sets up
    // the necessary information so that we can receive the result from it
    public void goToMockBluetoothActivity(View view) {
        Log.i(TAG, "in goToMockBluetoothActivity");
        Intent intent = new Intent(this, MockBluetoothActivity.class);
        startActivityForResult(intent, START_MOCK_BLUETOOTH);
    }

    // Sends fake bluetooth messages based upon the CSV string data from the bluetooth activity
    public void setUpMockBluetooth(List<String> mockStudents) {
        Log.i(TAG, "in setUpMockBluetooth");
        this.messageListener = new MockMessageListenerClass(this.realListener, mockStudents);
    }


    // Runs when the start button is clicked and displays the screen with all of the
    // other students which have overlapping courses.
    public void onStartClicked(View view) {
        Log.i(TAG, "In onStartClicked");
        toggleSearchStatus();
    }

    private void toggleSearchStatus() {
        this.searching = !this.searching;
        Button searchButton = findViewById(R.id.searchButton);

        if (this.searching) {
            searchButton.setText("Stop");
            updateStudentViews();
        } else {
            searchButton.setText("Start");
            clearStudentViews();
        }
    }

    private void clearStudentViews() {
        studentRecyclerView = findViewById(R.id.students_view);

        studentLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(studentLayoutManager);

        // pass in empty lists so nothing is displayed
        studentViewAdapter = new StudentViewAdapter(new ArrayList<Student>(), new ArrayList<Integer>());
        studentRecyclerView.setAdapter(studentViewAdapter);
    }


    // Set up the real blue tooth listener to add people to the database
    // as messages are received.
    public void setUpRealBluetooth() {
        Log.i(TAG, "in setUpRealBluetooth");
        // Set up bluetooth
        this.realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                // Put stuff in the database
                Log.i(TAG, "Found a bluetooth message!");
                if (db != null) {
                    Log.i(TAG, "Found a bluetooth message, and am able to act upon it because db is set up.");
                    if (searching) {
                        Log.i(TAG, "Actively searching, so will check this message");
                        String student_data = new String(message.getContent());
                        addStudentCSVStringToDb(student_data);
                        // whenever receive a bluetooth message, update the student views
                        updateStudentViews();
                    } else {
                        Log.i(TAG, "Not actively searching, doing nothing with this message");
                    }
                } else {
                    Log.i(TAG, "Found a bluetooth message, and am not able to act upon it because db is not set up");
                }
            }

            @Override
            public void onLost(@NonNull Message message) {
                Log.i(TAG, "Bluetooth message lost: currently doing nothing with this.");
                // Does nothing atm
            }
        };
    }

    // Sets the current student ID to the Main Activity studentId
    // variable, and sets it in shared preferences so other activities can
    // access it.
    public void setCurrentStudentID() {
        Log.i(TAG, "In setCurrentStudentID");
        // This is set if the ProfileCourseActivity has looped back to the main activity
        // and has set the current student id in an extra
        this.studentId = getIntent().getIntExtra("student_id", NO_ID_SET);
        Log.i(TAG, "student id from extras is " + this.studentId);

        // We only want to set the student if an ID has actually been set
        if (this.studentId != NO_ID_SET) {
            Log.i(TAG, "Student Id set, going to put it in shared preferrences");
            //SharedPreferences for studentId
            SharedPreferences preferences = getSharedPreferences("BOAF_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("currentStudentId", studentId);
            editor.apply();
        }
    }
}